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


import android.content.Context;
import android.os.Environment;
import appbreeder.activity.R;
import appbreeder.netupdate.RequestBuilder;

public class BaseApplicationManager {

	private static Context appContext;
	public static String externalBasePath;
	public static int appID;
	public static int appBrendName;

	public static void initBaseData(Context mContext)
	{
		appContext=mContext;
		externalBasePath=Environment.getExternalStorageState();
		RequestBuilder.getServiseHost(mContext);
		appID=Integer.parseInt(mContext.getResources().getString(R.string.appID));
		appBrendName=Integer.parseInt(mContext.getResources().getString(R.string.app_brend));
	}

	public static String getGuid() {
		return "" + new Date().getTime();
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
		try {
			FileOutputStream fos = new FileOutputStream(pathToFile, false);
			OutputStream os = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int byteRead = 0;

			while ((byteRead = in.read(buffer)) != -1) {
				os.write(buffer, 0, byteRead);
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			pathToFile ="";
		}
		return pathToFile ;

	}
}
