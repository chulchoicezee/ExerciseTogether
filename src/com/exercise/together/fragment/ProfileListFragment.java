package com.exercise.together.fragment;

import java.util.ArrayList;

import org.apache.http.HttpStatus;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exercise.together.MainActivity;
import com.exercise.together.ProfileDetailActivity;
import com.exercise.together.ProfileEditActivity;
import com.exercise.together.R;
import com.exercise.together.adapter.AListAdapter;
import com.exercise.together.util.Constants;
import com.exercise.together.util.ProfileInfo;
import com.exercise.together.util.ProfileRegistration;
import com.exercise.together.util.ProfileRegistration.ProfileListener;
import com.exercise.together.util.ProfileRegistration.Wrapper;

public class ProfileListFragment extends Fragment implements ProfileListener, OnItemClickListener {
	
	private static final String TAG = "MyProfileFragment";
	ArrayList<ProfileInfo> mProfiles;
	ProfileRegistration mRegiHelper = null;
	AListAdapter mListAdapter = null;
	ListView mListview = null;
	ProgressDialog mPd = null;
	
	public ProfileListFragment(ArrayList<ProfileInfo> profiles){
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
		
		
		mRegiHelper = new ProfileRegistration(getActivity());
		mRegiHelper.setProfileListener(this);
		
		mListview = (ListView)getActivity().findViewById(R.id.fragment_list_lv);
		//Log.v(TAG, "mProfiles="+mProfiles+", mProfiles.len="+mProfiles.size());
		mListAdapter = new AListAdapter<ProfileInfo>(getActivity(), R.layout.list_layout, mProfiles, mRegiHelper);
		
		mListview.setAdapter(mListAdapter);
		mListview.setOnItemClickListener(this);
		registerForContextMenu(mListview);
		Log.v(TAG, "mListview="+mListview);
		final SharedPreferences prefs = getActivity().getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		boolean done = prefs.getBoolean(Constants.KEY.DONE_REGISTRATION, false);
	    Log.v(TAG, "done="+done);
		if(done){
	    	String regid = prefs.getString(Constants.KEY.REGID, "");
	    	mRegiHelper.getProfileAsync(regid);//onLoadedProfile에서 listview로 바인딩함.
	        
		}else{
			mRegiHelper.getRegidServerAsync();
		}
		/*lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ProfileInfo pi = (ProfileInfo) parent.getItemAtPosition(position);
				Log.v(TAG, "pi.name"+pi.name);
				mRegiHelper.deleteProfileAsync(pi);
				return false;
			}
			
		});*/
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
	            Intent i = new Intent(getActivity().getApplicationContext(), ProfileEditActivity.class);
	            startActivity(i);
	            break;
	        }

		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Log.v(TAG, "parent="+parent+", view="+view);
		ProfileInfo pi = (ProfileInfo)parent.getAdapter().getItem(position);
		
		Intent i = new Intent(getActivity(), ProfileDetailActivity.class);
		i.putExtra(Constants.KEY.PROFILE_INFO, pi);
		startActivity(i);
		
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Log.v(TAG, ""+info.id);
		ProfileInfo pi = (ProfileInfo)mListAdapter.getItem((int)info.id);
		Log.v(TAG, "pi.name="+pi.name);
		
		switch(item.getItemId()){
		case 1:
			Log.v(TAG, "onContextItemSelected");
			mRegiHelper.deleteProfileAsync(pi);
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(Menu.NONE, 1, Menu.NONE, "Delete?");
	}

	@Override
	public void onLoadedProfile(ArrayList<ProfileInfo> aList) {
		// TODO Auto-generated method stub
		//리스트뷰에 pi를 장착
		mProfiles = aList;
		mListAdapter.clear();
		for(ProfileInfo pi : mProfiles){
			mListAdapter.add(pi);
		}
		TextView emptyView = (TextView)getActivity().findViewById(R.id.fragment_list_lv_empty);
		emptyView.setText(R.string.profileEmpty);
		mListview.setEmptyView(emptyView);
        
		//mListAdapter.notifyDataSetChanged();
		/*Fragment fragment = new MyProfileFragment(aList);
		
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
		}*/

	}

	@Override
	public void onResultProfileSend(Wrapper wrapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultRegidServer(String param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResultProfileDelete(Wrapper wrapper) {
		// TODO Auto-generated method stub
		if(wrapper.responseCode == HttpStatus.SC_OK){
			String regid = mRegiHelper.getRegidLocal();
			mRegiHelper.getProfileAsync(regid);
		}
		Toast.makeText(getActivity(), wrapper.responseString, Toast.LENGTH_SHORT).show();
	}
	
	Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.v(TAG, "handler");
			//mListAdapter.notifyDataSetChanged();
			
		}
	};

	@Override
	public void onResultProfileUpdate(Wrapper wrapper) {
		// TODO Auto-generated method stub
		//mListAdapter.notifyDataSetChanged();
		if(wrapper.responseCode == HttpStatus.SC_OK){
			ProfileInfo pi = (ProfileInfo)mListAdapter.getListItem(Integer.parseInt(wrapper.position));
			Log.v(TAG, "onResultProfileUpdate pi.allow="+pi.allow_disturbing);
			//pi.allowDisturbing = Integer.parseInt(wrapper.position);
			//mListAdapter
			//mListAdapter.notifyDataSetChanged();
		}
		Log.v(TAG, "onResultProfileUpdate wrapper.allow="+wrapper.allow_diturbing);
		Toast.makeText(getActivity(), wrapper.responseString, Toast.LENGTH_SHORT).show();
	}


}
