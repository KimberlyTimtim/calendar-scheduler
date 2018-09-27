package com.example.calendar.service;

import com.example.calendar.model.Event;

public interface CalendarCacheService {

    public boolean storeEvent(Event event);

    public boolean removeEvent(Event event);

    public Event retrieveEventByTitle(String title);

    public Event retrieveEventByDate(String startDateTime, String endDateTime);

    public Event retrieveEventByDate(String location);

}