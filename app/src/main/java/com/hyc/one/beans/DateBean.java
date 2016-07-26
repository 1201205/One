package com.hyc.one.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class DateBean implements Parcelable {
    public String date;
    public String realDate;

    public DateBean(String date, String realDate) {
        this.date = date;
        this.realDate = realDate;
    }


    @Override public int describeContents() { return 0; }


    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.realDate);
    }


    protected DateBean(Parcel in) {
        this.date = in.readString();
        this.realDate = in.readString();
    }


    public static final Parcelable.Creator<DateBean> CREATOR = new Parcelable.Creator<DateBean>() {
        @Override public DateBean createFromParcel(Parcel source) {return new DateBean(source);}


        @Override public DateBean[] newArray(int size) {return new DateBean[size];}
    };
}
