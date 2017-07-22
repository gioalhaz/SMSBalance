package com.smsbalance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smsbalance.data.DetailsListEntry;
import com.smsbalance.data.MainListEntry;

import java.util.List;

public class DetailsPopupListAdapter extends BaseAdapter {
    private final Context context;
    private List<DetailsListEntry> data;

    public DetailsPopupListAdapter(Context context) {
        this.context = context;
        this.data = null;
    }

    public void setData(List<DetailsListEntry> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return (data != null) ? data.size() : 0;
    }

    @Override
    public DetailsListEntry getItem(int position) {
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
            convertView = inflater.inflate(R.layout.details_popup_list_item, container, false);
        }

        if (data != null) {
            DetailsListEntry entry = data.get(position);
            TextView text;

            text = (TextView) convertView.findViewById(R.id.text_entity);
            text.setText(entry.getEntity());

            text = (TextView) convertView.findViewById(R.id.text_amount);
            text.setText(entry.getAmount());

            text = (TextView) convertView.findViewById(R.id.text_balance);
            text.setText(entry.getBalance());

            text = (TextView) convertView.findViewById(R.id.text_pos);
            text.setText(entry.getPos());
        }

        return convertView;
    }


}
