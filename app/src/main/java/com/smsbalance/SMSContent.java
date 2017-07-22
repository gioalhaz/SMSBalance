package com.smsbalance;

import com.smsbalance.processing.SMSParser;
import java.util.Date;

public class SMSContent {

    public static int TYPE_INBOX = 1;
    public static int TYPE_SENT = 2;
    public static int TYPE_DRAFT = 3;

    public int id;
    public int threadId;
    public String address;
    public Date date;
    public String body;
    public int deleted;
    public int type;

    public SMSParser.Result data;

    public String getUniqueKey() {
        return address + data.getCardCoalesce();
    }
}
