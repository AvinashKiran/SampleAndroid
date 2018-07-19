package com.demo.android.demoapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MeetingProgressFragment extends BaseTimerContainerFragment {


    private static final String TAG = "MeetingProgressFragment";

    private static final String ARG_PARAM1 = "time_slot";
    private static final String ARG_PARAM2 = "appTime";


    private static final long TEN_MILLIS = 10 * 60 * 1000;
    private static final int TEN_MINS = 600000;
    private static final int FIVE_MINS = 300000;


    private static final long ONE_SECOND =   1000;


//    private  long mStartTime ;

    // TODO: Rename and change types of parameters
//    private long mAppTime;


//    private boolean mEndMeetingFlag = false;

    private Button mButtonEndMeeting;
    private ProgressBar mProgressBar1;
    private ProgressBar mProgressBar2;
    private ProgressBar mProgressBar3;
    private ImageView mImageBell;
    private TextView mProgressText;
    private TextView mMeetingEndText;



//    private CountDownTimer mPbTimer;
    private long mTimeLeftInMillis;
    private long mExtendTimeLeftInMillis;
    private boolean isTimerExtended = false;

    protected boolean isTimerRuning =false;


    private int mProgressTimeLeft;

    private TextView mTextTimeLeft;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    private long pb2;
    private long pb3;
    private long pb1;


    private long mMeetingTimeMillis;
    private CountDownTimer mCountDownTimer;
    private CountDownTimer mPb1Timer;
    private CountDownTimer mPb2Timer;
    private CountDownTimer mPb3Timer;
    private AlertDialog mDialog;


    public MeetingProgressFragment() {
        // Required empty public constructor
    }
    public static MeetingProgressFragment newInstance(long meetingTimeInMillis) {
        MeetingProgressFragment fragment = new MeetingProgressFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, meetingTimeInMillis);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMeetingTimeMillis = getArguments().getLong(ARG_PARAM1);

            long currentTimeMillis = System.currentTimeMillis();
            mTimeLeftInMillis = mMeetingTimeMillis - currentTimeMillis;

            mExtendTimeLeftInMillis = mTimeLeftInMillis;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_meeting_progress, container, false);

      mProgressBar1  = v.findViewById(R.id.progressBar1);
      mProgressBar2  = v.findViewById(R.id.progressBar2);
      mProgressBar3  = v.findViewById(R.id.progressBar3);

        mImageBell     = v.findViewById(R.id.image_bell);
      mProgressText     = v.findViewById(R.id.text_meeting_progress);
      mMeetingEndText   = v.findViewById(R.id.text_meeting_end_msg);



      mTextTimeLeft = v.findViewById(R.id.text_time_left);

        mButtonEndMeeting = v.findViewById(R.id.button_end_meeting);

        mButtonEndMeeting.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              isTimerExtended = true;
              isTimerRuning = false;
              cancelAllTimers();

              BottomPopupFragment bottomPopupFragment =
                      BottomPopupFragment.newInstance();
              bottomPopupFragment.show(getChildFragmentManager(),
                      "bottom_popup");

          }
      });


        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("CNU", "onResume: ");
        mProgressText.setVisibility(View.VISIBLE);
