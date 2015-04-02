package com.exercise.together.util;

public class Config {
	
	public static String TNC = null;
    public static final String  NOTEPAD_CONFIG_DATA        = "config_data";
    public static final String  NOTEPAD_CONFIG_SORT_MODE = "config_sort_mode";
    public static final String  NOTEPAD_CONFIG_NOTE_LIST   = "config_note_list";
    public static final String  NOTEPAD_CONFIG_PASSWORD   = "config_password";
    public static final String  NOTEPAD_CONFIG_PASSWORD_OFF   = "config_password_off";
    public static final String  NOTEPAD_CONFIG_ACCOUNT_ID   = "config_account_id";
    public static final String  NOTEPAD_CONFIG_ACCOUNT_AUTHTOKEN   = "config_account_authToken";
    public static final String  NOTEPAD_CONFIG_LAST_SYNC_TIME   = "config_last_sync_time";

    public static final int     NOTEPAD_SORT_DESC   = 0;
    public static final int     NOTEPAD_SORT_ASC    = 1;
    public static final int     NOTEPAD_SORT_TEXT_ASC   = 2;
    public static final int     NOTEPAD_SORT_TEXT_DESC    = 3;
//    public static final int     NOTEPAD_SORT_SKIN   = 4;
    
    public static final int     NOTEPAD_NOTE_LIST_MODE_THUMB    = 0;
    public static final int     NOTEPAD_NOTE_LIST_MODE_LIST     = 1;

    public static int           mNoteListMode = NOTEPAD_NOTE_LIST_MODE_LIST;
    public static int           mSortMode = NOTEPAD_SORT_DESC;
    public static boolean		mPassword_off = false;
    public static String        mPassword = "";
    public static long          mLastSyncTime = 0;
    public static String        mAccountId = "";
    public static String        mAuthToken = "";
    
    public static String SENDER_ID = "1073359547065";
    
}
