package com.exercise.together.util;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileInfo extends Bean
{
	/* regid */
	public String regid;
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
    
    public ProfileInfo(int _activity, String _location){
    	super();
    	activity = _activity;
    	location = _location;
    }
    
    public ProfileInfo(Parcel in)
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
        dest.writeString(regid);
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
    	regid = in.readString();
    	phoneNumber = in.readString();
    	email = in.readString();
    	activity = in.readInt();
    	latitude = in.readDouble();
    	longitude = in.readDouble();
    	location = in.readString();
    	hoursFrom = in.readInt();
    	hoursTo = in.readInt();
    }
    
    public static final Parcelable.Creator<ProfileInfo> CREATOR = new Parcelable.Creator<ProfileInfo>()
			            {
			                public ProfileInfo createFromParcel(Parcel in)
			                {
			                    return new ProfileInfo(in);
			                }
			
			                public ProfileInfo[] newArray(int size)
			                {
			                    return new ProfileInfo[size];
			                }
			            };
}