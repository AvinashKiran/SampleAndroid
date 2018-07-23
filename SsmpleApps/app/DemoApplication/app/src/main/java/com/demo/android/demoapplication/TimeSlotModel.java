package com.demo.android.demoapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DELL on 7/14/2018.
 */

public class TimeSlotModel implements Parcelable{

    private String startTime;
    private String duration;
    private long durationInMillis;
    private long meetingDuration;

    public TimeSlotModel(String startTime, String duration, long durationInMillis, long meetingDuration) {
        this.startTime = startTime;
        this.duration = duration;
        this.durationInMillis = durationInMillis;
        this.meetingDuration = meetingDuration;
    }

    protected TimeSlotModel(Parcel in) {
        startTime = in.readString();
        duration = in.readString();
        durationInMillis = in.readLong();
        meetingDuration = in.readLong();
    }

    public static final Creator<TimeSlotModel> CREATOR = new Creator<TimeSlotModel>() {
        @Override
        public TimeSlotModel createFromParcel(Parcel in) {
            return new TimeSlotModel(in);
        }

        @Override
        public TimeSlotModel[] newArray(int size) {
            return new TimeSlotModel[size];
        }
    };

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getDurationInMillis() {
        return durationInMillis;
    }

    public void setDurationInMillis(long durationInMillis) {
        this.durationInMillis = durationInMillis;
    }

    public long getMeetingDuration() {
        return meetingDuration;
    }

    public void setMeetingDuration(long meetingDuration) {
        this.meetingDuration = meetingDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startTime);
        dest.writeString(duration);
        dest.writeLong(durationInMillis);
        dest.writeLong(meetingDuration);
    }
}
