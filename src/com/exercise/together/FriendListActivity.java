package com.exercise.together;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.exercise.together.fragment.FriendListFragment;
import com.exercise.together.util.Bean;

public class FriendListActivity extends Activity {

	private static final String TAG = "FriendListActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_friendlist);
		
		setActionBar(R.string.app_name);
		
		Log.v(TAG, "onCreate");
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		Intent i = getIntent();
		Bundle bd = i.getBundleExtra("friends");
		ArrayList<Bean> friends = bd.getParcelableArrayList("friends");
		FriendListFragment mFragment = new FriendListFragment(friends);
		ft.replace(R.id.frag1, mFragment, null);
		ft.commit();
	}

    void setActionBar(int resTitle){
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE,
                    ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
            actionBar.setTitle(getText(resTitle));
        }

    }
}
