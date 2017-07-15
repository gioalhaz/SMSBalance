package com.smsbalance;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("NavAct", "Floating clicked");
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("NavAct", "ReadSMS Floating clicked");
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                checkAndRequestSmsReadingPermissions();
                readSMS();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // -----  ListView
        ListView listView = (ListView) findViewById(R.id.MainList);
        List<SMSContent> values = new ArrayList<SMSContent>();

        SMSContent c;
        c = new SMSContent();
        c.address = "LibertyBank"; c.date = new Date(); c.body = "trulala";
        values.add(c);

        c = new SMSContent();
        c.address = "LibertyBank"; c.date = new Date(); c.body = "trulala";
        values.add(c);

        c = new SMSContent();
        c.address = "LibertyBank"; c.date = new Date(); c.body = "trulala";
        values.add(c);

        MainListViewAdapter adapter = new MainListViewAdapter(getBaseContext(), values);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean checkAndRequestSmsReadingPermissions() {

        boolean result = false;
        if(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_ASK_PERMISSIONS);

            result = true; // ეს არაა სწორი! ქოლბექში ირკვევა არის თუ არა შედეგი https://developer.android.com/training/permissions/requesting.html
        }

        return result;
    }

    private void readSMS() {
        ArrayList<SMSContent> list = new ArrayList<SMSContent>();
        try {
            ContentResolver cr = getApplicationContext().getContentResolver();
            Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), null, null, null, null);
            //Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);

            if (cursor.moveToFirst()) { // must check the result to prevent exception

                int idIndex = cursor.getColumnIndexOrThrow("_id");
                int threadIdIndex = cursor.getColumnIndexOrThrow("thread_id");
                int addressIndex = cursor.getColumnIndexOrThrow("address");
                int dateIndex = cursor.getColumnIndexOrThrow("date");
                int bodyIndex = cursor.getColumnIndexOrThrow("body");
                int deletedIndex = cursor.getColumnIndexOrThrow("deleted");
                int typeIndex = cursor.getColumnIndexOrThrow("type");

                do {
                    SMSContent sms = new SMSContent();

                    sms.id = cursor.getInt(idIndex);
                    sms.threadId = cursor.getInt(threadIdIndex);
                    sms.address = cursor.getString(addressIndex);
                    sms.date = new Date(cursor.getInt(dateIndex));
                    sms.body = cursor.getString(bodyIndex);
                    sms.deleted = cursor.getInt(deletedIndex);
                    sms.type = cursor.getInt(typeIndex);

                    list.add(sms);
                    Log.d("SMS", Integer.toString(sms.id));
                    /*
                    String msgData = "";
                    for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                        msgData += " " + cursor.getColumnName(idx) + ":" + cursor.getString(idx);

                    }
                    Log.d("SMS", msgData);
                    */

                } while (cursor.moveToNext());
            } else {
                // empty box, no SMS
            }
        }
        catch(Exception x) {
            Log.d("Error", x.toString());
        }

    }

}
