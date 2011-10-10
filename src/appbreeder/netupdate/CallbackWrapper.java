package appbreeder.netupdate;
import org.apache.http.HttpResponse;
 
public class CallbackWrapper implements Runnable {
 
	private ResponseListener callbackActivity;
	private HttpResponse response;
 
	public CallbackWrapper(ResponseListener callback) {
		// TODO Auto-generated constructor stub
		this.callbackActivity = callback;
	}

	public void run() {
		callbackActivity.onResponseReceived(response);
	}
 
	public void setResponse(HttpResponse response) {
		this.response = response;
	}
 
}
