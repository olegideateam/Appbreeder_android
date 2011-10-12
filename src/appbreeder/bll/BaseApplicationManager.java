package appbreeder.bll;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;



import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
import appbreeder.activity.R;
import appbreeder.controls.app.ABAppRecord;
import appbreeder.netupdate.RequestBuilder;

public class BaseApplicationManager {

	private static Context appContext;
	public static String baseDomainForResources;
	public static String externalBasePath;
	public static int appID;
	public static String appBrendName;
	public static String currentDBPath;
	public static ABAppRecord currentApp = null;
	public static ImageLoader imageLoader;
	public static void initBaseData(Context mContext)
	{
		appContext=mContext;
		externalBasePath=Environment.getExternalStorageDirectory().getAbsolutePath();
		RequestBuilder.getServiseHost(mContext);
		appID=Integer.parseInt(mContext.getResources().getString(R.string.appID));
		appBrendName=mContext.getResources().getString(R.string.app_brend);
		baseDomainForResources=mContext.getResources().getString(R.string.baseDomainForResources);
		imageLoader = new ImageLoader(mContext);
	}

	public static String getGuid() {
		return "" + new Date().getTime();
	}
	public static void ShowErrorWindow(Context mContext, String errorMessage) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		alertBuilder.setMessage(errorMessage).setTitle("Error");
		alertBuilder.setNegativeButton("Close", null);
		alertBuilder.show();
	}
	public static void ShowMessageWindow(Context mContext,String title, String message) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
		alertBuilder.setMessage(message).setTitle(title);
		alertBuilder.setNegativeButton("Close", null);
		alertBuilder.show();
	}
	public static String streamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {

			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
				// Log.v("sb",line );
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static JSONObject getJsonOnHttpResponse(HttpResponse response) {

		JSONObject tryJson = null;
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = streamToString(instream);
				tryJson = new JSONObject(result);
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tryJson;
	}
	public static InputStream getStreamFromUrl(String urlString)
			throws MalformedURLException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlString);
		HttpResponse response = httpClient.execute(request);
		return response.getEntity().getContent();
	}

	public static String saveInputStreamToTemp(String fileName, InputStream in) {
		File foldePath = Constants.getFileForFolder(Constants
				.getPathTempFolder().getAbsolutePath() + "/" + getGuid());
		String pathToFile = foldePath.getAbsolutePath() + fileName;
		if(!saveInputStreamToFile(pathToFile,in))
		{
			pathToFile ="";
		}
		return pathToFile ;

	}
	public static boolean saveInputStreamToFile(String filePath, InputStream in) {
		boolean resalt=false;
		try {
			FileOutputStream fos = new FileOutputStream(filePath, false);
			OutputStream os = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int byteRead = 0;

			while ((byteRead = in.read(buffer)) != -1) {
				os.write(buffer, 0, byteRead);
			}
			os.flush();
			os.close();
			fos.close();
			resalt=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resalt;

	}
}
