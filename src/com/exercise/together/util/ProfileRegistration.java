package com.exercise.together.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.exercise.together.MainActivity;
import com.exercise.together.util.Constants.Column;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class ProfileRegistration {

	private static final String TAG = "ProfileRegistration";
	Context mContext;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public interface ProfileListener{
    	void onLoadedProfile(ArrayList<ProfileInfo> aList);
    	void onResultProfileSend(Wrapper wrapper);
    	void onResultRegidServer(String param);
    	void onResultProfileDelete(Wrapper wrapper);
    	void onResultProfileUpdate(Wrapper wrapper);
    };
    public interface FriendListener{
    	void onLoadedFriend(ArrayList<ProfileInfo> aList);
    	
    }
    ProfileListener mListener;
    FriendListener mFreindListener;
    /**
     * Substitute you own sender ID here. This is the project number you got
     * from the API Console, as described in "Getting Started."
     */
    GoogleCloudMessaging mGcm;

	public ProfileRegistration(Context ctx){
		mContext = ctx;
	}
	public void setProfileListener(ProfileListener pl){
		mListener = pl;
	}
	public void setFriendListener(FriendListener fl){
		mFreindListener = fl;
	}
	
	/**
	 * Check the device to make sure it has the Google Play Services APK. If
	 * it doesn't, display a dialog that allows users to download the APK from
	 * the Google Play Store or enable it in the device's system settings.
	 */
	public boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
		Log.v(TAG, "resultCode="+resultCode);
	    if (resultCode != ConnectionResult.SUCCESS) {
		    if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
		        GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity)mContext,
		                PLAY_SERVICES_RESOLUTION_REQUEST).show();
		    } else {
		        Log.i(TAG, "This device is not supported.");
		        //finish();
		    }
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	public String getRegidLocal() {
		final SharedPreferences prefs = mContext.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String registrationId = prefs.getString(Constants.KEY.REGID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing registration ID is not guaranteed to work with
	    // the new app version.
	    int registeredVersion = prefs.getInt(Constants.KEY.APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = Utility.getAppVersion(mContext);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}
	
	public void getRegidServerAsync(){
		
		new AsyncTask<Void, Void, String>(){

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String regid = null;
				GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(ProfileRegistration.this.mContext);
				try {
		        	//gcm에서 regid가져오기
		            regid = gcm.register(com.exercise.together.util.Config.SENDER_ID);
		            Log.v(TAG, "regid="+regid);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return regid;
			}

			@Override
			protected void onPostExecute(String regid) {
				// TODO Auto-generated method stub
				super.onPostExecute(regid);
				mListener.onResultRegidServer(regid);
				getProfileAsync(regid);
			}

		}.execute();
	}
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(String regId) {
	    //final SharedPreferences prefs = getGCMPreferences(context);
		final SharedPreferences prefs = mContext.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion = Utility.getAppVersion(mContext);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(Constants.KEY.REGID, regId);
	    editor.putInt(Constants.KEY.APP_VERSION, appVersion);
	    editor.putBoolean(Constants.KEY.DONE_REGISTRATION, true);
	    editor.commit();
	}
	
	public void sendProfileAsync(ProfileInfo pi) {
		
		new AsyncTask<ProfileInfo, Void, Wrapper>(){
			
        	int responseCode = 0;
        	ProgressDialog pd;
        	
        	@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(mContext);
            	pd.setMessage("getting registeration id from server");
            	pd.show();
            }

			@Override
			protected Wrapper doInBackground(ProfileInfo... params) {
				// TODO Auto-generated method stub
            	HttpClient client = new DefaultHttpClient();
            	HttpPost httpPost = new HttpPost("http://chulchoice.cafe24app.com/profile/register");
            	HttpResponse response = null;
				String responseString = null;
				
				Log.v(TAG, "params[0].age="+params[0].age);
				
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(11);
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.REGID, params[0].regid));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.NAME, params[0].name));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.SPORTS, String.valueOf(params[0].activity)));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.GENDER, String.valueOf(params[0].gender)));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.AGE, String.valueOf(params[0].age)));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.PHONE, params[0].phoneNumber));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.EMAIL, params[0].email));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.LOCATION, params[0].location));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.FROM_HOUR, String.valueOf(params[0].hoursFrom)));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.TO_HOUR, String.valueOf(params[0].hoursTo)));
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.ALLOW_DISTURB, String.valueOf(params[0].allowDisturbing)));
            	//Encoding POST data
            	try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
					
					response = client.execute(httpPost);
					responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "responseCode="+responseCode);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
				
					if(responseCode == HttpStatus.SC_OK)
						storeRegistrationId(params[0].regid);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}      
            	
            	Wrapper wrapper = new Wrapper();
            	wrapper.responseCode = responseCode;
            	wrapper.responseString = responseString;
            	
            	return wrapper;
			}

			@Override
			protected void onPostExecute(Wrapper wrapper) {
				// TODO Auto-generated method stub
				super.onPostExecute(wrapper);
				if(pd != null)
					pd.dismiss();
				
				mListener.onResultProfileSend(wrapper);
			}
    		
    	}.execute(pi);
	}
	
	public void getProfileAsync(String regid){

		new AsyncTask<String, Void, String>(){

	    	int responseCode = 0;
	    	ProgressDialog pd;

        	@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(mContext);
            	pd.setMessage("getting profile from server");
            	pd.show();
            	Log.v(TAG, "onPreExecute");
            }

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				Log.v(TAG, "doInBackground");
            	HttpClient client = new DefaultHttpClient();
            	HttpGet httpGet = new HttpGet("http://chulchoice.cafe24app.com/profile/get/"+params[0]);
            	Log.v(TAG, "params[0]="+params[0]);
            	HttpResponse response = null;
            	String responseString = null;
        		
            	try {
        			response = client.execute(httpGet);
        			responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "responseCode="+responseCode);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
        		} catch (UnsupportedEncodingException e) 
            	{
            	     e.printStackTrace();
            	} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	return responseString;
			}

			@Override
			protected void onPostExecute(String responseString) {
				// TODO Auto-generated method stub
				super.onPostExecute(responseString);
				
				Log.v(TAG, "onPostExecute responseString="+responseString);
				
				if(pd != null)
					pd.dismiss();
				
				ArrayList<ProfileInfo> alist = new ArrayList<ProfileInfo>();
		        
				if(responseString != null){
            		Log.d(TAG, "Http Post responseString : "+responseString);
            		try {
						JSONArray root = new JSONArray(responseString);
						Log.v(TAG, "root="+root);
						Log.v(TAG, "root.length="+root.length());
						for(int i=0; i<root.length(); i++){
							JSONObject jo = root.getJSONObject(i);
							Log.v(TAG, "jo="+jo);
							if(jo != null){
								int id = jo.getInt(Constants.KEY.ID);
								String regid = jo.getString(Constants.KEY.REGID);
								String name = jo.getString(Constants.KEY.NAME);
								int gender = jo.getInt(Constants.KEY.GENDER);
								int age = jo.getInt(Constants.KEY.AGE);
								int activity = jo.getInt(Constants.KEY.SPORTS);
						        String phone = jo.getString(Constants.KEY.PHONE);
						        String email = jo.getString(Constants.KEY.EMAIL);
						        String location = jo.getString(Constants.KEY.LOCATION);
						        int hoursFrom = jo.getInt(Constants.KEY.FROM_HOUR);
						        int hoursTo = jo.getInt(Constants.KEY.TO_HOUR);
						        int allowDisturbing = jo.getInt(Constants.KEY.ALLOW_DISTURB);
						        
						        Log.v(TAG, "regid="+regid);
						        Log.v(TAG, "name="+name+", gender="+gender+", age="+age+", activity="+activity);
						        Log.v(TAG, "phone="+phone+", email="+email+", location="+location+", hoursFrom="+hoursFrom+", hoursTo="+hoursTo+", allowDisturbing="+allowDisturbing);
						        
						        ProfileInfo pi = new ProfileInfo.Builder()
									.setid(id)
									.setRegid(regid)
				        			.setName(name)
				        			.setActivity(activity)
					        		.setGender(gender)
					        		.setAge(age)
					        		.setLocation(location)
					        		.setPhonenumber(phone)
					        		.setEmail(email)
					        		.setHoursFrom(hoursFrom)
					        		.setHoursTo(hoursTo)
					        		.setAllowDisturbing(allowDisturbing)
					        		.build();
								
								alist.add(pi);
							}
						}
						//rootObj.getJSONObject("");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
				
				mListener.onLoadedProfile(alist);
			}
    		
    	}.execute(regid);
	}
	
	public void deleteProfileAsync(ProfileInfo pi){
		
		new AsyncTask<ProfileInfo, Void, Wrapper>(){

			ProgressDialog pd;
			int responseCode = 0;
	    	String responseString = null;
	    	
        	@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(mContext);
            	pd.setMessage("Deleting profile at server");
            	pd.show();
            	Log.v(TAG, "onPreExecute");
            }
			
			@Override
			protected Wrapper doInBackground(ProfileInfo... params) {
				// TODO Auto-generated method stub
				HttpClient client = new DefaultHttpClient();
				HttpDelete httpDelete = new HttpDelete("http://chulchoice.cafe24app.com/profile/delete/"+params[0].id);
				Log.v(TAG, "params[0].id="+params[0].id);
            	HttpResponse response = null;
            	
            	try {
        			response = client.execute(httpDelete);
        			responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "responseCode="+responseCode);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
        		} catch (UnsupportedEncodingException e) 
            	{
            	     e.printStackTrace();
            	} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	Wrapper wrapper = new Wrapper();
            	wrapper.responseCode = responseCode;
            	wrapper.responseString = responseString;
            	
            	return wrapper;
			}

			@Override
			protected void onPostExecute(Wrapper wrapper) {
				// TODO Auto-generated method stub
				super.onPostExecute(wrapper);
				
				if(pd != null)
					pd.dismiss();
				
				mListener.onResultProfileDelete(wrapper);
			}
			
		}.execute(pi);
	}
	
	public void updateProfileAsync(Bundle bd){
		
		new AsyncTask<Bundle, Void, Wrapper>(){

			int responseCode = 0;
	    	String responseString = null;
	    	
        	@Override
			protected Wrapper doInBackground(Bundle... params) {
				// TODO Auto-generated method stub
        		HttpClient client = new DefaultHttpClient();
            	HttpPost httpPost = new HttpPost("http://chulchoice.cafe24app.com/profile/update");
            	HttpResponse response = null;
				String responseString = null;
				
				//int allowDist = params[0].getInt(Constants.KEY.ALLOW_DISTURB);
				
				Bundle bd = params[0];
				Log.v(TAG, "bd.size="+bd.size());
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(bd.size());
				for(Column c : Column.values()){
					Log.v(TAG, "c="+c);
					String value = bd.getString(c.name());
					Log.v(TAG, "value="+value);
					if(value != null && value.isEmpty() != true){
						Log.v(TAG, "c.name()="+c.name()+", value="+value);
						nameValuePair.add(new BasicNameValuePair(c.name(), value));	
					}
	            	
				}
            	//Encoding POST data
            	try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
					
					response = client.execute(httpPost);
					responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "responseCode="+responseCode);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}      
            	
            	Wrapper wrapper = new Wrapper();
            	wrapper.responseCode = responseCode;
            	wrapper.responseString = responseString;
            	wrapper.position = params[0].getString(Constants.KEY.POSITION);
            	wrapper.allow_diturbing = params[0].getString(Constants.KEY.ALLOW_DISTURB);
            	return wrapper;
			}
			
			@Override
			protected void onPostExecute(Wrapper wrapper) {
				// TODO Auto-generated method stub
				super.onPostExecute(wrapper);
				
				mListener.onResultProfileUpdate(wrapper);
			}
		}.execute(bd);
	}
	
	public void getFriendsAsync(ProfileInfo pi){

		new AsyncTask<ProfileInfo, Void, String>(){

	    	int responseCode = 0;
	    	ProgressDialog pd;

        	@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				pd = new ProgressDialog(mContext);
            	pd.setMessage("getting profile from server");
            	pd.show();
            	Log.v(TAG, "onPreExecute");
            }

			@Override
			protected String doInBackground(ProfileInfo... params) {
				// TODO Auto-generated method stub
				Log.v(TAG, "doInBackground");
            	HttpClient client = new DefaultHttpClient();
            	HttpPost httpPost = new HttpPost("http://chulchoice.cafe24app.com/friends/get/");
            	Log.v(TAG, "params[0]="+params[0]);
            	HttpResponse response = null;
            	String responseString = null;
            	
            	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(6);
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.REGID, params[0].regid));	
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.GENDER_FILTER, String.valueOf(params[0].gender_filter)));	
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.AGE_FILTER, String.valueOf(params[0].age_filter)));	
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.LEVEL_FILTER, String.valueOf(params[0].level_filter)));	
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.LOCATION_FILTER, String.valueOf(params[0].location_filter)));	
            	nameValuePair.add(new BasicNameValuePair(Constants.KEY.TIME_FILTER, String.valueOf(params[0].time_filter)));	

            	try {
            		httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
					
        			response = client.execute(httpPost);
        			responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "responseCode="+responseCode);
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					response.getEntity().writeTo(out);
					responseString = out.toString();
					out.close();
        		} catch (UnsupportedEncodingException e) 
            	{
            	     e.printStackTrace();
            	} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	return responseString;
			}

			@Override
			protected void onPostExecute(String responseString) {
				// TODO Auto-generated method stub
				super.onPostExecute(responseString);
				
				Log.v(TAG, "onPostExecute responseString="+responseString);
				
				if(pd != null)
					pd.dismiss();
				
				ArrayList<ProfileInfo> alist = new ArrayList<ProfileInfo>();
		        
				if(responseString != null){
            		Log.d(TAG, "Http Post responseString : "+responseString);
            		try {
						JSONArray root = new JSONArray(responseString);
						Log.v(TAG, "root="+root);
						Log.v(TAG, "root.length="+root.length());
						for(int i=0; i<root.length(); i++){
							JSONObject jo = root.getJSONObject(i);
							Log.v(TAG, "jo="+jo);
							if(jo != null){
								int id = jo.getInt(Constants.KEY.ID);
								String regid = jo.getString(Constants.KEY.REGID);
								String name = jo.getString(Constants.KEY.NAME);
								int gender = jo.getInt(Constants.KEY.GENDER);
								int age = jo.getInt(Constants.KEY.AGE);
								int activity = jo.getInt(Constants.KEY.SPORTS);
						        String phone = jo.getString(Constants.KEY.PHONE);
						        String email = jo.getString(Constants.KEY.EMAIL);
						        String location = jo.getString(Constants.KEY.LOCATION);
						        int hoursFrom = jo.getInt(Constants.KEY.FROM_HOUR);
						        int hoursTo = jo.getInt(Constants.KEY.TO_HOUR);
						        int allowDisturbing = jo.getInt(Constants.KEY.ALLOW_DISTURB);
						        
						        Log.v(TAG, "regid="+regid);
						        Log.v(TAG, "name="+name+", gender="+gender+", age="+age+", activity="+activity);
						        Log.v(TAG, "phone="+phone+", email="+email+", location="+location+", hoursFrom="+hoursFrom+", hoursTo="+hoursTo+", allowDisturbing="+allowDisturbing);
						        
						        ProfileInfo pi = new ProfileInfo.Builder()
									.setid(id)
									.setRegid(regid)
				        			.setName(name)
				        			.setActivity(activity)
					        		.setGender(gender)
					        		.setAge(age)
					        		.setLocation(location)
					        		.setPhonenumber(phone)
					        		.setEmail(email)
					        		.setHoursFrom(hoursFrom)
					        		.setHoursTo(hoursTo)
					        		.setAllowDisturbing(allowDisturbing)
					        		.build();
								
								alist.add(pi);
							}
						}
						//rootObj.getJSONObject("");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
				
				mFreindListener.onLoadedFriend(alist);
			}
    		
    	}.execute(pi);
	}
	
	public class Wrapper{
		public int responseCode;
		public String responseString;
	
		public String position;
		public String allow_diturbing;
	}
	
}
