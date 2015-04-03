package com.exercise.together;

import java.io.IOException;
import java.util.ArrayList;

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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.ProfileListener;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MyProfileEditActivity extends Activity implements OnItemSelectedListener, ProfileListener{

	ArrayAdapter<CharSequence> adpSports, adpGender, adpAge, adpLocation, adpFromHour, adpToHour;
	public static final int OPTION = Menu.FIRST+1;
	private static final String TAG = "MyProfileEditActivity";
	public static final String PROPERTY_REG_ID = "registration_id";
	ProgressDialog mPd;
	ProfileRegistration mRegiHelper = null;
    Spinner spn_sports = null;
    EditText et_name = null;
    Spinner spn_gender = null;
    Spinner spn_age = null;
    Spinner spn_location = null;
    EditText et_phone = null;
    EditText et_email = null;
    Spinner spn_fromHour = null;
    Spinner spn_toHour = null;
    Switch sw_allowDisturb = null;
    int mAllowDisturb = 1;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_edit);
		
		 spn_sports = (Spinner) findViewById(R.id.spn_sports);
		 et_name = (EditText)findViewById(R.id.et_name);
		 spn_gender = (Spinner) findViewById(R.id.spn_gender);
		 spn_age = (Spinner) findViewById(R.id.spn_age);
		 spn_location = (Spinner) findViewById(R.id.spn_location);
		 et_phone = (EditText)findViewById(R.id.et_phone);
		 et_email = (EditText)findViewById(R.id.et_email);
		 spn_fromHour = (Spinner) findViewById(R.id.spn_fromhour);
		 spn_toHour = (Spinner) findViewById(R.id.spn_tohour);
		 sw_allowDisturb = (Switch) findViewById(R.id.sw_allow);
		 
		 adpSports = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
		 adpSports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     spn_sports.setAdapter(adpSports);
	     spn_sports.setOnItemSelectedListener(this);
		 
	     adpGender = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
	     adpGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_gender.setAdapter(adpGender);
		 spn_gender.setOnItemSelectedListener(this);
		 
		 adpAge = ArrayAdapter.createFromResource(this, R.array.ages, android.R.layout.simple_spinner_item);
	     adpAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_age.setAdapter(adpAge);
		 spn_age.setOnItemSelectedListener(this);
		 
		 adpLocation = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
		 adpLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_location.setAdapter(adpLocation);
		 spn_location.setOnItemSelectedListener(this);
		 
		 adpFromHour = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
		 adpFromHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_fromHour.setAdapter(adpFromHour);
		 spn_fromHour.setOnItemSelectedListener(this);
		 
		 adpToHour = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
		 adpToHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_toHour.setAdapter(adpToHour);
		 spn_toHour.setOnItemSelectedListener(this);
		 
 		 sw_allowDisturb.setChecked(true);
 		 sw_allowDisturb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				mAllowDisturb = isChecked?1:2;
			}
		});

		 
 		mRegiHelper = new ProfileRegistration(this);
 		mRegiHelper.setProfileListener(this);
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
            if (!mRegiHelper.checkPlayServices()) {
            	message = "GCM 서비스 불가";
            	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                return true;
            }else{
            	Toast.makeText(this, "등록 진행", Toast.LENGTH_SHORT).show();
            	
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
		String phone = et_phone.getText().toString();
		String email = et_email.getText().toString();
		int hoursFrom = spn_fromHour.getSelectedItemPosition();
		int hoursTo = spn_toHour.getSelectedItemPosition();
		int allowDisturbing = mAllowDisturb;
		
		Log.v(TAG, "-----registerProfileAsync regid="+regid);
        Log.v(TAG, "name="+name+", gender="+gender+", age="+age+", sports="+sports);
        Log.v(TAG, "phone="+phone+", email="+email+", location="+location+", hoursFrom="+hoursFrom+", hoursTo="+hoursTo+", allowDisturbing="+allowDisturbing);
        
		ProfileInfo.Builder pb = new ProfileInfo.Builder();
		pb.setRegid(regid)
			.setName(name)
			.setActivity(sports)
    		.setGender(gender)
    		.setAge(age)
    		.setLocation(location)
    		.setPhonenumber(phone)
    		.setEmail(email)
    		.setHoursFrom(hoursFrom)
    		.setHoursTo(hoursTo)
    		.setAllowDisturbing(allowDisturbing);
    	ProfileInfo pi = pb.build();
    		
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
	public void onResultProfileSend() {
		// TODO Auto-generated method stub
		
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
}
