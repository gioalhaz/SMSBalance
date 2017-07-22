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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.smsbalance.data.DetailsListEntry;
import com.smsbalance.data.MainListEntry;
import com.smsbalance.processing.SMSParserMan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // -----  ListView
        ListView listView = (ListView) findViewById(R.id.MainList);
        //List<SMSContent> values = new ArrayList<SMSContent>();

        final MainListViewAdapter listViewAdapter = new MainListViewAdapter(getBaseContext());
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainListEntry entry = (MainListEntry)parent.getItemAtPosition(position);

                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.details_popup, null);

// ================
                ListView listView = (ListView) popupView.findViewById(R.id.list_details);

                final DetailsPopupListAdapter listViewAdapter = new DetailsPopupListAdapter(getBaseContext());
                listView.setAdapter(listViewAdapter);

                listViewAdapter.setData(entry.details);

// ================
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                Button btnDismiss = (Button)popupView.findViewById(R.id.button_close);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }});

                popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("NavAct", "ReadSMS Floating clicked");
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                checkAndRequestSmsReadingPermissions();

                List<MainListEntry> list = readSMS();
                listViewAdapter.setData(list);
                listViewAdapter.notifyDataSetChanged();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

    private ArrayList<MainListEntry> readSMS() {

        ArrayList<MainListEntry> list = new ArrayList<MainListEntry>();
        try {
            ContentResolver cr = getApplicationContext().getContentResolver();
            String[] projection =  {"_id", "address", "date", "body"};
            Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), projection, null, null, "date asc");

            if (cursor.moveToFirst()) { // must check the result to prevent exception

                SMSParserMan parser = SMSParserMan.createSMSParserMan();

                int idIndex = cursor.getColumnIndexOrThrow("_id");
                //int threadIdIndex = cursor.getColumnIndexOrThrow("thread_id");
                int addressIndex = cursor.getColumnIndexOrThrow("address");
                int dateIndex = cursor.getColumnIndexOrThrow("date");
                int bodyIndex = cursor.getColumnIndexOrThrow("body");
                //int deletedIndex = cursor.getColumnIndexOrThrow("deleted");
                //int typeIndex = cursor.getColumnIndexOrThrow("type");

                HashMap<String, MainListEntry> map = new HashMap<>();

                //int count = 0;
                do {
                    SMSContent sms = new SMSContent();

                    sms.id = cursor.getInt(idIndex);
                    //sms.threadId = cursor.getInt(threadIdIndex);
                    sms.address = cursor.getString(addressIndex);
                    sms.date = new Date(cursor.getLong(dateIndex));
                    sms.body = cursor.getString(bodyIndex);
                    //sms.deleted = cursor.getInt(deletedIndex);
                    //sms.type = cursor.getInt(typeIndex);

                    sms.data = parser.parseSMS(sms.address, sms.body);

                    if (sms.data != null) {
                        //count++;

                        String key = sms.address + sms.data.getCardCoalesce();
                        MainListEntry entry = map.get(key);
                        if (entry == null) {
                            entry = new MainListEntry();

                            entry.setID(sms.id);
                            entry.setGroup(sms.address);
                            entry.setName(sms.data.getCardCoalesce());
                            entry.setValue(sms.data.getBalance() + " " + sms.data.getBalanceCurrency());

                            map.put(key, entry);
                        }
                        else {
                            entry.setValue(sms.data.getBalance() + " " + sms.data.getBalanceCurrency());
                        }

                        DetailsListEntry details = new DetailsListEntry();
                        details.setPos(sms.data.getPos());
                        details.setBalance(sms.data.getBalance() + " " + sms.data.getBalanceCurrency());
                        details.setAmount(sms.data.getAmount() + " " + sms.data.getAmountCurrency());
                        details.setEntity(sms.data.getCardCoalesce());

                        entry.details.add(details);

                        Log.d("SMS", Integer.toString(sms.id));
                    }
                    else {
                        Log.d("SMS", "Cant parse");
                    }

                } while (cursor.moveToNext());

                for(MainListEntry entry: map.values()) {
                    list.add(entry);
                }

            } else {
                // empty box, no SMS
            }
        }
        catch(Exception x) {
            Log.d("Error", x.toString());
        }

        return list;
    }

}
