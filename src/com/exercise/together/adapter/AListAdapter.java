package com.exercise.together.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.exercise.together.R;
import com.exercise.together.util.FriendInfo;
import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;

public class AListAdapter<T> extends ArrayAdapter<T>{

	private static final String TAG = "AListAdapter";
	Context mContext;
	ArrayList<T> mItems;
	int mResRowId;
	ProfileRegistration mRegiHelper = null;
	int checkInitialized = 0;
	boolean initializing = true;
	
	public AListAdapter(Context context, int resId, ArrayList<T> items, ProfileRegistration regiHelper) {
		super(context, resId, items);
		// TODO Auto-generated constructor stub
		mContext = context;
		mResRowId = resId;
		mItems = items;
		mRegiHelper = regiHelper;
		//mRegiHelper = new ProfileRegistration(context);
		/*new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(2000);
					initializing = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
		}.execute();*/
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		ViewCache vc;
		
		Log.v(TAG, "getView position="+position);
		
		if(v == null){
			v = LayoutInflater.from(mContext).inflate(mResRowId, parent, false);
			vc = new ViewCache();
			vc.iv1 = (ImageView)v.findViewById(R.id.imageView1);
			vc.tv1 = (TextView)v.findViewById(R.id.textView1);
			vc.tv2 = (TextView)v.findViewById(R.id.textView2);
			vc.tv3 = (TextView)v.findViewById(R.id.textView3);
			vc.tv4 = (TextView)v.findViewById(R.id.textView4);
			vc.sw1 = (Switch)v.findViewById(R.id.switch1);
			v.setTag(vc);
		}
		
		vc = (ViewCache) v.getTag();
		
		T bean = mItems.get(position);
		
		if(bean instanceof FriendInfo){
			Log.v(TAG, "bean instanceof FriendInfo");
			FriendInfo aFriend = (FriendInfo)mItems.get(position);
			//FriendInfo info = getItem(position);
			vc.tv1.setText(aFriend.email);
		}else if(bean instanceof ProfileInfo){
			Log.v(TAG, "bean instanceof ProfileInfo");
			ProfileInfo aProfile = (ProfileInfo)mItems.get(position);
			String[] sports = mContext.getResources().getStringArray(R.array.activities);
			String[] ages = mContext.getResources().getStringArray(R.array.ages);
			String[] locations = mContext.getResources().getStringArray(R.array.locations);
			String temp = sports[aProfile.sports];
			TypedArray imgs = mContext.getResources().obtainTypedArray(R.array.nav_drawer_icons);
			
			vc.iv1.setImageResource(imgs.getResourceId(aProfile.sports,  -1));
			vc.tv1.setText(sports[aProfile.sports]);
			vc.tv2.setText("연령대 : "+ages[aProfile.age]);
			vc.tv4.setText("지역 : "+aProfile.location);
			vc.sw1.setOnCheckedChangeListener(null);
			vc.sw1.setChecked(aProfile.allow_disturbing==1?true:false);
			//vc.sw1.setVisibility(View.GONE);

			vc.sw1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					//Log.v(TAG, "checkInitialized="+checkInitialized+", getCount()="+getCount());
					/*if(checkInitialized != getCount()){
						checkInitialized++;
						return;
					}*/
					//Log.v(TAG, "initializing="+initializing);
					/*if(initializing){
						return;
					}*/
					
					int position = (int)buttonView.getTag();
					ProfileInfo pi = (ProfileInfo)getItem(position);
					pi.allow_disturbing = isChecked?1:2;
					Log.v(TAG, "onCheckedChanged pi.id="+pi.id+", isChecked="+isChecked);
					
					Bundle bd = new Bundle();
					bd.putString(com.exercise.together.util.Constants.KEY.POSITION, String.valueOf(position));
					bd.putString(com.exercise.together.util.Constants.KEY.ID, String.valueOf(pi.id));
					bd.putString(com.exercise.together.util.Constants.KEY.ALLOW_DISTURBING, String.valueOf(isChecked?1:2));
					//Log.v(TAG, "pi.id="+pi.id);
					mRegiHelper.updateProfileAsync(bd);
				}
			});
			vc.sw1.setTag(position);
		}
		
		return v;
	}

	class ViewCache{
		ImageView iv1;
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		Switch sw1;
	}

	public T getListItem(int pos){
		return mItems.get(pos);
	}
}


