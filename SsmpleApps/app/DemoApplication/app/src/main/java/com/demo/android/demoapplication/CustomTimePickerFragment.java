package com.demo.android.demoapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class CustomTimePickerFragment extends BaseTimerContainerFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TimePicker mTimePicker;
    private Button mButtonStartMeeting;

    @Override
    protected void onBackPressed() {

    }

    public CustomTimePickerFragment() {
        // Required empty public constructor
    }

    public static CustomTimePickerFragment newInstance(String param1, String param2) {
        CustomTimePickerFragment fragment = new CustomTimePickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_custom_time_picker, container, false);

        mTimePicker = v.findViewById(R.id.timePicker);
        mButtonStartMeeting = v.findViewById(R.id.button_start_meeting);
        mButtonStartMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   int hour = mTimePicker.getCurrentHour();
                   int minute = mTimePicker.getCurrentMinute();

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                long meetingInMillis = calendar.getTimeInMillis();

                long currentTimeMillis = System.currentTimeMillis();
                if ((meetingInMillis - currentTimeMillis) > 0) {

                    MeetingProgressFragment progressFragment = MeetingProgressFragment.
                            newInstance(meetingInMillis);
                    addFragment(R.id.fragment_container, progressFragment);
                } else {

                    Toast.makeText(getContext(), "Select future time", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
