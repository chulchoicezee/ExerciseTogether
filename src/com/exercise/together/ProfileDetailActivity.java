package com.exercise.together;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.exercise.together.util.Constants;
import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.FriendListener;
import com.exercise.together.util.ProfileRegistration.ProfileListener;
import com.exercise.together.util.ProfileRegistration.Wrapper;

public class ProfileDetailActivity extends Activity implements FriendListener{

	TextView tv_sports;
	TextView tv_name;
	ImageView iv_photo;
	TextView tv_gender;
	TextView tv_age;
	TextView tv_location;
	TextView tv_level;
	TextView tv_phone;
	TextView tv_email;
	TextView tv_time;
	TextView tv_alarm;
	Button btn_find;
	
	ProfileInfo mProfileInfo;
	ProfileRegistration mRegiHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_detail);
		
		mRegiHelper = new ProfileRegistration(this);
		mRegiHelper.setFriendListener(this);
		
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
		btn_find = (Button)findViewById(R.id.proDetail_btn1);
		
		ProfileInfo pi = (ProfileInfo)getIntent().getParcelableExtra(Constants.KEY.PROFILE_INFO);
		mProfileInfo = pi;
		
		String[] sports = getResources().getStringArray(R.array.activities);
		String[] ages = getResources().getStringArray(R.array.ages);
		String[] genders = getResources().getStringArray(R.array.genders);
		String[] times = getResources().getStringArray(R.array.times);
		//String[] locations = getResources().getStringArray(R.array.locations);
		
		tv_sports.setText(sports[pi.sports]);
		tv_name.setText(pi.name);
		tv_gender.setText(genders[pi.gender]);
		tv_age.setText(ages[pi.age]);
		tv_level.setText(String.valueOf(pi.age));
		tv_location.setText(pi.location);
		tv_time.setText(times[pi.time]);
		tv_phone.setText(pi.phone);
		tv_email.setText(pi.email);
		tv_alarm.setText(pi.allow_disturbing==1?"ON":"OFF");
		
		btn_find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mRegiHelper.getFriendsAsync(mProfileInfo);
			}
		});
	}

	@Override
	public void onLoadedFriend(ArrayList<ProfileInfo> aList) {
		// TODO Auto-generated method stub
		
	}



}
