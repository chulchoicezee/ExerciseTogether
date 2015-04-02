package com.exercise.together;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.exercise.together.util.Bean;
import com.exercise.together.util.Constants.INFO;
import com.exercise.together.util.FriendInfo;

public class JsonAsyncTask extends AsyncTask<String, Void, String>{

	private static final String TAG = "JsonAsyncTask";
	int mType;
	Context mContext;
	ProgressDialog mProDiag;
	Handler mHandler;
	JSONObject responseJSON = null;
	
	JsonAsyncTask(Context ctx, int type, Handler handler){
		mContext = ctx;
		mType = type;
		mHandler = handler;
	}
		
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mProDiag = new ProgressDialog(mContext);
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
			
			switch(mType){
			case INFO.GENERAL_INFO:
			{
				for(int i=0; i<rootArray.length(); i++){
					JSONObject jo = rootArray.getJSONObject(i);
					String email = jo.getString("senderEmail");
					String id = jo.getString("_id");
				}
				Message msg = mHandler.obtainMessage();
				msg.what = mType;
				msg.arg1 = rootArray.length();
				Log.v(TAG, "rootArray.length()="+rootArray.length());
				mHandler.sendMessage(msg);
			}
				break;
			case INFO.FRIEND_INFO:
			{
				FriendInfo[] friends= new FriendInfo[rootArray.length()];
				ArrayList<FriendInfo> friendsList = new ArrayList<FriendInfo>();
				FriendInfo friend;
				
				for(int i=0; i<rootArray.length(); i++){
					JSONObject jo = rootArray.getJSONObject(i);
					String email = jo.getString("senderEmail");
					String id = jo.getString("_id");
					friendsList.add(new FriendInfo(email));
				}
				Message msg = mHandler.obtainMessage();
				Bundle bd = new Bundle();
				//bd.putParcelableArray("friends", friends);
				bd.putParcelableArrayList("friends", friendsList);
				msg.setData(bd);
				msg.what = mType;
				msg.arg1 = rootArray.length();
				Log.v(TAG, "bd="+bd.toString());
				Log.v(TAG, "rootArray.length()="+rootArray.length());
				mHandler.sendMessage(msg);
			}
				break;
			default:
				break;
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}		
}
