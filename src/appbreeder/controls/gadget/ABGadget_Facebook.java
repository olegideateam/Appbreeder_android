package appbreeder.controls.gadget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

public class ABGadget_Facebook extends ABTabRecord{
	
	public static String c_RSSURL = "RSSURL";

	public ABGadget_Facebook (SQLiteDatabase sqldb,Cursor cur)
	{
		super(sqldb,cur);
//		Cursor cursor=super.getTabelCursor(sqldb, this.getClass().getName());
//		rssFeedCanel=super.getValueString(cursor,c_RSSURL);
	}

	

	@Override
	public View getTabView(Context context) {
		return super.getTabView(context);
	}
}
