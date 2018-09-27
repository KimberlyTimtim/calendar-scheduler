package com.example.calendar.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Event {

    private String eventId;
    private String title;
    private String location;
    private String eventDescription;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    // Flag for event duration:
    // 0 - not
    // 1 - wholeDay
    private int isWholeDay;

    // Flag for event recurrence:
    // 0 - does not repeat
    // 1 - daily
    // 2 - weekly (weekdays)
    // 3 - weekly (weekends)
    // 4 - monthly
    // 5 - annually
    private int isRecurring;

    private List<Reminder> remindersList;
    private List<String> recurringDaysList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getIsWholeDay() {
        return isWholeDay;
    }

    public void setIsWholeDay(int isWholeDay) {
        this.isWholeDay = isWholeDay;
    }

    public int getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(int isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<String> getRecurringDaysList() {
        return recurringDaysList;
    }

    public void setRecurringDaysList(List<String> recurringDayList) {
        this.recurringDaysList = recurringDayList;
    }

    public List<Reminder> getRemindersList() {
        return remindersList;
    }

    public void setRemindersList(List<Reminder> remindersList) {
        this.remindersList = remindersList;
    }

}