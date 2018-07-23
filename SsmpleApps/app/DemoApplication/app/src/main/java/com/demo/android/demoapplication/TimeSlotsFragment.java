package com.demo.android.demoapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class TimeSlotsFragment extends BaseTimerContainerFragment implements TimeSlotsRecyclerAdapter.ITimeSlotListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TIME_SLOT_FRAGMENT = "TimeSlotFragment";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final long MEETING_25 = 25 * 60 * 1000;
    private final long MEETING_50 = 50 * 60 * 1000;
    private final long MEETING_120 = (1 * 60 * 60 * 1000) +  (20 * 60 * 1000);
    private final long MEETING_150 = (1 * 60 * 60 * 1000) +  ( 50 * 60 * 1000);
    private final long MEETING_220 = (2 * 60 * 60 *  1000) + ( 20 * 60 * 1000);
    private final long MEETING_250 = (2 * 60 * 60 *  1000) + ( 50 * 60 * 1000);


    private Button mButtonStartMeeting;
    private TextView mTextViewCustomMeeting;
    private TimeSlotModel mSelectedTimeSlot;
    private long mAppTime;

    private RecyclerView mRecyclerView;

    public TimeSlotsFragment() {
        // Required empty public constructor
    }

    public static TimeSlotsFragment newInstance() {
        TimeSlotsFragment fragment = new TimeSlotsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_time_slots, container, false);
//        GridView gridview = (GridView) v.findViewById(R.id.gridview);

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        TimeSlotsRecyclerAdapter adapter = new TimeSlotsRecyclerAdapter(getTimeSlots(), this);
        mRecyclerView.setAdapter(adapter);

        mButtonStartMeeting = v.findViewById(R.id.button_start_meeting);
        mTextViewCustomMeeting = v.findViewById(R.id.text_custom_timer);

        mButtonStartMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedTimeSlot == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                            .setTitle(R.string.mojo)
                            .setMessage("Please select meeting end time")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                    return;
                }
                MeetingProgressFragment progressFragment = MeetingProgressFragment.
                        newInstance(mSelectedTimeSlot.getDurationInMillis());
                addFragment(R.id.fragment_container, progressFragment, TIME_SLOT_FRAGMENT);
            }
        });


//        gridview.setAdapter(new TimeSlotsAdapter(getContext(), getTimeSlots()));
//        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                mSelectedTimeSlot = getTimeSlots().get(position);
//
//            }
//        });

        mTextViewCustomMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFragment(R.id.fragment_container, new CustomTimePickerFragment(), TIME_SLOT_FRAGMENT);
            }
        });
        return v;
    }

    private List<TimeSlotModel> getTimeSlots() {


        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat simpleDateFormat =
//                new SimpleDateFormat("hh:mm a");

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);


        if (minutes >= 51) {
            hours = hours + 1;
            minutes = 0;
        } else if (minutes >= 0 && minutes < 20) {
            minutes = 0;
        } else if (minutes >= 20) {
            minutes = 30;
        }

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 59);

        mAppTime = calendar.getTimeInMillis();


        final long slot25 = calendar.getTimeInMillis() + MEETING_25;
        final long slot50 = calendar.getTimeInMillis() + MEETING_50;
        final long slot120 = calendar.getTimeInMillis() + MEETING_120;
        final long slot150 = calendar.getTimeInMillis() + MEETING_150;
        final long slot220 = calendar.getTimeInMillis() + MEETING_220;
        final long slot250 = calendar.getTimeInMillis() + MEETING_250;

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("hh:mm a");
        final String slot_25 = simpleDateFormat.format(slot25);
        final String slot_50 = simpleDateFormat.format(slot50);
        final String slot_120 = simpleDateFormat.format(slot120);
        final String slot_150 = simpleDateFormat.format(slot150);
        final String slot_220 = simpleDateFormat.format(slot220);
        final String slot_250 = simpleDateFormat.format(slot250);

        return new ArrayList<TimeSlotModel>() {
            {
                add(new TimeSlotModel(slot_25, "(25 MIN)", slot25, MEETING_25));
                add(new TimeSlotModel(slot_50, "(50 MIN)", slot50, MEETING_50));
                add(new TimeSlotModel(slot_120, "(1 HR 20 MIN)", slot120, MEETING_120));
                add(new TimeSlotModel(slot_150, "(1 HR 50 MIN)", slot150, MEETING_150));
                add(new TimeSlotModel(slot_220, "(2 HR 20 MIN)", slot220, MEETING_220));
                add(new TimeSlotModel(slot_250, "(2 HR 50 MIN)", slot250, MEETING_250));
            }
        };
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClickTimeSlot(TimeSlotModel timeSlot) {

        mSelectedTimeSlot = timeSlot;
    }

    @Override
    protected void onBackPressed() {

    }
}
