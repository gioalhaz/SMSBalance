package com.smsbalance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.smsbalance.data.MainListEntry;

import java.util.List;

/**
 * Created by gioal on 15.07.2017.
 */

public class MainListViewAdapter extends BaseAdapter {

    private final Context context;
    private List<MainListEntry> data;

    public MainListViewAdapter(Context context) {
        this.context = context;
        this.data = null;
    }

    public void setData(List<MainListEntry> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return (data != null) ? data.size() : 0;
    }

    @Override
    public MainListEntry getItem(int position) {
        return (data != null) ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (data != null) ? data.get(position).getID() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, container, false);
        }

        if (data != null) {
            MainListEntry entry = data.get(position);
            TextView text;

            ImageView img = (ImageView) convertView.findViewById(R.id.img);
            img.setImageResource(getEntryImageID(entry));

            text = (TextView) convertView.findViewById(R.id.text_source);
            text.setText(entry.getGroup());

            text = (TextView) convertView.findViewById(R.id.text_entity);
            text.setText(entry.getName());

            text = (TextView) convertView.findViewById(R.id.text_amount);
            text.setText(entry.getValue());
        }

        return convertView;
    }

    private int getEntryImageID(MainListEntry entry) {
        if (entry.getGroup().compareTo("LibertyBank") == 0)
            return R.drawable.le_libertybank;
        else if (entry.getGroup().compareTo("TBC SMS") == 0)
            return R.drawable.le_tbcbank;
        else
            return R.drawable.le_unknown;
    }

}
