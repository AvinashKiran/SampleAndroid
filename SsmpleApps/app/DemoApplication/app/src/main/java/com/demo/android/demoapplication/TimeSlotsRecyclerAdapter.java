package com.demo.android.demoapplication;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DELL on 7/15/2018.
 */

public class TimeSlotsRecyclerAdapter extends RecyclerView.Adapter<TimeSlotsRecyclerAdapter.MyHolder>{


    List<TimeSlotModel> mTimeSlotModels;

    private ITimeSlotListener mListener;

    private MyHolder mLastSelected;

    public interface ITimeSlotListener {
        void onClickTimeSlot(TimeSlotModel timeSlot);
    }


    public TimeSlotsRecyclerAdapter(List<TimeSlotModel> timeSlotModels, ITimeSlotListener listener) {
        mTimeSlotModels = timeSlotModels;
        mListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, null);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {

        holder.bind(mTimeSlotModels.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedPosition = holder.getAdapterPosition();

                if (mLastSelected != null) {
                    int lastSelectedPosition = mLastSelected.getAdapterPosition();
                    mLastSelected.rl_time.setBackgroundResource(R.drawable.custom_button_time_slot);
                    mLastSelected.time.setTextColor(Color.WHITE);
                    notifyItemChanged(lastSelectedPosition);

                }
                holder.rl_time.setBackgroundResource(R.drawable.custom_time_slot_white);
                holder.time.setTextColor(Color.BLACK);

                Log.d("TAG", "onClick: ");

                mLastSelected = holder;

                mListener.onClickTimeSlot(mTimeSlotModels.get(selectedPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTimeSlotModels.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rl_time;
        private TextView time;
        private TextView duration;


        public MyHolder(final View itemView) {
            super(itemView);
              rl_time = itemView.findViewById(R.id.rl_time);
              time = rl_time.findViewById(R.id.time);
              duration = itemView.findViewById(R.id.duration);




        }

        public void bind(TimeSlotModel timeSlotModel) {
            time.setText(timeSlotModel.getStartTime());
            duration.setText(timeSlotModel.getDuration());
        }
    }
}
