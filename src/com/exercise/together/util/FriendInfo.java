package com.exercise.together.util;

import android.os.Parcel;
import android.os.Parcelable;

public class FriendInfo extends Bean
{
    /* sex */
    public int gender;
    /* age */
    public int age;
    /* phone number */
    public String phoneNumber;
    public String email;
    /* activity */
    public int activity;
    
    public double latitude;
    public double longitude;
    public String location;
    
    public int hoursFrom;
    public int hoursTo;
    
    public FriendInfo(String name){
    	email = name;
    }
    
    public FriendInfo(Parcel in)
    {
    	readFromParcel(in);
    }

    @Override
    public int describeContents()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        // TODO Auto-generated method stub
        dest.writeInt(gender);
        dest.writeInt(age);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeInt(activity);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(location);
        dest.writeInt(hoursFrom);
        dest.writeInt(hoursTo);
    }

    public void readFromParcel(Parcel in){
    	gender = in.readInt();
    	age = in.readInt();
    	phoneNumber = in.readString();
    	email = in.readString();
    	activity = in.readInt();
    	latitude = in.readDouble();
    	longitude = in.readDouble();
    	location = in.readString();
    	hoursFrom = in.readInt();
    	hoursTo = in.readInt();
    }
    
    public static final Parcelable.Creator<FriendInfo> CREATOR = new Parcelable.Creator<FriendInfo>()
			            {
			                public FriendInfo createFromParcel(Parcel in)
			                {
			                    return new FriendInfo(in);
			                }
			
			                public FriendInfo[] newArray(int size)
			                {
			                    return new FriendInfo[size];
			                }
			            };
}