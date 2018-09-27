package com.example.calendar.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.calendar.model.Event;
import com.example.calendar.model.Reminder;
import com.example.calendar.service.CalendarCacheService;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class CalendarCacheServiceImplTest {

    @Autowired
    private CalendarCacheService calendarCacheServiceImpl;

    @Test
    public void test() {
        List<String> recurringDaysList = new ArrayList<String>();
        recurringDaysList.add("spoon");
        recurringDaysList.add("knife");

        List<Reminder> remindersList = new ArrayList<Reminder>();
        Reminder reminder = new Reminder();
        reminder.setReminderText("Sample test reminder");
        remindersList.add(reminder);

        Event event = new Event();
        event.setEventId("00001");
        event.setEventDescription("Kimberly");
        event.setLocation("Cebu");
        event.setStartDate("2018-09-28");
        event.setStartTime("17:33:59+0800");
        event.setRemindersList(remindersList);
        event.setRecurringDaysList(recurringDaysList);

//        System.out.println(calendarCacheServiceImpl.storeEvent(event));
//        System.out.println(calendarCacheServiceImpl.removeEvent(event));
    }

}