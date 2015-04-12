package com.exercise.together;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exercise.together.adapter.AListAdapter;
import com.exercise.together.util.Constants;
import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.FriendListener;

public class ProfileDetailActivity extends Activity implements FriendListener{

	private static final String TAG = "ProfileDetailActivity";
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
	CheckBox cb_gender_filter;
	CheckBox cb_age_filter;
	CheckBox cb_location_filter;
	CheckBox cb_level_filter;
	CheckBox cb_time_filter;
	
	Button btn_find;
	
	AListAdapter mListAdapter = null;
	ListView mListview = null;

	ProfileInfo mProfileInfo;
	ProfileRegistration mRegiHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_detail);
		
		mRegiHelper = new ProfileRegistration(this);
		mRegiHelper.setFriendListener(this);
		mListview = (ListView)findViewById(R.id.friendlist_lv);
		
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
		cb_gender_filter = (CheckBox)findViewById(R.id.proDetail_cb_gender_filter);
		cb_age_filter = (CheckBox)findViewById(R.id.proDetail_cb_age_filter);
		cb_location_filter = (CheckBox)findViewById(R.id.proDetail_cb_location_filter);
		cb_level_filter = (CheckBox)findViewById(R.id.proDetail_cb_level_filter);
		cb_time_filter = (CheckBox)findViewById(R.id.proDetail_cb_time_filter);
		
		ProfileInfo pi = (ProfileInfo)getIntent().getParcelableExtra(Constants.KEY.PROFILE_INFO_ARRAY);
		mProfileInfo = pi;
		
		String[] sports = getResources().getStringArray(R.array.activities);
		String[] ages = getResources().getStringArray(R.array.ages);
		String[] genders = getResources().getStringArray(R.array.genders);
		String[] times = getResources().getStringArray(R.array.times);
		//String[] locations = getResources().getStringArray(R.array.locations);
		
		Log.v(TAG, "pi.gender_filter="+pi.gender_filter);
		Log.v(TAG, "pi.age_filter="+pi.age_filter);
		Log.v(TAG, "pi.location_filter="+pi.location_filter);
		Log.v(TAG, "pi.level_filter="+pi.level_filter);
		Log.v(TAG, "pi.time_filter="+pi.time_filter);
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
		cb_gender_filter.setChecked(pi.gender_filter==1?true:false);
		cb_age_filter.setChecked(pi.age_filter==1?true:false);
		cb_location_filter.setChecked(pi.location_filter==1?true:false);
		cb_level_filter.setChecked(pi.level_filter==1?true:false);
		cb_time_filter.setChecked(pi.time_filter==1?true:false);
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
		
		Log.v(TAG, "aList="+aList+", aList.size()="+aList.size());
		/*if(aList.size() > 0){
			mListAdapter = new AListAdapter<>(this, R.layout.list_layout, aList, mRegiHelper);
			mListview.setAdapter(mListAdapter);	
		}else{
			//mListview.setAdapter(mListAdapter);	
			
		}*/
		Intent i = new Intent(this, FriendListActivity.class);
		i.putExtra(Constants.KEY.PROFILE_INFO_ARRAY, aList);
		startActivity(i);
		
	}



}
