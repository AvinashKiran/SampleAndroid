package com.demo.android.demoapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends BaseTimerContainerFragment {

    public static final String TIMER_FRAGMENT = "TimerFragment";
    private AppCompatTextView text_timer;
    private AppCompatTextView text_am_pm;

    private Button mButtonNext;

    Timer timer;
    TimerTask timerTask;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();




    public TimerFragment() {
        // Required empty public constructor
    }
    public static TimerFragment newInstance(String param1, String param2) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);


      View v = inflater.inflate(R.layout.fragment_timer, container, false);

      text_timer = v.findViewById(R.id.text_time);
      text_am_pm = v.findViewById(R.id.text_am_pm);

      mButtonNext = v.findViewById(R.id.button_next);

      mButtonNext.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            addFragment(R.id.fragment_container, TimeSlotsFragment.newInstance(), TIMER_FRAGMENT);
          }
      });


        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        startTimer();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 10000);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimerTask();
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }



    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat =
                                new SimpleDateFormat("hh:mm a");


                        String strDate = simpleDateFormat.format(calendar.getTime());
                        String time = strDate.substring(0, 5);
                        String amPm = strDate.substring(strDate.length()-2, strDate.length());
                        text_timer.setText(time);
                        text_am_pm.setText(amPm);
                    }
                });
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
    protected void onBackPressed() {

    }


}
