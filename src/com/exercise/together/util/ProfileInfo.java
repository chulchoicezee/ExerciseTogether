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
    /* phone number */
    public String phoneNumber;
    /* email */
    public String email;
    /* favorite_sports */
    public int activity;
    
    public String location;
    
    public int hoursFrom;
    public int hoursTo;
    public int allowDisturbing;
    
    ProfileInfo(Builder builder){
    	super();
    	this.id = builder._id;
    	this.regid = builder._regid;
    	this.name = builder._name;
    	this.gender = builder._gender;
    	this.age = builder._age;
    	this.phoneNumber = builder._phoneNumber;
    	this.email = builder._email;
    	this.activity = builder._activity;
    	this.location = builder._location;
    	this.hoursFrom = builder._hoursFrom;
    	this.hoursTo = builder._hoursTo;
    	this.allowDisturbing = builder._allowDisturbing;
    }
    
    public static class Builder {
    	
    	public int _id;
    	public String _regid;
    	public String _name;
        public int _gender;
        public int _age;
        public String _phoneNumber;
        public String _email;
        public int _activity;
        public String _location;
        public int _hoursFrom;
        public int _hoursTo;
        public int _allowDisturbing;
        
        public void Builder(String regid, String name, int gender, int age, String phoneNumber, 
        		String email, int activity, String location, int hoursFrom, int hoursTo, int allowDisturbing){
        	this._regid = regid;
        	this._name = name;
        	this._gender = gender;
        	this._age = age;
        	this._phoneNumber = phoneNumber;
        	this._email = email;
        	this._activity = activity;
        	this._location = location;
        	this._hoursFrom = hoursFrom;
        	this._hoursTo = hoursTo;
        	this._allowDisturbing = allowDisturbing;
        }
        public Builder setid(int id){this._id = id; return this; }
        public Builder setRegid(String regid){this._regid = regid; return this; }
        public Builder setName(String name){this._name = name; return this; }
        public Builder setGender(int gender){this._gender = gender; return this; }
        public Builder setAge(int age){this._age = age; return this; }
        public Builder setPhonenumber(String phone){this._phoneNumber = phone; return this; }
        public Builder setEmail(String email){this._email = email; return this; }
        public Builder setActivity(int activity){this._activity = activity; return this; }
        public Builder setLocation(String location){this._location = location; return this; }
        public Builder setHoursFrom(int hoursFrom){this._hoursFrom = hoursFrom; return this; }
        public Builder setHoursTo(int hoursTo){this._hoursTo = hoursTo; return this; }
        public Builder setAllowDisturbing(int allowDisturbing){this._allowDisturbing = allowDisturbing; return this; }
        
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
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeInt(activity);
        dest.writeString(location);
        dest.writeInt(hoursFrom);
        dest.writeInt(hoursTo);
        dest.writeInt(allowDisturbing);
    }

    public void readFromParcel(Parcel in){
    	id = in.readInt();
    	regid = in.readString();
    	name = in.readString();
    	gender = in.readInt();
    	age = in.readInt();
    	phoneNumber = in.readString();
    	email = in.readString();
    	activity = in.readInt();
    	location = in.readString();
    	hoursFrom = in.readInt();
    	hoursTo = in.readInt();
    	allowDisturbing = in.readInt();
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