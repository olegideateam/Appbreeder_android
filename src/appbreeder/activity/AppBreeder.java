package appbreeder.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import appbreeder.bll.BaseApplicationManager;
import appbreeder.bll.Constants;
import appbreeder.controls.app.ABAppRecord;
import appbreeder.controls.app.TabsGridAdapter;
import appbreeder.controls.gadget.ABTabRecord;
import appbreeder.controls.gadget.TabsDBManager;
import appbreeder.database.AppDBManager;
import appbreeder.database.DBOpenerHelper;
import appbreeder.netupdate.RequestBuilder;
import appbreeder.netupdate.UpdateManager;

public class AppBreeder extends Activity {

	private LinearLayout llTabsButtons;
	private RelativeLayout rlContentView;
	private LayoutInflater mInflater;
	private LinearLayout gridParent;
	private GridView gvMoreTabs;
	private Context mContext;
	
	private ABTabRecord currentRecord = null;
	private ArrayList<ABTabRecord> tabsList = new ArrayList<ABTabRecord>();;

	private static ProgressDialog progressDialog = null;

	public Handler progressBarHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UpdateManager.SHOW_PROGRESS_DIALOG:
				showProgressDialog();
				break;
			case UpdateManager.DISMISS_PROGRESS_DIALOG:
				hideDialog();
				break;
			case UpdateManager.START_TABS:
				hideDialog();
				BaseApplicationManager.currentDBPath=msg.obj.toString();
				AppBreeder.this.setTabs();
				break;

			case UpdateManager.START_DOWNLOAD_DB:
				UpdateManager.uploadDatabase(msg.obj.toString(),
						progressBarHandler);
				break;

