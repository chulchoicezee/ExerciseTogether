package com.exercise.together;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.exercise.together.adapter.NavDrawerListAdapter;
import com.exercise.together.fragment.MyProfileFragment;
import com.exercise.together.fragment.SettingFragment;
import com.exercise.together.fragment.SportsMainFragment;
import com.exercise.together.model.NavDrawerItem;
import com.exercise.together.util.Bean;
import com.exercise.together.util.Callback;
import com.exercise.together.util.Constants;
import com.exercise.together.util.Constants.INFO;
import com.exercise.together.util.Constants.MENU;
import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.ProfileListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity implements Callback, ProfileListener {
	protected static final String TAG = "MainActivity";
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	ProgressDialog mProDiag;
    Thread mThread;
    int mPosition;
    Context mContext = null;
    ProfileRegistration mRegiHelper = null;
    
    //AtomicInteger msgId = new AtomicInteger();
    String regid;
    ProgressDialog mPd;
    int mStatus = 0;
    Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = null;
			Fragment frag = (Fragment)msg.obj;
			Fragment fragment = null;
			Bundle bd = null;
			
			if(mProDiag != null)
				mProDiag.dismiss();
			Log.v(TAG, "mHandler handleMessage msg.what="+msg.what+", mPosition="+mPosition);		
			switch(msg.what){
			case INFO.GENERAL_INFO:
			{
				switch(mPosition){
				case MENU.MY_PROFILE:
					ArrayList<Bean> profiles = new ArrayList<Bean>();
					
					fragment = new MyProfileFragment(profiles);
					break;
				case MENU.BADMINTON:
					fragment = new SportsMainFragment();
					bd = new Bundle();
					bd.putInt("number", msg.arg1);
					fragment.setArguments(bd);
					break;
				case MENU.TABLE_TENNIS:
					fragment = new SportsMainFragment();
					bd = new Bundle();
					bd.putInt("number", msg.arg1);
					fragment.setArguments(bd);
					break;
				case MENU.TENNIS:
					fragment = new SportsMainFragment();
					bd = new Bundle();
					bd.putInt("number", msg.arg1);
					fragment.setArguments(bd);
					break;
				case MENU.INLINE:
					fragment = new SportsMainFragment();
					bd = new Bundle();
					bd.putInt("number", msg.arg1);
					fragment.setArguments(bd);
					break;
				case MENU.SETTING:
					fragment = new SettingFragment();
					break;
				
				/*case 6:
					ArrayList<FriendInfo> infoList = new ArrayList<FriendInfo>();
					infoList.add(new FriendInfo("chulgee@gmail.com"));
					infoList.add(new FriendInfo("chulgee1@gmail.com"));
					Toast.makeText(MainActivity.this, "just received", Toast.LENGTH_SHORT).show();
					if(frag instanceof BadmintonFragment){
						fm = getFragmentManager();
						ft = fm.beginTransaction();
						FriendListFragment friendList_F = new FriendListFragment(infoList);
						ft.replace(R.id.frame_container, friendList_F);
						ft.addToBackStack(null);
						ft.commit();
					}else if(frag instanceof TennisFragment){
						
					}else{
						
					}
					break;*/
				default:
					break;
				}
				
				if (fragment != null) {
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.frame_container, fragment).commit();

					// update selected item and title, then close the drawer
					mDrawerList.setItemChecked(mPosition, true);
					mDrawerList.setSelection(mPosition);
					setTitle(navMenuTitles[mPosition]);
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					// error in creating fragment
					Log.e("MainActivity", "Error in creating fragment");
				}
			}
				break;
			case INFO.FRIEND_INFO:
				bd = msg.getData();
				ArrayList<Bean> friends = bd.getParcelableArrayList("friends");
				Log.v(TAG, "friends="+friends.toString());
				//list activity
				Intent i = new Intent(MainActivity.this, FriendListActivity.class);
				i.putExtra("friends", bd);
				startActivity(i);
				break;
			default:
				break;
			}
			
		}
    	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRegiHelper = new ProfileRegistration(this);
		mRegiHelper.setProfileListener(this);
		
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			//displayView(0);
			final SharedPreferences prefs = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
			boolean done = prefs.getBoolean(Constants.KEY.DONE_REGISTRATION, false);
		    if(done){
		    	String regid = prefs.getString(Constants.KEY.REGID, "");
		    	mRegiHelper.getProfileAsync(regid);//onLoadedProfile에서 listview로 바인딩함.
		        
			}else{
		    	//profile 등록하라는 화면 보여주기.
				Toast.makeText(this, "등록된 프로파일이 없음", Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				msg.what = INFO.GENERAL_INFO;
				msg.arg1 = MENU.MY_PROFILE;
				mPosition = MENU.MY_PROFILE;
				mHandler.sendMessage(msg);
		    }
			
		}
	}

	

	
	/**
	 * Stores the registration ID and app versionCode in the application's
	 * {@code SharedPreferences}.
	 *
	 * @param context application's context.
	 * @param regId registration ID
	 */
	private void storeRegistrationId(Context context, String regId) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    int appVersion = getAppVersion(context);
	    Log.i(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(Constants.KEY.REGID, regId);
	    editor.putInt(Constants.KEY.APP_VERSION, appVersion);
	    editor.commit();
	}
	
	/**
	 * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
	 * or CCS to send messages to your app. Not needed for this demo since the
	 * device sends upstream messages to a server that echoes back the message
	 * using the 'from' address in the message.
	 */
	private HttpResponse sendRegistrationIdToBackend() {
		
		HttpClient client = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost("http://chulchoice.cafe24app.com/regid");
    	List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
    	nameValuePair.add(new BasicNameValuePair(Constants.KEY.REGID, regid));
    	//Encoding POST data
    	try {
    	      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    	 
    	} catch (UnsupportedEncodingException e) 
    	{
    	     e.printStackTrace();
    	}
    	
    	HttpResponse response = null;
		try {
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(response != null)
    		Log.d(TAG, "Http Post Response : "+response.toString());
		return response;
	}
	
	/**
	 * Gets the current registration ID for application on GCM service.
	 * <p>
	 * If result is empty, the app needs to register.
	 *
	 * @return registration ID, or empty string if there is no existing
	 *         registration ID.
	 */
	private String getRegistrationId(Context context) {
	    final SharedPreferences prefs = getGCMPreferences(context);
	    String registrationId = prefs.getString(Constants.KEY.REGID, "");
	    if (registrationId.isEmpty()) {
	        Log.i(TAG, "Registration not found.");
	        return "";
	    }
	    // Check if app was updated; if so, it must clear the registration ID
	    // since the existing registration ID is not guaranteed to work with
	    // the new app version.
	    int registeredVersion = prefs.getInt(Constants.KEY.APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion(context);
	    if (registeredVersion != currentVersion) {
	        Log.i(TAG, "App version changed.");
	        return "";
	    }
	    return registrationId;
	}

	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private static int getAppVersion(Context context) {
	    try {
	        PackageInfo packageInfo = context.getPackageManager()
	                .getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * @return Application's {@code SharedPreferences}.
	 */
	private SharedPreferences getGCMPreferences(Context context) {
	    // This sample app persists the registration ID in shared preferences, but
	    // how you store the registration ID in your app is up to you.
	    return getSharedPreferences(MainActivity.class.getSimpleName(),
	            Context.MODE_PRIVATE);
	}
	
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		
		Message msg = mHandler.obtainMessage();
		mPosition = position;
		String url = null;
		
		switch (position) {
		case MENU.MY_PROFILE:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.GENERAL_INFO, mHandler).execute(url);
			break;
		case MENU.SETTING:
			msg.what = INFO.GENERAL_INFO;
			mHandler.sendMessage(msg);
			break;
		case MENU.BADMINTON:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.GENERAL_INFO, mHandler).execute(url);
			break;
		case MENU.TABLE_TENNIS:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.GENERAL_INFO, mHandler).execute(url);
			break;
		case MENU.TENNIS:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.GENERAL_INFO, mHandler).execute(url);
			break;
		case MENU.INLINE:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.GENERAL_INFO, mHandler).execute(url);
			break;

		default:
			break;
		}

		
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBtnClick(final Fragment frag) {
		// TODO Auto-generated method stub
		String url;
		switch(mPosition){
		case 0:
			break;
		case 1:
			url = "http://immense-ridge-3047.herokuapp.com/users";
			new JsonAsyncTask(this, INFO.FRIEND_INFO, mHandler).execute(url);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		default:
			break;
		}
	}

	void getJSON(INFO _type, String str){
		
		INFO type = _type;
		
		new AsyncTask<String, Void, String>() {

			JSONObject responseJSON = null;
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				mProDiag = new ProgressDialog(MainActivity.this);
				mProDiag.setMessage("getting member's info");
				mProDiag.setTitle("Exercise Together");
				mProDiag.show();
			}

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				//URL url;
				StringBuilder strBuilder = new StringBuilder();
				
				try {
					//url = new URL(params[0]);
					//Log.v(TAG, "doInBackground url="+url.toString());
					HttpClient client = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(new URI(params[0]));
					HttpResponse response = client.execute(httpget);
					int responseCode = response.getStatusLine().getStatusCode();
					Log.v(TAG, "doInBackground responseCode="+responseCode);
					
					if(responseCode == HttpURLConnection.HTTP_OK) {
						HttpEntity entity= response.getEntity();
						InputStream is = entity.getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(is));
						String line;
						while((line = reader.readLine()) != null){
							strBuilder.append(line);
						}
					}else{
						Log.v(TAG, "it's an error");
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return strBuilder.toString();

			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				
				Log.v(TAG, "onPostExecute result="+result);
				
				if(mProDiag != null){
					mProDiag.dismiss();
				}
				
				//json parsing
				try {
					JSONArray rootArray = new JSONArray(result);
					for(int i=0; i<rootArray.length(); i++){
						JSONObject jo = rootArray.getJSONObject(i);
						String email = jo.getString("senderEmail");
						String id = jo.getString("_id");
					}
					Message msg = mHandler.obtainMessage();
					msg.what = mPosition;
					msg.arg1 = rootArray.length();
					Log.v(TAG, "rootArray.length()="+rootArray.length());
					mHandler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}		
			
		}.execute(str);
	}


	@Override
	public void onLoadedProfile(ProfileInfo pi) {
		// TODO Auto-generated method stub
		//리스트뷰에 pi를 장착
	}




	@Override
	public void onResultProfileSend() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onResultRegidServer(String param) {
		// TODO Auto-generated method stub
		
	}
	

	
}
