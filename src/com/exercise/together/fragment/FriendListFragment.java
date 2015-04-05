package com.exercise.together.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.exercise.together.R;
import com.exercise.together.adapter.AListAdapter;
import com.exercise.together.util.Bean;

public class FriendListFragment extends Fragment{

	ArrayList<Bean> mFriendList;
	
	public FriendListFragment(ArrayList<Bean> infoList){
		mFriendList = infoList;
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
         
        return rootView;
    }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		ListView lv = (ListView)getActivity().findViewById(R.id.fragment_list_lv);
		AListAdapter la = new AListAdapter(getActivity(), R.layout.list_layout, mFriendList, null);
		TextView emptyView = (TextView)getActivity().findViewById(R.id.fragment_list_lv_empty);
        lv.setEmptyView(emptyView);
        
		lv.setAdapter(la);
	}
	
}