			case UpdateManager.SHOW_ERROR:
				hideDialog();
				BaseApplicationManager.ShowErrorWindow(mContext,
						msg.obj.toString());
				break;
			}

		}
	};

	public void hideDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void showProgressDialog() {
		progressDialog.show();
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mContext = this;
		BaseApplicationManager.initBaseData(this);
		progressDialog = new ProgressDialog(AppBreeder.this);
		progressDialog.setTitle("Please Wait...");
		progressDialog.setCancelable(false);

		llTabsButtons = (LinearLayout) this.findViewById(R.id.llTabsView);
		rlContentView = (RelativeLayout) this.findViewById(R.id.rlContentView);
		mInflater = (LayoutInflater) llTabsButtons.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gridParent = (LinearLayout) mInflater.inflate(R.layout.more_layout,
				null);
		gvMoreTabs = (GridView) gridParent.findViewById(R.id.gvMoreTabs);
		gvMoreTabs.setAdapter(new TabsGridAdapter(this, tabsList));
		RequestBuilder.getServiseHost(this);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		UpdateManager.updateDataBase(1, progressBarHandler);
		// loadingNetDB();

	}

	Handler handlerCallbackgetDataBase = new Handler() {
		public void handleMessage(Message msg) {
		};
	};

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	// public void loadingNetDB() {
	// new Thread() {
	// @Override
	// public void run() {
	// Message showMsg = progressBarHandler.obtainMessage();
	// showMsg.what = SHOW_PROGRESS_DIALOG;
	// progressBarHandler.sendMessage(showMsg);
	//
	// String databaseUrlStr = null;
	//
	// try {
	//
	// DefaultHttpClient client = new DefaultHttpClient();
	// HttpGet uri = new HttpGet(Constants.dataBaseURLString);
	// HttpResponse resp = client.execute(uri);
	//
	// StatusLine status = resp.getStatusLine();
	// if (status.getStatusCode() != 200) {
	// Log.d("Error loading",
	// "HTTP error, invalid server status code: "
	// + resp.getStatusLine());
	// }
	// DocumentBuilderFactory factory = DocumentBuilderFactory
	// .newInstance();
	// DocumentBuilder builder = factory.newDocumentBuilder();
	// Document doc = builder.parse(resp.getEntity().getContent());
	//
	// NodeList list = doc.getChildNodes();
	//
	// Node str = list.item(0);
	//
	// databaseUrlStr = str.getTextContent();
	//
	// Log.v("String", databaseUrlStr);
	//
	// if (databaseUrlStr != null) {
	// URL url = new URL(databaseUrlStr); // you can write here
	// // any link
	// File file = new File(DBOpenerHelper.DATABASE_NAME);
	//
	// long startTime = System.currentTimeMillis();
	// Log.d("AppBreeder", "download begining");
	// Log.d("AppBreeder", "download url:" + url);
	// Log.d("AppBreeder", "downloaded file name:"
	// + DBOpenerHelper.DATABASE_NAME);
	// /* Open a connection to that URL. */
	// URLConnection ucon = url.openConnection();
	// /*
	// * Define InputStreams to read from the URLConnection.
	// */
	// InputStream is = ucon.getInputStream();
	// BufferedInputStream bis = new BufferedInputStream(is);
	//
	// /*
	// * Read bytes to the Buffer until there is nothing more
	// * to read(-1).
	// */
	// ByteArrayBuffer baf = new ByteArrayBuffer(50);
	// int current = 0;
	// while ((current = bis.read()) != -1) {
	// baf.append((byte) current);
	// }
	//
	// /* Convert the Bytes read to a String. */
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(baf.toByteArray());
	// fos.close();
	// Log.d("AppBreeder",
	// "download ready in"
	// + ((System.currentTimeMillis() - startTime) / 1000)
	// + " sec");
	// }
	// } catch (MalformedURLException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// Log.v("IOException", e.toString());
	// } catch (ParserConfigurationException e) {
	// e.printStackTrace();
	// } catch (IllegalStateException e) {
	// e.printStackTrace();
	// } catch (SAXException e) {
	// e.printStackTrace();
	// }
	//
	// Message dissmisMsg = progressBarHandler.obtainMessage();
	// dissmisMsg.what = DISMISS_PROGRESS_DIALOG;
	// progressBarHandler.sendMessage(dissmisMsg);
	// if (databaseUrlStr == null) {
	// AlertDialog.Builder builder = new AlertDialog.Builder(
	// AppBreeder.this);
	// builder.setTitle("Check your internet connection!");
	// builder.setPositiveButton("OK", null);
	// builder.setCancelable(true);
	// builder.show();
	// } else {
	// Message setTub = progressBarHandler.obtainMessage();
	// setTub.what = SETUP_TABS;
	// progressBarHandler.sendMessage(setTub);
	// }
	//
	// }
	// }.run();
	// }

	public void setTabs() {
		AppDBManager appDbManager = new AppDBManager(this);

		ArrayList<ABAppRecord> apps = appDbManager.getAppRecords();
		if (apps.size() > 0) {
			BaseApplicationManager.currentApp = apps.get(0);
			initAppData();
			TabsDBManager tabsManager = new TabsDBManager(this);

			tabsList = tabsManager.getTabsRecords(Integer.toString(BaseApplicationManager.currentApp
					.getID()));

			llTabsButtons.removeAllViews();

			int faceTabsNumber = java.lang.Math.min(tabsList.size(), 4);

			for (int i = 0; i < faceTabsNumber; i++) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				ImageButton but = (ImageButton) tab
						.findViewById(R.id.ibTabButton);
				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText(tabsList.get(i).getTitle());
				tv.setTextColor(0xFFFFFFFF);
				but.setId(i + 1);
				but.setTag(tabsList.get(i));
				tab.setId(i + 1);
				llTabsButtons.addView(tab, i);
				if (i == 0) {
					this.tupTab(but);
				}
			}
			if (tabsList.size() == 5) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				ImageButton but = (ImageButton) tab
						.findViewById(R.id.ibTabButton);
				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText(tabsList.get(5).getTitle());
				but.setId(5);
				but.setTag(tabsList.get(5));
				tab.setId(5);
				llTabsButtons.addView(tab, 4);
			} else if (tabsList.size() > 5) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				ImageButton but = (ImageButton) tab
						.findViewById(R.id.ibTabButton);
				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText("More");
				but.setId(0);
				tab.setId(0);
				llTabsButtons.addView(tab, 4);
				gvMoreTabs.setAdapter(new TabsGridAdapter(this, tabsList));
			}

		}
		

	}
	
	public void initAppData()
	{
		setTitle(BaseApplicationManager.currentApp.getAppName());
	}
	
	public void tupTab(View v) {
		int lastSelected = tabsList.indexOf(currentRecord);
		int i = v.getId();
		if ((currentRecord == null && v.getId() < 4)
				|| (lastSelected > 4 && v.getId() < 4)) {
			View lastTab = llTabsButtons.getChildAt(4);
			if (lastTab != null) {
				lastTab.setBackgroundColor(0x00000000);
				TextView tv = (TextView) lastTab.findViewById(R.id.tvTabButton);
				tv.setTextColor(0xFFFFFFFF);
			}
		}
		if (lastSelected != -1) {
			if (lastSelected == 0 && v.getId() > 4) {

			} else {
				View lastTab = llTabsButtons.getChildAt(lastSelected);
				if (lastTab != null) {
					lastTab.setBackgroundColor(0x00000000);
					TextView tv = (TextView) lastTab
							.findViewById(R.id.tvTabButton);
					tv.setTextColor(0xFFFFFFFF);
				}

			}
		}
		rlContentView.removeAllViews();
		if (v.getId() == 0) {
			rlContentView.setBackgroundColor(0xFF000000);
			rlContentView.addView(gridParent);
			currentRecord = null;
			View lastTab = llTabsButtons.getChildAt(4);
			lastTab.setBackgroundColor(0xCCFFFFFF);
			TextView tv = (TextView) lastTab.findViewById(R.id.tvTabButton);
			tv.setTextColor(0xFF000000);
		} else {
			int _id = v.getId() - 1;
			currentRecord = tabsList.get(_id);
			if (_id < 4) {
				View lastTab = llTabsButtons.getChildAt(_id);
				lastTab.setBackgroundColor(0xCCFFFFFF);
				TextView tv = (TextView) lastTab.findViewById(R.id.tvTabButton);
				tv.setTextColor(0xFF000000);
			}

			_id = _id % 4;

			switch (_id) {
			case 0:
				rlContentView.setBackgroundColor(0xFF000000);
				break;
			case 1:
				rlContentView.setBackgroundColor(0xFFFF0000);
				break;
			case 2:
				rlContentView.setBackgroundColor(0xFFFFFF00);
				break;
			case 3:
				rlContentView.setBackgroundColor(0xFFFFFFFF);
				break;
			default:

				break;
			}

		}
	}
	public void setSelectedTub()
	{
		rlContentView.setBackgroundColor(0xFF000000);
		rlContentView.addView(gridParent);
		currentRecord = null;
		View lastTab = llTabsButtons.getChildAt(4);
		lastTab.setBackgroundColor(0xCCFFFFFF);
		TextView tv = (TextView) lastTab.findViewById(R.id.tvTabButton);
		tv.setTextColor(0xFF000000);
	}
	public void setUnSelectedTub(View view)
	{
	
		
	}
	
}