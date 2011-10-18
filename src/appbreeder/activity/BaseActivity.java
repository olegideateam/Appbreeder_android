package appbreeder.activity;


import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BaseActivity extends Activity {
	private boolean customTitleSupported;
	private View paretTitleView;
	private TextView titleTextView ;
	private ProgressBar progressBarCustomTitle ;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		   getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                   WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 //  requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	}
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		if (customTitleSupported)
		{
	        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
	        customTitleSupported = false;
	    }
		paretTitleView=((View)findViewById(R.id.RelativeLayoutViewTitle).getParent());
		titleTextView = (TextView) this.findViewById(R.id.TextView01_TitleBar);
		progressBarCustomTitle =(ProgressBar)findViewById(R.id.ProgressBarCustomTitle );
	}
	@Override
	public void setTitle(CharSequence cs)
	{
		titleTextView.setText(cs);
	}
	public void setTitleColor(int color)
	{
		titleTextView.setTextColor(color);
	}
	public void setNavBarGradient(int colorStart,int colorEnd)
	{
		 GradientDrawable gd = new GradientDrawable(
		            GradientDrawable.Orientation.TOP_BOTTOM,
		            new int[] {colorStart,colorEnd});
		 //View v=((View)findViewById(R.id.ImageViewTitle_BG).getParent());
		 paretTitleView.setBackgroundDrawable(gd);
	}
	public void setNavBarImage(String imageSRC)
	{
	//	Drawable dr=new BitmapDrawable.
		//findViewById(R.id.RelativeLayoutTitleBar).setBackgroundDrawable(gd);
		
	}
	public void setProgressBarCustomTitleVisibility(boolean flag)
	{
		if(flag)
		{
			progressBarCustomTitle .setVisibility(View.VISIBLE);
		}else
			progressBarCustomTitle .setVisibility(View.INVISIBLE);
	}
	
	
}
