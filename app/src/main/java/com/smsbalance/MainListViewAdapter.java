package com.smsbalance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Context;

import java.util.List;

/**
 * Created by gioal on 15.07.2017.
 */

public class MainListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<SMSContent> values;

    public MainListViewAdapter(Context context, List<SMSContent> values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public SMSContent getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return values.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, container, false);
        }

        SMSContent record = values.get(position);
        TextView text;

        text = (TextView) convertView.findViewById(R.id.text_source);
        text.setText(record.address);
        text = (TextView) convertView.findViewById(R.id.text_entity);
        text.setText(record.date.toString());
        text = (TextView) convertView.findViewById(R.id.text_amount);
        text.setText(record.body);

        return convertView;
    }

}
