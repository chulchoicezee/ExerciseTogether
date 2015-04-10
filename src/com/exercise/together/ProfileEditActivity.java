package com.exercise.together;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.ProfileListener;
import com.exercise.together.util.ProfileRegistration.Wrapper;

public class ProfileEditActivity extends Activity implements OnItemSelectedListener, ProfileListener, OnCheckedChangeListener{

	ArrayAdapter<CharSequence> adpGender, adpAge, adpSports, adpLocation, adpLevel, adpTime;
	public static final int OPTION = Menu.FIRST+1;
	private static final String TAG = "MyProfileEditActivity";
	public static final String PROPERTY_REG_ID = "registration_id";
	ProgressDialog mPd;
	ProfileRegistration mRegiHelper = null;
    EditText et_name = null;
    Spinner spn_gender = null;
    Spinner spn_age = null;
    Spinner spn_sports = null;
    Spinner spn_location = null;
    Spinner spn_level = null;
    
    EditText et_phone = null;
    EditText et_email = null;
    Spinner spn_time = null;
    Switch sw_allowDisturb = null;
    CheckBox cbox_gender = null;
    CheckBox cbox_age = null;
    CheckBox cbox_location = null;
    CheckBox cbox_level = null;
    CheckBox cbox_time = null;
    
    int mAllowDisturb = 1;
    int mGenderFilter = 1;
    int mAgeFilter = 1;
    int mLocationFilter = 1;
    int mLevelFilter = 1;
    int mTimeFilter = 1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_edit);
		
		 et_name = (EditText)findViewById(R.id.et_name);
		 spn_gender = (Spinner) findViewById(R.id.spn_gender);
		 cbox_gender = (CheckBox) findViewById(R.id.proEdit_cb_gender);
		 spn_age = (Spinner) findViewById(R.id.spn_age);
		 cbox_age = (CheckBox) findViewById(R.id.proEdit_cb_age);
		 spn_sports = (Spinner) findViewById(R.id.spn_sports);
		 spn_location = (Spinner) findViewById(R.id.spn_location);
		 cbox_location = (CheckBox) findViewById(R.id.proEdit_cb_location);
		 spn_level = (Spinner) findViewById(R.id.spn_level);
		 cbox_level = (CheckBox) findViewById(R.id.proEdit_cb_level);
		 et_phone = (EditText)findViewById(R.id.et_phone);
		 et_email = (EditText)findViewById(R.id.et_email);
		 spn_time = (Spinner) findViewById(R.id.spn_time);
		 cbox_time = (CheckBox) findViewById(R.id.proEdit_cb_time);
		 sw_allowDisturb = (Switch) findViewById(R.id.sw_allow);
		 
		 adpGender = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
		 adpGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_gender.setAdapter(adpGender);
		 spn_gender.setOnItemSelectedListener(this);
		 
		 adpAge = ArrayAdapter.createFromResource(this, R.array.ages, android.R.layout.simple_spinner_item);
		 adpAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_age.setAdapter(adpAge);
		 spn_age.setOnItemSelectedListener(this);
		 
		 adpSports = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
		 adpSports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_sports.setAdapter(adpSports);
		 spn_sports.setOnItemSelectedListener(this);
		 
		 adpLocation = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
		 adpLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_location.setAdapter(adpLocation);
		 spn_location.setOnItemSelectedListener(this);
		 
		 adpLevel = ArrayAdapter.createFromResource(this, R.array.levels, android.R.layout.simple_spinner_item);
		 adpLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_age.setAdapter(adpLevel);
		 spn_age.setOnItemSelectedListener(this);
		 
		 adpTime = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
		 adpTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_time.setAdapter(adpTime);
		 spn_time.setOnItemSelectedListener(this);
		 
		 sw_allowDisturb.setChecked(true);
		 sw_allowDisturb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mAllowDisturb = isChecked?1:2;
			}
		});
		 
		 cbox_gender.setOnCheckedChangeListener(this);
		 cbox_age.setOnCheckedChangeListener(this);
		 cbox_location.setOnCheckedChangeListener(this);
		 cbox_level.setOnCheckedChangeListener(this);
		 cbox_time.setOnCheckedChangeListener(this);
		 
 		mRegiHelper = new ProfileRegistration(this);
 		mRegiHelper.setProfileListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(buttonView.getId() == R.id.proEdit_cb_gender){
			mGenderFilter = isChecked?1:0;
		}else if(buttonView.getId() == R.id.proEdit_cb_age){
			mAgeFilter = isChecked?1:0;
		}else if(buttonView.getId() == R.id.proEdit_cb_location){
			mLocationFilter = isChecked?1:0;
		}else if(buttonView.getId() == R.id.proEdit_cb_level){
			mLevelFilter = isChecked?1:0;
		}else if(buttonView.getId() == R.id.proEdit_cb_time){
			mTimeFilter = isChecked?1:0;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.create_profile, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()) {
        case R.id.register :
            String message = null;
            
            if(et_name.getText().toString().length() < 1 || spn_sports.getSelectedItemPosition() == 0 
            		|| spn_age.getSelectedItemPosition() == 0 || spn_location.getSelectedItemPosition() == 0 ){
            	Toast.makeText(this, "필수 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
            	return false;
            }
            	
            if (!mRegiHelper.checkPlayServices()) {
            	message = "GCM 서비스 불가";
            	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                return true;
            }else{
            	Toast.makeText(this, "등록합니다", Toast.LENGTH_SHORT).show();
            	
            	String regid = mRegiHelper.getRegidLocal();

            	if(regid.isEmpty()){
            		mRegiHelper.getRegidServerAsync();//onResultRegidServer is called.
            	}else{
        			//프로파일 등록하기
            		registerProfileAsync(regid);
            	}
            }
            break;
        }
		return super.onOptionsItemSelected(item);
	}

	void registerProfileAsync(String regid){
		
		String name = et_name.getText().toString();
		int gender = spn_gender.getSelectedItemPosition();
		int age = spn_age.getSelectedItemPosition();
		int sports = spn_sports.getSelectedItemPosition();
		String location = (String)spn_location.getSelectedItem();
		int level = spn_level.getSelectedItemPosition();
		String phone = et_phone.getText().toString();
		String email = et_email.getText().toString();
		int time = spn_time.getSelectedItemPosition();
		int allow_disturbing = mAllowDisturb;
		int gender_filter = mGenderFilter;
		int age_filter = mAgeFilter;
		int location_filter = mLocationFilter;
		int level_filter = mLevelFilter;
		int time_filter = mTimeFilter;
		
		Log.v(TAG, "regid="+regid);
        Log.v(TAG, "name="+name+", gender="+gender+", age="+age+", sports="+sports+", location="+location+", level="+level);
        Log.v(TAG, "phone="+phone+", email="+email+", time="+time+", allow_disturbing="+allow_disturbing);
        
        ProfileInfo pi = new ProfileInfo.Builder()
			.setRegid(regid)
			.setName(name)
    		.setGender(gender)
    		.setAge(age)
			.setSports(sports)
    		.setLocation(location)
    		.setLevel(level)
    		.setPhone(phone)
    		.setEmail(email)
    		.setTime(time)
    		.setAllowDisturbing(allow_disturbing)
    		.setGenderFilter(gender_filter)
    		.setAgeFilter(age_filter)
			.setLocationFilter(location_filter)
    		.setLevelFilter(level_filter)
    		.setTimeFilter(time_filter)
    		.build();
        
   		
		mRegiHelper.sendProfileAsync(pi);
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Spinner spinner = (Spinner)parent;
		if(spinner.getId() == R.id.spn_sports){
			//Toast.makeText(MyProfileEditActivity.this, "종목:"+spinner.getItemAtPosition(position)+"을 선택햇음", Toast.LENGTH_SHORT).show();
			
		}else if(spinner.getId() == R.id.spn_gender){
			//Toast.makeText(MyProfileEditActivity.this, "성별:"+spinner.getItemAtPosition(position)+"을 선택햇음", Toast.LENGTH_SHORT).show();
			
		}else if(spinner.getId() == R.id.spn_location){
			//
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onLoadedProfile(ArrayList<ProfileInfo> aList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultProfileSend(Wrapper wrapper) {
		// TODO Auto-generated method stub
		Log.v(TAG, "wrapper.responseCode="+wrapper.responseCode);
		Toast.makeText(this, wrapper.responseString, Toast.LENGTH_SHORT).show();
		
		if(wrapper.responseCode == HttpStatus.SC_OK){
			finish();
		}
	}

	@Override
	public void onResultRegidServer(String regid) {
		// TODO Auto-generated method stub
		if(regid != null && regid.length() > 0){
			//프로파일 등록하기
			registerProfileAsync(regid);
		}else{
			Toast.makeText(this, "failed to get regid from cloud", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onResultProfileDelete(Wrapper wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultProfileUpdate(Wrapper wrapper) {
		// TODO Auto-generated method stub
		
	}
}
