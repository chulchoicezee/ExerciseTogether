package com.exercise.together;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MyProfileEditActivity extends Activity implements OnItemSelectedListener{

	ArrayAdapter<CharSequence> adpSports, adpGender, adpLocation;
	public static final int OPTION = Menu.FIRST+1;
	private static final String TAG = "MyProfileEditActivity";
	public static final String PROPERTY_REG_ID = "registration_id";
	ProgressDialog mPd;
	String mName = null;
    String mSports = null;
    String mGender = null;
    String mLocation = null;
    ProfileRegistration mRegiHelder = null;
    EditText et_name = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile_edit);
		
		 Spinner spn_sports = (Spinner) findViewById(R.id.spn_sports);
		 Spinner spn_gender = (Spinner) findViewById(R.id.spn_gender);
		 Spinner spn_location = (Spinner) findViewById(R.id.spn_location);
	     et_name = (EditText)findViewById(R.id.et_name);
	     //spinner.setPrompt("시/도 를 선택하세요.");
		 adpSports = ArrayAdapter.createFromResource(this, R.array.activities, android.R.layout.simple_spinner_item);
		 adpSports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     spn_sports.setAdapter(adpSports);
	     adpGender = ArrayAdapter.createFromResource(this, R.array.genders, android.R.layout.simple_spinner_item);
	     adpGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_gender.setAdapter(adpGender);
		 adpLocation = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
		 adpLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 spn_location.setAdapter(adpLocation);
		 spn_sports.setOnItemSelectedListener(this);
		 spn_gender.setOnItemSelectedListener(this);
		 spn_location.setOnItemSelectedListener(this);
		 
		 mRegiHelder = new ProfileRegistration(this);
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
            if (mRegiHelder.checkPlayServices()) {
            	message = "GCM 서비스 불가";
            	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                return true;
            }else{
            	message = "등록 진행";
            	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            	String regid = mRegiHelder.getRegistrationId();
            	if(regid.isEmpty()){
            		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
                    try {
                    	//gcm에서 regid가져오기
        	            regid = gcm.register(com.exercise.together.util.Config.SENDER_ID);
    					if(regid != null && regid.length() > 0){
    						//프로파일 등록하기
    		        		ProfileInfo pi = new ProfileInfo(1, "동작구");
    		        		mRegiHelder.sendProfileAsync(pi);
    					}
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
            	}else{
            		//프로파일 등록하기
            		ProfileInfo pi = new ProfileInfo(1, "동작구");
            		mRegiHelder.sendProfileAsync(pi);
            	}
            }
            break;
        }
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		Spinner spinner = (Spinner)parent;
		if(spinner.getId() == R.id.spn_sports){
			mSports = (String)spinner.getSelectedItem();
			Toast.makeText(MyProfileEditActivity.this, "종목:"+spinner.getItemAtPosition(position)+"을 선택햇음", Toast.LENGTH_SHORT).show();
			
		}else if(spinner.getId() == R.id.spn_gender){
			mGender = (String)spinner.getSelectedItem();
			Toast.makeText(MyProfileEditActivity.this, "성별:"+spinner.getItemAtPosition(position)+"을 선택햇음", Toast.LENGTH_SHORT).show();
			
		}else if(spinner.getId() == R.id.spn_location){
			Toast.makeText(MyProfileEditActivity.this, "위치:"+spinner.getItemAtPosition(position)+"을 선택햇음", Toast.LENGTH_SHORT).show();
			mLocation = (String)spinner.getSelectedItem();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
}
