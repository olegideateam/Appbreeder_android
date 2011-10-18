package appbreeder.netupdate;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import appbreeder.bll.BaseApplicationManager;
import appbreeder.bll.Constants;

public class UpdateManager {
	public static final int DATABASE_EXIST = 1;
	public static final int DATABASE_NOT_EXIST = 2;
	public static final int DATABASE_SAVED = 3;
	public static final int START_DOWNLOAD_DB = 4;
	public static final int START_TABS = 5;
	public static final int SHOW_ERROR = 6;
	public static final int SHOW_PROGRESS_DIALOG = 7;
	public static final int DISMISS_PROGRESS_DIALOG = 8;

	public static void updateDataBase(int appID, final Handler callbackHandler) {
		
		Message msgDialog = new Message();
		msgDialog.what = SHOW_PROGRESS_DIALOG;
		callbackHandler.sendMessage(msgDialog);
		Client.sendRequest(RequestBuilder.buildReqest_GetSQLiteDatabase(appID),
				new ResponseListener() {
					@Override
					public void onResponseReceived(final HttpResponse response) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								Message msg = new Message();
								String json = null;
								try {
									json = BaseApplicationManager
											.streamToString(response
													.getEntity().getContent());
								} catch (Exception e) {
									e.printStackTrace();
									msg.what = SHOW_ERROR;
									msg.obj = "Server Error pleas try again.";
									if (callbackHandler != null) {
										callbackHandler.sendMessage(msg);
									}
								}

								Log.i("JSONObject reqest", "resalt" + json);
								JSONObject jsonobj = null;
								try {
									jsonobj = new JSONObject(json);

									
									if (jsonobj.has("d")) {
										msg.what = START_DOWNLOAD_DB;
										msg.obj = jsonobj.getString("d");
										if (callbackHandler != null)
											callbackHandler.sendMessage(msg);
									}else
									{
										msg.what = SHOW_ERROR;
										if(jsonobj.has("Message"))
											msg.obj = jsonobj.getString("Message");
										else
											msg.obj =json;
										if (callbackHandler != null)
											callbackHandler.sendMessage(msg);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
								// Log.i("JSONObject reqest", "resalt ogj"
								// + jsonobj);
								// try {
								// Log.i("JSONObject reqest", "resalt ogj"
								// + jsonobj.getString("d"));
								// } catch (JSONException e) {
								// // TODO Auto-generated catch block
								// e.printStackTrace();
								// }

								// if(BaseApplicationManager.saveInputStreamToFile(file.getAbsolutePath(),
								// in))
								// Message msg=new Message();
								// msg.what=DATABASE_EXIST;
								// if (callbackHandler != null) {
								// callbackHandler.sendMessage(msg);
								// }

							}
						}).start();
					}
				});

	}

	public static Handler handlerUploadDatabase = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DATABASE_EXIST:

				break;
			case DATABASE_NOT_EXIST:

				break;
			case DATABASE_SAVED:

				break;

			case START_DOWNLOAD_DB:
				uploadDatabase(msg.obj.toString(), handlerUploadDatabase);
				break;
			}
		};
	};

	public static void uploadDatabase(final String urlToDB,
			final Handler callbackHandler) {
		URL url = null;

		try {
			url = new URL(urlToDB);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String fileName = url.getFile();
		fileName = fileName.substring(fileName.lastIndexOf("/"));
		final File fileDB = Constants.getPathDBFolder(fileName);
		if (fileDB.exists()) {
			Client.sendRequest(RequestBuilder.buildReqest_LoadDB(urlToDB),
					new ResponseListener() {
						@Override
						public void onResponseReceived(
								final HttpResponse response) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									Message msg = new Message();
									InputStream in = null;

									try {
										in = response.getEntity().getContent();
									} catch (Exception e) {
										e.printStackTrace();
										msg.what = SHOW_ERROR;
										msg.obj = "Databse save Error";
										if (callbackHandler != null) {
											callbackHandler.sendMessage(msg);
										}
									}

									if (in != null) {

										if (BaseApplicationManager
												.saveInputStreamToFile(fileDB
														.getAbsolutePath(), in)) {
											msg.what = START_TABS;
											msg.obj = fileDB.getAbsoluteFile();
											if (callbackHandler != null) {
												callbackHandler
														.sendMessage(msg);
											}
										} else {
											msg.what = SHOW_ERROR;
											msg.obj = "Databse save Error";
											if (callbackHandler != null) {
												callbackHandler
														.sendMessage(msg);
											}
										}
									}

								}
							}).start();
						}
					});
		} else {
			Message msg = new Message();
			msg.what = START_TABS;
			msg.obj = fileDB.getAbsoluteFile();
			callbackHandler.sendMessage(msg);
		}
	}

	public static void updateAppInformaion() {

	}

	public static void updateGadgetInformaion() {

	}

	public static void updateGadgetInformaionByType() {

	}
}
