package appbreeder.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import appbreeder.bll.BaseApplicationManager;
import appbreeder.controls.app.ABAppRecord;
import appbreeder.controls.app.TabsGridAdapter;
import appbreeder.controls.gadget.ABTabRecord;
import appbreeder.controls.gadget.TabsDBManager;
import appbreeder.database.AppDBManager;
import appbreeder.netupdate.RequestBuilder;
import appbreeder.netupdate.UpdateManager;

public class AppBreeder extends BaseActivity {

	private LinearLayout llTabsButtons;
	private RelativeLayout rlContentView;
	private LayoutInflater mInflater;
	private LinearLayout gridParent;
	private GridView gvMoreTabs;
	private Context mContext;

	private ABTabRecord currentRecord = null;
	private ArrayList<ABTabRecord> tabsList = new ArrayList<ABTabRecord>();;

	private static ProgressDialog progressDialog = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		setTitle("Loading...");
		super.setNavBarGradient(0xFFEEEEEE, 0xFF000000);

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
		// this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		UpdateManager.updateDataBase(1, progressBarHandler);
		// loadingNetDB();

	}

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
				BaseApplicationManager.currentDBPath = msg.obj.toString();
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

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

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

			tabsList = tabsManager.getTabsRecords(Integer
					.toString(BaseApplicationManager.currentApp.getID()));
			RequestBuilder.buildReqest_checkABTab(BaseApplicationManager.currentApp.getID(), tabsList );
			llTabsButtons.removeAllViews();

			int faceTabsNumber = java.lang.Math.min(tabsList.size(), 4);

			for (int i = 0; i < faceTabsNumber; i++) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				View vTab = tab.findViewById(R.id.rrTabButton);
				ImageView but = (ImageView) tab.findViewById(R.id.ibTabButton);
				but.setTag(tabsList.get(i).getIcon());
				BaseApplicationManager.imageLoader.DisplayImage(tabsList.get(i)
						.getIcon(), (Activity) but.getContext(), but);
				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText(tabsList.get(i).getTitle());
				tv.setTextColor(0xFFFFFFFF);
				vTab.setId(i + 1);
				vTab.setTag(tabsList.get(i));
				llTabsButtons.addView(tab, i);
				if (i == 0) {
					this.tupTab(vTab);
				}
			}
			if (tabsList.size() == 5) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				View vTab = tab.findViewById(R.id.rrTabButton);

				ImageView but = (ImageView) tab.findViewById(R.id.ibTabButton);
				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText(tabsList.get(5).getTitle());
				vTab.setId(5);
				llTabsButtons.addView(tab, 4);
			} else if (tabsList.size() > 5) {
				View tab = mInflater.inflate(R.layout.tab_button_layout, null);
				View vTab = tab.findViewById(R.id.rrTabButton);

				ImageView but = (ImageView) tab.findViewById(R.id.ibTabButton);
				but.setImageResource(R.drawable.stub);

				TextView tv = (TextView) tab.findViewById(R.id.tvTabButton);
				tv.setText("More");

				// but.setId(0);
				vTab.setId(0);
				llTabsButtons.addView(tab, 4);
				gvMoreTabs.setAdapter(new TabsGridAdapter(this, tabsList));
			}

		}

	}

	public void initAppData() {
		// setTitle(BaseApplicationManager.currentApp.getAppName());
	}

	View lastSelectedView;

	public void tupTab(View v) {

		if (lastSelectedView != null && v.getId() <= 4) {
			if (lastSelectedView.getId() > 4) {
				lastSelectedView = ((ViewGroup) llTabsButtons.getChildAt(4))
						.getChildAt(0);
			}
			setUnSelectedTub(lastSelectedView);
		}
		rlContentView.removeAllViews();
		if (v.getId() == 0) {
			// rlContentView.setBackgroundColor(0xFF0000FF);
			rlContentView.addView(gridParent);
			currentRecord = null;
			setTitle("More");

			setSelectedTub(v);
		} else {
			if (lastSelectedView != null && lastSelectedView.getTag() != null) {
				((ABTabRecord) lastSelectedView.getTag()).closeGadget();
			}
			currentRecord = (ABTabRecord) v.getTag();
			setTitle(currentRecord.getTitle());
			rlContentView.addView(currentRecord.getTabView(this),
					new ViewGroup.LayoutParams(-1, -1));
			if (v.getId() <= 4) {
				setSelectedTub(v);
			}
		}
		lastSelectedView = v;
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		alertBuilder.setMessage("Do you  want exit from app?");
		alertBuilder.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				});
		alertBuilder.setNegativeButton("No", null);
		alertBuilder.show();
	}

	public void setSelectedTub(View v) {
		v.setBackgroundColor(0xCCFFFFFF);
		TextView tv = (TextView) v.findViewById(R.id.tvTabButton);
		tv.setTextColor(0xFF000000);
	}

	public void setUnSelectedTub(View v) {
		v.setBackgroundColor(0x00000000);
		TextView tv = (TextView) v.findViewById(R.id.tvTabButton);
		tv.setTextColor(0xFFFFFFFF);

	}

}