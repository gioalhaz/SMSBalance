package com.smsbalance;

import java.util.Date;

/**
 * Created by gioal on 02.07.2017.
 */

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
}
