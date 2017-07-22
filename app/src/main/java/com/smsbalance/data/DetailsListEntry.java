package com.smsbalance.data;

/**
 * Created by gioal on 16.07.2017.
 */

public class DetailsListEntry {
    private long ID;
    private String entity;
    private String amount;
    private String balance;
    private String pos;

    public long getID() {
        return ID;
    }

    public void setID(long key) {
        this.ID = key;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String value) {
        this.entity = value;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String value) {
        this.amount = value;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String value) {
        this.balance = value;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String value) {
        this.pos = value;
    }
}
