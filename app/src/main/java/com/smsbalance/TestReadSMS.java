package com.smsbalance;

import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

/**
 * Created by gioal on 18.06.2017.
 */

public class TestReadSMS implements View.OnClickListener {

    public static final String INBOX = "content://sms/inbox";
    public static final String SENT = "content://sms/sent";
    public static final String DRAFT = "content://sms/draft";

    @Override
    public void onClick(View v) {

        Log.d("ReadSMS", "Floating clicked");
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}
