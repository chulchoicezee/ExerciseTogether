package com.exercise.together;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.together.util.Constants;
import com.exercise.together.util.ProfileInfo;

public class ProfileDetailActivity extends Activity{

	TextView tv_sports;
	TextView tv_name;
	ImageView iv_photo;
	TextView tv_gender;
	TextView tv_age;
	TextView tv_level;
	TextView tv_location;
	TextView tv_time;
	TextView tv_phone;
	TextView tv_email;
	TextView tv_alarm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_detail);
		
		tv_sports = (TextView)findViewById(R.id.proDetail_tv_sports);
		tv_name = (TextView)findViewById(R.id.proDetail_tv_name);
		iv_photo = (ImageView)findViewById(R.id.proDetail_iv_photo);
		tv_gender = (TextView)findViewById(R.id.proDetail_tv_gender);
		tv_age = (TextView)findViewById(R.id.proDetail_tv_age);
		tv_level = (TextView)findViewById(R.id.proDetail_tv_level);
		tv_location = (TextView)findViewById(R.id.proDetail_tv_location);
		tv_time = (TextView)findViewById(R.id.proDetail_tv_time);
		tv_phone = (TextView)findViewById(R.id.proDetail_tv_phone);
		tv_email = (TextView)findViewById(R.id.proDetail_tv_email);
		tv_alarm = (TextView)findViewById(R.id.proDetail_tv_alarm);
		
		
		ProfileInfo pi = (ProfileInfo)getIntent().getParcelableExtra(Constants.KEY.PROFILE_INFO);
		String[] sports = getResources().getStringArray(R.array.activities);
		String[] ages = getResources().getStringArray(R.array.ages);
		String[] genders = getResources().getStringArray(R.array.genders);
		//String[] locations = getResources().getStringArray(R.array.locations);
		
		tv_sports.setText(sports[pi.activity]);
		tv_name.setText(pi.name);
		tv_gender.setText(genders[pi.gender]);
		tv_age.setText(ages[pi.age]);
		tv_level.setText(String.valueOf(pi.age));
		tv_location.setText(pi.location);
		tv_time.setText(pi.hoursFrom+" ~ "+pi.hoursTo);
		tv_phone.setText(pi.phoneNumber);
		tv_email.setText(pi.email);
		tv_alarm.setText(pi.allowDisturbing==1?"ON":"OFF");
	}

}
