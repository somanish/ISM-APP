package com.samsidx.ismappbeta.data;

public class ScheduleData {

    long id;
    int slot,day;
    String sub;
    // constructors
    public ScheduleData() {
    }
    public ScheduleData(String sub, int slot, int day) {
        this.sub = sub;
        this.slot = slot;
        this.day = day;
    }
    public ScheduleData(long id, String sub, int slot, int day) {
        this.id = id;
        this.sub = sub;
        this.slot = slot;
        this.day = day;
    }
    // setters
    public void setId(long id) {
        this.id = id;
    }
    public void setSub(String sub) {
        this.sub = sub;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }
    public void setDay(int day) {
        this.day = day;
    }
    // getters
    public long getId() {
        return this.id;
    }
    public String getSub() {
        return this.sub;
    }
    public int getSlot() {
        return this.slot;
    }
    public int getDay() {
        return this.day;
    }
}
