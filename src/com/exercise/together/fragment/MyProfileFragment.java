package com.exercise.together.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.together.MyProfileEditActivity;
import com.exercise.together.R;
import com.exercise.together.adapter.AListAdapter;
import com.exercise.together.util.Bean;

public class MyProfileFragment extends Fragment {
	
	ArrayList<Bean> mProfiles;
	
	public MyProfileFragment(ArrayList<Bean> profiles){
		mProfiles = profiles;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
         
        setHasOptionsMenu(true);
        
        return rootView;
    }
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		ListView lv = (ListView)getActivity().findViewById(R.id.fragment_list_lv);
		AListAdapter la = new AListAdapter(getActivity(), R.layout.list_layout, mProfiles);
		TextView emptyView = (TextView)getActivity().findViewById(R.id.fragment_list_lv_empty);
        lv.setEmptyView(emptyView);
        
		lv.setAdapter(la);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.profile, menu); 
	    

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 switch(item.getItemId()) {
	        case R.id.create :
	            String message = "create is selected";
	            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
	            Intent i = new Intent(getActivity().getApplicationContext(), MyProfileEditActivity.class);
	            startActivity(i);
	            break;
	        }

		
		return super.onOptionsItemSelected(item);
	}
	
	
}
