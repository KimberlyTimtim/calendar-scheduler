package com.example.calendar.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.example.calendar.constants.CalendarConstants;
import com.example.calendar.model.Event;
import com.example.calendar.service.CalendarCacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CalendarCacheServiceImpl implements CalendarCacheService {

    private static final Logger LOGGER = LogManager.getLogger(CalendarCacheServiceImpl.class);
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final String UTF_8 = "UTF-8";
    private static final long CACHE_TTL = 7;
    private StringRedisTemplate calendarTemplate;
    private ObjectMapper mapper;

    @Autowired
    public CalendarCacheServiceImpl(StringRedisTemplate calendarTemplate, ObjectMapper mapper) {
        if (calendarTemplate == null || mapper == null) {
            throw new IllegalArgumentException(
                    "Could not initialize CacheService with null reference of calendarTemplate / mapper.");
        }

        this.calendarTemplate = calendarTemplate;
        this.mapper = mapper;
    }

    /**
     * Store event in Redis cache. <BR>
     * If key is already existing, it will overwrite the existing payload. <BR>
     */
    public boolean storeEvent(Event event) {
        long listSize = 0;

        String key = generateKey(event);
        if (key != null) {
            String payloadString = "";

            try {
                payloadString = mapper.writeValueAsString(event);
                payloadString = zip(payloadString);
            } catch (JsonProcessingException e) {
                LOGGER.error(e.getMessage(), e);
            } catch (IOException ex) {
                LOGGER.error("Failed to zip data, saving unzipped content", ex);
            }

            String existingPayload = getPayload(key);
            if (existingPayload != null) {
                // If cached payload already exists, check if payload has changed.
                if (existingPayload.equals(payloadString)) {
                    LOGGER.info("No payload changes.");
                    return true;
                } else {
                    LOGGER.warn("Overwriting existing payload.");
                }
            }

            listSize = pushPayloadToCache(key, payloadString);
        } else {
            LOGGER.error("Error creating key. Please check if the request data is valid.");
        }

        return (listSize == 1);
    }

    public boolean removeEvent(Event event) {
        String key = generateKey(event);
        LOGGER.info("Delete key: " + key);
        
        return calendarTemplate.delete(key);
    }

    public Event retrieveEventByTitle(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    public Event retrieveEventByDate(String startDateTime, String endDateTime) {
        // TODO Auto-generated method stub
        return null;
    }

    public Event retrieveEventByDate(String location) {
        // TODO Auto-generated method stub
        return null;
    }

    private String generateKey(Event event) {
        String key = null;

        if (event != null) {
            key = CalendarConstants.CACHE_KEY_PREFIX + event.getEventId();
        }

        return key;
    }

    private String getPayload(String key) {
        if (calendarTemplate.boundListOps(key).size() > 0) {
            // return the first element in the list
            return calendarTemplate.boundListOps(key).range(0, 0).get(0);
        } else {
            return null;
        }
    }

    private long pushPayloadToCache(String key, String payloadValue) {
        Map<String, Object> log = new HashMap<String, Object>();
        log.put("key", key);
        log.put("value", payloadValue);
        log.put("ttlSettings", CACHE_TTL);
        LOGGER.info(log);

        calendarTemplate.boundListOps(key).leftPush(payloadValue);
        calendarTemplate.boundListOps(key).trim(0, 0);
        calendarTemplate.boundListOps(key).expire(CACHE_TTL, TimeUnit.DAYS);

        return calendarTemplate.boundListOps(key).size();
    }

    private String zip(String uncompressed) throws IOException {
        if (uncompressed == null || uncompressed.length() == 0) {
            return uncompressed;
        }
        return Base64.getEncoder().encodeToString(compress(uncompressed));
    }

    private byte[] compress(String text) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStream out = new DeflaterOutputStream(baos);
        out.write(text.getBytes(UTF_8));
        out.close();

        return baos.toByteArray();
    }

    private String unzip(String compressed) {
        if (StringUtils.isEmpty(compressed)) {
            return compressed;
        } else {
            return decompress(decoder.decode(compressed));
        }
    }

    private String decompress(byte[] bytes) {
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), UTF_8);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

}