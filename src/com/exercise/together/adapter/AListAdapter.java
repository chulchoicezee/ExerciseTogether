package com.exercise.together.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.exercise.together.R;
import com.exercise.together.util.Bean;
import com.exercise.together.util.FriendInfo;
import com.exercise.together.util.ProfileInfo;

public class AListAdapter extends ArrayAdapter<Bean>{

	private static final String TAG = "AListAdapter";
	Context mContext;
	ArrayList<Bean> mItems;
	int mResRowId;
	
	public AListAdapter(Context context, int resId, ArrayList<Bean> items) {
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
			vc.tv1 = (TextView)v.findViewById(R.id.textView1);
			vc.tv2 = (TextView)v.findViewById(R.id.textView2);
			vc.sw1 = (Switch)v.findViewById(R.id.switch1);
			v.setTag(vc);
		}
		
		vc = (ViewCache) v.getTag();
		
		Bean bean = mItems.get(position);
		
		if(bean instanceof FriendInfo){
			Log.v(TAG, "bean instanceof FriendInfo");
			FriendInfo aFriend = (FriendInfo)mItems.get(position);
			//FriendInfo info = getItem(position);
			vc.tv1.setText(aFriend.email);
		}else if(bean instanceof ProfileInfo){
			Log.v(TAG, "bean instanceof ProfileInfo");
			ProfileInfo aProfile = (ProfileInfo)mItems.get(position);
			String activity = null;
			switch(aProfile.activity){
			case 1:
				activity = "배드민턴";
				break;
			case 2:
				activity = "탁구";
				break;
			case 3:
				activity = "테니스";
				break;
			case 4:
				activity = "인라인";
				break;
			default:
				break;
			}
			vc.tv1.setText(activity);
			vc.tv2.setText(aProfile.location);
			vc.sw1.setVisibility(View.GONE);
		}
		
		
		return v;
	}

	class ViewCache{
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		Switch sw1;
	}

}


