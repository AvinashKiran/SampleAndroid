package com.demo.android.demoapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 7/14/2018.
 */

class TimeSlotsAdapter extends BaseAdapter {

    private Context mContext;

    private List<TimeSlotModel> mTimeSlots;

    public TimeSlotsAdapter(Context context, List<TimeSlotModel> timeSlots) {
        mContext = context;
        mTimeSlots = timeSlots;
    }

    @Override
    public int getCount() {
        return mTimeSlots.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimeSlotModel timeSlotModel = mTimeSlots.get(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
        }

        final TextView time = convertView.findViewById(R.id.time);
        final TextView duration = convertView.findViewById(R.id.duration);

        time.setText(timeSlotModel.getStartTime());
        duration.setText(timeSlotModel.getDuration());

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                time.setAlpha(0.5f);
//                duration.setAlpha(0.5f);
//            }
//        });


        return convertView;
    }
}
