package org.grokking.challenges.model;

/**
 * Created by vinhdp on 29/11/17.
 */
public class Challenge {
    private int id;
    private String name;
    private long date;
    private long startTime;
    private long endTime;

    public Challenge() {
    }

    public Challenge(int id, String name, long date, long startTime, long endTime) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
