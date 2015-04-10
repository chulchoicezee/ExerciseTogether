package com.exercise.together.util;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileInfo extends Bean
{
	/* unique id*/
	public int id;
	/* regid max 200*/ 
	public String regid;
	/* name */
	public String name;
    /* gender 0 : man, 1 : woman*/
	public int gender;
    /* age */
	public int age;
    /* sports */
	public int sports;
    /* location */
	public String location;
    /* level */
	public int level;
	public String phone;
	public String email;
	public int time;
	public int allow_disturbing;
    
	public int gender_filter;
	public int age_filter;
	public int location_filter;
	public int level_filter;
	public int time_filter;
    
    ProfileInfo(Builder builder){
    	this.id = builder.id;
    	this.regid = builder.regid;
    	this.name = builder.name;
    	this.gender = builder.gender;
    	this.age = builder.age;
    	this.sports = builder.sports;
    	this.location = builder.location;
    	this.level = builder.level;
    	this.phone = builder.phone;
    	this.email = builder.email;
    	this.time = builder.time;
    	this.allow_disturbing = builder.allow_disturbing;
    	this.gender_filter = builder.gender_filter;
    	this.age_filter = builder.age_filter;
    	this.location_filter = builder.location_filter;
    	this.level_filter = builder.level_filter;
    	this.time_filter = builder.time_filter;
    }
    
    public static class Builder {
    	
    	private int id;
    	private String regid;
    	private String name;
        private int gender;
        private int age;
        private int sports;
        private String location;
        private int level;
    	private String phone;
    	private String email;
    	private int time;
    	private int allow_disturbing;
        private int gender_filter;
    	private int age_filter;
    	private int location_filter;
    	private int level_filter;
    	private int time_filter;
        
        public Builder setid(int id){this.id = id; return this; }
        public Builder setRegid(String regid){this.regid = regid; return this; }
        public Builder setName(String name){this.name = name; return this; }
        public Builder setGender(int gender){this.gender = gender; return this; }
        public Builder setAge(int age){this.age = age; return this; }
        public Builder setSports(int sports){this.sports = sports; return this; }
        public Builder setLocation(String location){this.location = location; return this; }
        public Builder setLevel(int level){this.level = level; return this; }
        public Builder setPhone(String phone){this.phone = phone; return this; }
        public Builder setEmail(String email){this.email = email; return this; }
        public Builder setTime(int time){this.time = time; return this; }
        public Builder setAllowDisturbing(int allow_disturbing){this.allow_disturbing = allow_disturbing; return this; }
        public Builder setGenderFilter(int gender_filter){this.gender_filter = gender_filter; return this; }
        public Builder setAgeFilter(int age_filter){this.age_filter = age_filter; return this; }
        public Builder setLocationFilter(int location_filter){this.location_filter = location_filter; return this; }
        public Builder setLevelFilter(int level_filter){this.level_filter = level_filter; return this; }
        public Builder setTimeFilter(int time_filter){this.time_filter = time_filter; return this; }
        
        public ProfileInfo build(){
        	return new ProfileInfo(this);
        }
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
    	dest.writeInt(id);
        dest.writeString(regid);
    	dest.writeString(name);
        dest.writeInt(gender);
        dest.writeInt(age);
        dest.writeInt(sports);
        dest.writeString(location);
        dest.writeInt(level);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeInt(time);
        dest.writeInt(allow_disturbing);
        dest.writeInt(gender_filter);
        dest.writeInt(age_filter);
        dest.writeInt(location_filter);
        dest.writeInt(level_filter);
        dest.writeInt(time_filter);
    }

    public void readFromParcel(Parcel in){
    	id = in.readInt();
    	regid = in.readString();
    	name = in.readString();
    	gender = in.readInt();
    	age = in.readInt();
    	sports = in.readInt();
    	location = in.readString();
    	level = in.readInt();
    	phone = in.readString();
    	email = in.readString();
    	time = in.readInt();
    	allow_disturbing = in.readInt();
    	gender_filter = in.readInt();
    	age_filter = in.readInt();
    	location_filter = in.readInt();
    	level_filter = in.readInt();
    	time_filter = in.readInt();
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