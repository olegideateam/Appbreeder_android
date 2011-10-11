package appbreeder.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import appbreeder.bll.BaseApplicationManager;

public class DBOpenerHelper extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	    
/*------------- Tables Ver 01 (don't change this please)-----------------------------------------------*/
	
    public static final String ABAPP_TABLE_NAME = "ABApp";
    public static final String ABGADGET_FACEBOOK_TABLE_NAME = "ABGadget_Facebook";
    public static final String ABGADGET_HOME_TABLE_NAME = "ABGadget_Home";
    public static final String ABGADGET_INFO_ONLEVEL_TABLE_NAME = "ABGadget_Info_OneLevel";
    public static final String ABGADGET_INFO_THREELEVEL_TABLE_NAME = "ABGadget_Info_ThreeLevel";
    public static final String ABGADGET_INFO_TWOLEVEL_TABLE_NAME = "ABGadget_Info_TwoLevel";
    public static final String ABGADGET_PHOTOGALLERY_TABLE_NAME = "ABGadget_PhotoGallery";
    public static final String ABGADGET_RSSFEED_TABLE_NAME = "ABGadget_RSSFeed";
    public static final String ABGADGET_TWITTER_TABLE_NAME = "ABGadget_Twitter";
    public static final String ABSKIN_TABLE_NAME = "ABSkin";
    public static final String ABTAB_TABLE_NAME = "ABTab";
    
    //public static final String DATABASE_NAME = "/data/data/com.appbreeder.android/appbreeder.db3";

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.e("DBOpenerHelper", "onUpgrade");
		try{			

		}catch (SQLException e) {
			// TODO: handle exception
			Log.e("SQLException", e.getMessage());
			//Toast.makeText(context, text, duration)
		}
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		Log.e("DBOpenerHelper", "onUpgrade");

	}
	
	public DBOpenerHelper(Context context){
		super(context,BaseApplicationManager.currentDBPath, null, DATABASE_VERSION);
		//this.context = context;
		Log.e("DBOpenerHelper", "Constructor");
	}

}
