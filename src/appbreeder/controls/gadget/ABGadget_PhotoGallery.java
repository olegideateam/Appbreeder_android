package appbreeder.controls.gadget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;

public class ABGadget_PhotoGallery extends ABTabRecord {
	private String childItemsLastChangeDate;
	private String bGColor;
	private String mode;

	public static String c_G_ChildItemsLastChangeDate = "G_ChildItemsLastChangeDate";
	public static String c_BGColor = "BGColor";
	public static String c_Mode = "Mode";

	public ABGadget_PhotoGallery(SQLiteDatabase sqldb, Cursor cur) {
		super(sqldb, cur);
		Cursor cursor = super.getTabelCursor(sqldb, "ABGadget_PhotoGallery");
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			childItemsLastChangeDate = super.getValueString(cursor,
					c_G_ChildItemsLastChangeDate);
			bGColor = super.getValueString(cursor, c_BGColor);
			mode = super.getValueString(cursor, c_Mode);
		}
	}

	@Override
	public View getTabView(Context context) {
		return super.getTabView(context);
	}

	public String getChildItemsLastChangeDate() {
		return childItemsLastChangeDate;
	}

	public void setChildItemsLastChangeDate(String childItemsLastChangeDate) {
		this.childItemsLastChangeDate = childItemsLastChangeDate;
	}

	public String getBGColor() {
		return bGColor;
	}

	public void setBGColor(String bGColor) {
		this.bGColor = bGColor;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
