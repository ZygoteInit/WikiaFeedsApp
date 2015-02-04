package wikia.com.test;

import wikia.com.util.WikiaUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WikiaSplashScreen extends Activity {
	
	 private static int SPLASH_TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splashscreen);
		
		
		
		 new Handler().postDelayed(new Runnable() {
			 
	            @Override
	            public void run() {
	                // This method will be executed once the timer is over
	                // Start your app main activity
	            	new AlertDialog.Builder(WikiaSplashScreen.this)
	                .setTitle("I've got that Handled!")
	                .setMessage("Hey mate! your wikia feeds will be saved locally for future offline use")
	                .setPositiveButton(android.R.string.yes,new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                    	 Intent i = new Intent(WikiaSplashScreen.this, MainActivity.class);
	     	                startActivity(i);
	     	                // close this activity
	     	                finish();
	                    }
	                }).show();
	               
	            }
	        }, SPLASH_TIME_OUT);
	}


}
