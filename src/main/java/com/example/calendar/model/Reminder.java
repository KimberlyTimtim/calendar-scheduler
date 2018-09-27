package com.example.calendar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Reminder {

    // state of the reminder:
    // 0 - singleActive
    // 1 - recurringActive
    // 2 - dismissed
    private int reminderState;

    private String reminderText;

    public Reminder() {
        reminderState = 0;
    }

    public String getReminderText() {
        return reminderText;
    }

    public void setReminderText(String reminderText) {
        this.reminderText = reminderText;
    }

    public int getReminderState() {
        return reminderState;
    }

    public void setReminderState(int reminderState) {
        this.reminderState = reminderState;
    }

}