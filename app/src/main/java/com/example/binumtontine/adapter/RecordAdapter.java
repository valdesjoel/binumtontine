package com.example.binumtontine.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.binumtontine.R;
import com.example.binumtontine.activity.adherent.Record;

import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private Context recordContext;
    private List<Record> recordList;

    public RecordAdapter(Context context, List<Record> records) {
        recordList = records;
        recordContext = context;
    }

    public void add(Record record) {
        recordList.add(record);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }
    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecordViewHolder holder;

        if (view ==null){
            LayoutInflater recordInflater = (LayoutInflater)
                    recordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = recordInflater.inflate(R.layout.record, null);

            holder = new RecordViewHolder();
            holder.ageView = (TextView) view.findViewById(R.id.record_age);
            holder.nameView = (TextView) view.findViewById(R.id.record_name);
            holder.positionView = (TextView) view.findViewById(R.id.record_position);
            holder.addressView = (TextView) view.findViewById(R.id.record_address);
            view.setTag(holder);

        }else {
            holder = (RecordViewHolder) view.getTag();
        }

        Record record = (Record) getItem(i);
        holder.nameView.setText(record.name);
        holder.ageView.setText(record.age);
        holder.positionView.setText(record.position);
        holder.addressView.setText(record.address);
        return view;
    }

    private static class RecordViewHolder {

        public TextView nameView;
        public TextView positionView;
        public TextView ageView;
        public TextView addressView;
    }
}
