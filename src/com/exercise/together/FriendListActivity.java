package com.exercise.together;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.exercise.together.adapter.AListAdapter;
import com.exercise.together.util.Bean;
import com.exercise.together.util.ProfileInfo;

public class FriendListActivity extends Activity {

	private static final String TAG = "FriendListActivity";
	private AListAdapter mListAdapter;
	private ArrayList<ProfileInfo> mProfiles;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_friend_list);
		
		setActionBar(R.string.app_name);
		
		Log.v(TAG, "onCreate");
		
		Intent i = getIntent();
		Bundle bd = i.getBundleExtra("friends");
		ArrayList<Bean> friends = bd.getParcelableArrayList("friends");
		
		ListView lv = (ListView)findViewById(R.id.friendlist_lv);
		mListAdapter = new AListAdapter<ProfileInfo>(this, R.layout.list_layout, mProfiles, null);
		lv.setAdapter(mListAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
		//FragmentManager fm = getFragmentManager();
		//FragmentTransaction ft = fm.beginTransaction();
		//FriendListFragment mFragment = new FriendListFragment(friends);
		//ft.replace(R.id.frag1, mFragment, null);
		//ft.commit();
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
