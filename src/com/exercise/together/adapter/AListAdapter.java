package com.exercise.together.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.exercise.together.R;
import com.exercise.together.util.FriendInfo;
import com.exercise.together.util.ProfileInfo;

public class AListAdapter<T> extends ArrayAdapter<T>{

	private static final String TAG = "AListAdapter";
	Context mContext;
	ArrayList<T> mItems;
	int mResRowId;
	
	public AListAdapter(Context context, int resId, ArrayList<T> items) {
		super(context, resId, items);
		// TODO Auto-generated constructor stub
		mContext = context;
		mResRowId = resId;
		mItems = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		ViewCache vc;
		
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
			Log.v(TAG, "aProfile.name="+aProfile.name);
			String[] sports = mContext.getResources().getStringArray(R.array.activities);
			String[] ages = mContext.getResources().getStringArray(R.array.ages);
			String[] locations = mContext.getResources().getStringArray(R.array.locations);
			Log.v(TAG, "aProfile.activity="+aProfile.activity);
			String temp = sports[aProfile.activity];
			Log.v(TAG, "temp="+temp);
			TypedArray imgs = mContext.getResources().obtainTypedArray(R.array.nav_drawer_icons);
			
			vc.iv1.setImageResource(imgs.getResourceId(aProfile.activity,  -1));
			vc.tv1.setText(sports[aProfile.activity]);
			vc.tv2.setText("연령대 : "+ages[aProfile.age]);
			vc.tv4.setText("지역 : "+aProfile.location);
			vc.sw1.setChecked(aProfile.allowDisturbing==1?true:false);
			//vc.sw1.setVisibility(View.GONE);
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

}


