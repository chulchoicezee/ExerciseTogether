package com.exercise.together.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Bean implements Parcelable
{

    
    public Bean(){
    }
    
    public Bean(Parcel in)
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

    }

    public void readFromParcel(Parcel in){

    }
    
    public static final Parcelable.Creator<Bean> CREATOR = new Parcelable.Creator<Bean>()
			            {
			                public Bean createFromParcel(Parcel in)
			                {
			                    return new Bean(in);
			                }
			
			                public Bean[] newArray(int size)
			                {
			                    return new Bean[size];
			                }
			            };
}