package com.example.dictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HistoryListAdapter extends BaseAdapter {

    private List<String> listData;
    private LayoutInflater layoutInflater;

    public HistoryListAdapter(LayoutInflater aContext,  List<String> listData) {
        this.listData = listData;
        layoutInflater = aContext;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.history_item, null);
            holder = new ViewHolder();
            //holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.value = (TextView) convertView.findViewById(R.id.value);
            //holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String historyItem = this.listData.get(position);
        holder.value.setText(historyItem);
        //holder.arrow.setImageResource(historyItem.getArrowId());
        //holder.icon.setImageResource(historyItem.getIconId());

        return convertView;
    }



    static class ViewHolder {
        //ImageView icon;
        TextView value;
        //ImageView arrow;
    }

}
