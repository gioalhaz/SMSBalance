package com.smsbalance.data;

import java.util.ArrayList;
import java.util.List;

public class MainListEntry {
    private long ID;
    private String group;
    private String name;
    private String value;

    public List<DetailsListEntry> details = new ArrayList<>();

    public long getID() {
        return ID;
    }

    public void setID(long key) {
        this.ID = key;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String value) {
        this.group = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ID: " + Long.toString(ID)
                + "; " + group
                + "; " + name + " Available balance: "
                + value;
    }
}