//        mProgressBar1.setMax((int) (mTimeLeftInMillis / 1000) / 60);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String today = dateFormat.format(c.getTimeInMillis());

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("hh:mm a");

        String time = simpleDateFormat.format(mMeetingTimeMillis);

        mMeetingEndText.setText("Ends at " + time + " on " + today);

        long diffMillis = mTimeLeftInMillis;

        int diffMins = (int) ( diffMillis / 1000) / 60;
        int diffSecs = (int) (diffMillis / 1000)  % 60;

        Log.d(TAG, "onResume diffMins  : " +diffMins);
        Log.d(TAG, "onResume diffSecs  : " +diffSecs);

        if (diffMillis > TEN_MINS) {
            pb2 = FIVE_MINS;
            pb3 = FIVE_MINS;
            pb1 = diffMillis - TEN_MINS;  // mins
            Log.d(TAG, "onResume  pb1_white: " + pb1);
        } else {
            pb1 = 0;
//            int diffMins = (int) ((diffMillis/1000)/60);

            if (diffMillis > FIVE_MINS) {
                if (diffMillis % 2 == 0) {
                    pb2 = diffMillis / 2;
                    pb3 = pb2;
                } else {
                    pb2 = diffMillis / 2;
                    pb3 = diffMillis - pb2;
                }
            } else {
                pb2 = 0;
                pb3 = diffMillis;
            }
        }

            Log.d("CNU", "onResume pb1_white &  pb_2_3_white & pb3 : " + pb1 + " & " + pb2 + " & " + pb3);

        startTimer();

        if (pb1 > 0) {

            if (mPb1Timer == null) {
                Log.e("CNU", "onResume Pb1Timer starting here !!: ");
                mPb1Timer = new Pb1Timer(pb1, ONE_SECOND);
                mPb1Timer.start();
            }
        } else if (pb2 > 0){

            if (mPb2Timer == null) {
                Log.e("CNU", "onResume Pb2Timer starting here !!: ");
                mPb2Timer = new Pb2Timer(pb2, ONE_SECOND);
                mPb2Timer.start();
            }
        } else if (pb3 > 0){

            if (mPb3Timer == null) {
                Log.e("CNU", "onResume Pb3Timer starting here !!: ");
                mPb3Timer = new Pb3Timer(pb3, ONE_SECOND);
                mPb3Timer.start();
            }
        }

    }


   public class Pb1Timer extends CountDownTimer {

       public Pb1Timer(long millisInFuture, long countDownInterval) {
           super(millisInFuture, countDownInterval);
           mProgressBar1.setMax((int) (millisInFuture / 1000));
       }

       @Override
       public void onTick(long millisUntilFinished) {
           int progress = (int) (millisUntilFinished/1000);
           Log.d(TAG, "onTick Pb1Timer progress >>   : " + progress);
           mProgressBar1.setProgress(mProgressBar1.getMax()-progress);
           // TODO : need to work on for 1st time
           if (!isTimerExtended) {
               mProgressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.pb1_white));
               mProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.pb1_white));
               mProgressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.pb1_white));
           } else {

               mProgressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.pb1_red));
               mProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gray));
               mProgressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gray));
               mTextTimeLeft.setTextColor(Color.RED);
           }

       }

       @Override
       public void onFinish() {
           Log.d(TAG, "pb1Timer onFinish  pb_2_3_white : " + pb2);
           pb1 = 0;
           if (pb2 > 0) {
               if (mPb2Timer != null) {
                   mPb2Timer.cancel();
               }
               mPb2Timer = new Pb2Timer(pb2, ONE_SECOND);
               mPb2Timer.start();
           }
       }
   }

    public class Pb2Timer extends CountDownTimer {

        public Pb2Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mProgressBar2.setMax((int) (millisInFuture / 1000));
            Log.e("CNU", "Pb2Timer calling playAlarm() !!: ");
            playAlarm();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            Log.d(TAG, "onTick Pb2Timer progress >>   : " + progress);
            mProgressBar2.setProgress(mProgressBar2.getMax()-progress);
            mProgressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gray));
            mProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.pb_2_3_yellow));
            mProgressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.pb_2_3_yellow));
            mTextTimeLeft.setTextColor(getResources().getColor(R.color.color_yellow));
        }

        @Override
        public void onFinish() {
            Log.d(TAG, "pb2Timer onFinish pb3 : " + pb3);
            pb2 = 0;
            if (pb3 > 0) {
                Log.e("CNU", "onFinish of pb2Timer Pb3 starting here !!!! ");
                if (mPb3Timer != null) {
                    mPb3Timer.cancel();
                }
                mPb3Timer = new Pb3Timer(pb3, ONE_SECOND);
                mPb3Timer.start();
            }
        }
    }

    public class Pb3Timer extends CountDownTimer {

        public Pb3Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mProgressBar3.setMax((int) (millisInFuture / 1000));
            Log.e("CNU", "Pb3Timer calling playAlarm() !!: ");
            playAlarm();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int progress = (int) (millisUntilFinished/1000);
            Log.d(TAG, "onTick Pb3Timer progress >>   : " + progress);

            mProgressBar3.setProgress(mProgressBar3.getMax()-progress);
            mProgressBar1.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gray));
            mProgressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.pb_gray));
            mProgressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.pb_2_3_yellow));
            mTextTimeLeft.setTextColor(getResources().getColor(R.color.color_yellow));
        }

        @Override
        public void onFinish() {
            pb3 = 0;
            Log.d(TAG, "pb3Timer onFinish: ");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("CNU", "onPause: ");
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
    }


    public void startTimer() {


            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
//                Log.d(TAG, "onTick mTimeLeftInMillis : " + mTimeLeftInMillis);
                    isTimerRuning = true;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {

                    if (mPb1Timer != null) mPb1Timer.cancel();

                    if (!isTimerExtended) {
                        isTimerExtended = true;
                        mTimeLeftInMillis = mTimeLeftInMillis + mExtendTimeLeftInMillis;

                        mProgressBar2.setProgress(0);
                        mProgressBar3.setProgress(0);
                        Log.e("CNU", "onTick Pb1Timer starting here in extend !!!!: ");
                        if (mPb1Timer != null) {
                            mPb1Timer.cancel();
                        }
                        mPb1Timer = new Pb1Timer(mTimeLeftInMillis, 1000);
                        mPb1Timer.start();

                        if (mCountDownTimer != null) {
                            mCountDownTimer.cancel();
                        }

                        startTimer();
                    }

//                mProgressText.setVisibility(View.INVISIBLE);
                    Log.d(TAG, "check onFinish isTimerExtended >>>>>>>>>>>>  " + isTimerExtended);
                }
            }.start();
    }

    private void updateCountDownText() {

        int minutes = (int) ((mTimeLeftInMillis / 1000) / 60 );
        int seconds = (int) (mTimeLeftInMillis / 1000)  % 60;
        int hours = (int) (((mTimeLeftInMillis / 1000) / 60 ) / 60);
        if (hours > 0) {
            minutes = minutes - hours * 60;
        }

        String timeLeftFormatted  = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);


        mTextTimeLeft.setText(timeLeftFormatted);

    }

    private void playAlarm() {

        if (mTimeLeftInMillis < 25*60*60*1000) {  // play only if more than 25 mins
            return;
        }
        Log.e("CNU", "playAlarm() called !!: ");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean sound = sharedPreferences.getBoolean(getResources().getString(R.string.key_enable_sound), false);
        boolean vibrate = sharedPreferences.getBoolean(getResources().getString(R.string.key_enable_vibration), false);

        if (vibrate) {
            Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }


        if (sound) {
            MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.alarm_tone);
            mediaPlayer.start();
        }
        mImageBell.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mImageBell.setVisibility(View.INVISIBLE);
            }
        }, 5000);


    }

    @Override
    protected void onBackPressed() {
        Log.e(TAG, ">>>>>>>>>>>>>>>>>>> onBackPressed >>>>>>>>>>>>>>>>>> : " + isTimerRuning);
        if (isTimerRuning) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                    .setTitle("Your meeting is active")
                    .setMessage("Do you want to end your meeting?")
                    .setPositiveButton("End", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            cancelAllTimers();
                            addFragment(R.id.fragment_container, new TimerFragment());
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            mDialog = builder.create();
            mDialog.show();
        } else {
            addFragment(R.id.fragment_container, new TimerFragment());
        }
    }

    private void cancelAllTimers() {

        Log.d(TAG, "cancelAllTimers: ");
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        if (mPb1Timer != null) {
            mPb1Timer.cancel();
            mPb1Timer = null;
        }
        if (mPb2Timer != null) {
            mPb2Timer.cancel();
            mPb2Timer = null;
        }
        if (mPb3Timer != null) {
            mPb3Timer.cancel();
            mPb3Timer = null;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDialog != null)
        {
            mDialog.dismiss();
        }
        cancelAllTimers();
    }


}
