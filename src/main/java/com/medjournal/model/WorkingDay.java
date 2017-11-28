package com.medjournal.model;

import com.medjournal.exception.IllegalTimeFormatException;
import com.medjournal.util.RegexpUtil;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkingDay {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;

    public WorkingDay() {
    }

    public WorkingDay(DayOfWeek dayOfWeek, String startTime, String endTime, String room) throws IllegalTimeFormatException {
        this.dayOfWeek = dayOfWeek;
        this.startTime = validateTime(startTime);
        this.endTime = validateTime(endTime);
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartTime() {
        return startTime.toString();
    }

    public void setStartTime(String startTime) throws IllegalTimeFormatException {
        this.startTime = validateTime(startTime);
    }

    public String getEndTime() {
        return endTime.toString();
    }

    public void setEndTime(String endTime) throws IllegalTimeFormatException {
        this.endTime = validateTime(endTime);
    }

    private LocalTime validateTime(String time) throws IllegalTimeFormatException {
        if (time.matches(RegexpUtil.TIME_PATTERN)) {
            return LocalTime.parse(time);
        } else {
            throw new IllegalTimeFormatException();
        }
    }
}
