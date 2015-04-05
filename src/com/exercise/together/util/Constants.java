package com.exercise.together.util;

import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.RawContacts;

public final class Constants {
	
	public static final class ACCOUNT{
		public static String NAME_LOCAL = "Pantech Address Book";
		public static String NAME_AAB = "AT&T Address Book";
		public static String NAME_SIM = "Pantech SIM Address Book";
		public static String TYPE_LOCAL = "com.pantech.phone";
		public static String TYPE_AAB = "com.pantech.phone";
		//public static String TYPE_AAB = "com.pantech.aab";
		public static String TYPE_SIM = "com.android.contacts.sim";
		
	}
	
	//not use
	public static final class INFO{
		public static final int GENERAL_INFO = 10;
	    public static final int FRIEND_INFO = 20;
	}

	public static final class MENU{
	    public static final int MY_PROFILE = 0;
	    public static final int BADMINTON = 1;
	    public static final int TABLE_TENNIS = 2;
	    public static final int TENNIS = 3;
	    public static final int INLINE = 4;
	    public static final int SETTING = 5;
	    public static final int DONE_GETTING_REGID = 6;
	    public static final int DONE_SETTING_REGID_TO_SERVER = 7;
	}
	
	public static final class SYNC{
		//Type
	    public static final int TYPE_SLOW = 1;
	    public static final int TYPE_MERGE = 2;
	    public static final int TYPE_REPLACE = 3;
	    //Mode
	    public static final int MODE_SYNC = 1;
	    public static final int MODE_SYNC_SETTING = 2;
	    //public static final int MODE_SAN = 3;
	    
	    //Sync Setting Value
	    public static final int SYNC_SETTING_OFF = 0;
	    public static final int SYNC_SETTING_ON = 1;
	}
	
	public static final class ETC{
		public static final int LOGS = 100;
	}
	
	public static final class ERROR{
		public static final int SUCCESS = 0;
	    public static final int FAIL1 = -1;
	    public static final int FAIL2 = -2;
	    public static final int FAIL3 = -3;
	    public static final int FAIL4 = -4;
	}

	public static final class KEY{
		public static final String EXTRA_MESSAGE = "message";
		public static final String ID = "id";
	    public static final String REGID = "regid";
	    public static final String DONE_REGISTRATION = "done_registration";
	    public static final String NAME = "name";
	    public static final String SPORTS = "favorite_sports";
	    public static final String GENDER = "gender";
	    public static final String AGE = "age";
	    public static final String PHONE = "phone";
	    public static final String EMAIL = "email";
	    public static final String LOCATION = "location";
	    public static final String FROM_HOUR = "from_when";
	    public static final String TO_HOUR = "to_when";
	    public static final String ALLOW_DISTURB = "allow_disturbing";
	    
	    public static final String APP_VERSION = "appVersion";
	    
	}
    
	public static final class PROJECTION{
		public static final String[] CONTACTS = new String[] {
			Contacts._ID,
			Contacts.DISPLAY_NAME,
			Contacts.CONTACT_STATUS,
			Contacts.CONTACT_PRESENCE,
			Contacts.PHOTO_ID,
			Contacts.LOOKUP_KEY,
		};
		public static final String[] RAWCONTACTS = new String[] {
			RawContacts._ID,
			RawContacts.DISPLAY_NAME_PRIMARY,
			RawContacts.ACCOUNT_NAME,
			RawContacts.ACCOUNT_TYPE,
		};
	}
    
	public static final class ACTION{
		public static final String SYNC_SERVICE = "com.pantech.aabsync.service";
		//public static final String SYNC_SERVICE = "com.pantech.aabsync.mainService";
		public static final String MAIN = "com.pantech.aabsync.client.action.MAIN";
		public static final String ACCOUNT_SETTING = "android.settings.SYNC_SETTINGS";
		
		public static final String SOAP = "com.pantech.aabsync.client.action.SOAP";
		public static final String SYNC = "com.pantech.aabsync.client.action.SYNC";
		public static final String SHOW_ABOUT = "com.pantech.aabsync.client.action.ABOUT";
		public static final String LOG_LIST = "com.pantech.aabsync.client.action.LOG_LIST";
		public static final String LOG_VIEW = "com.pantech.aabsync.client.action.LOG_VIEW";
		public static final String SHOW_ERROR = "com.pantech.aabsync.client.action.SHOW_ERROR";
		public static final String LEARN_MORE = "com.pantech.aabsync.client.action.LEARN_MORE";
		public static final String SHOW_TNC = "com.pantech.aabsync.client.action.TNC";
		public static final String ENG_MODE = "com.pantech.aabsync.client.action.ENG_MODE";
		public static final String UI_SERVICE = "com.pantech.aabsync.client.action.UI_SERVICE";
		
	}
	
	public static final class CB_CODE{
		public static final int SOAP_FINISHED = 10;
		public static final int SYNC_PROGRESS = 11;
		public static final int SYNC_FINISHED = 12;
		public static final int SYNC_LOG_DELETED = 13;
		public static final int SYNC_LOG_LOADED = 14;
		public static final int SYNC_STOP_PROGRESS = 15;
		public static final int SYNC_STOP_FINISHED = 16;

	}
	
	public static final class CONTACT{
		public static final int PHONE_CONTACT_CHECKED = 0;
		public static final int PHONE_CONTACT_DELETED = 1;
		public static final int SIM_CONTACT_CHECKED = 2;
		public static final int SIM_CONTACT_COPIED = 3;
		public static final int TIMER_EXIT = 4;
		
	}
	
	/*public enum _Column{
        id(1), regid(2), name(3), gender(4);
   
        private int rank;
       
        private Column(int rank){
            this.rank = rank;
        }
       
        public int getRank(){
            return rank;
        }
	}*/
	
	public enum Column{
		id,
		regid,
		name,
		gender,
		age,
		sports,
		location,
		phone,
		email,
		from_when,
		to_when,
		allow_disturbing
	}
}
