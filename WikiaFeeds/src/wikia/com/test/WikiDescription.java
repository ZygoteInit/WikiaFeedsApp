package wikia.com.test;

import java.util.ArrayList;

import wikia.com.storage.WikiaFeedPojo;
import wikia.com.util.ImageLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class WikiDescription extends Activity {
	
	ImageView image;
	TextView txtusers,txtactiveusers,txtpages,txtvideos,txtimages,txtedits,txtarticles,txtdesc;
	WikiaFeedPojo wikiafeed;
	Intent intent;
	WikiaFeedPojo wfp;
	ImageLoader imgmgr;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wikidesclayout);
		
		
		//finding controls
		imgmgr = new ImageLoader(this.getApplicationContext());
		image=(ImageView)findViewById(R.id.imgwiki);
		txtusers=(TextView)findViewById(R.id.txtusers);
		txtactiveusers=(TextView)findViewById(R.id.txtactiveusers);
		txtarticles=(TextView)findViewById(R.id.txtarticles);
		txtedits=(TextView)findViewById(R.id.txtedits);
		txtpages=(TextView)findViewById(R.id.txtpages);
		txtimages=(TextView)findViewById(R.id.txtimages);
		//txtvideos=(TextView)findViewById(R.id.txtvideos);
		txtdesc=(TextView)findViewById(R.id.txtdesc);
		//finding controls
		
	    intent= getIntent();
	    
	    wfp=(WikiaFeedPojo) intent.getSerializableExtra("wikia");
		
	    if(wfp!=null)
	    {
	    	
	    setTitle(wfp.getItemTitle());
		txtusers.setText(wfp.getItemUsers());
		txtactiveusers.setText(wfp.getItemActiveUsers());
		txtarticles.setText(wfp.getItemArticles());
		txtedits.setText(wfp.getItemEdits());
		txtimages.setText(wfp.getItemImages());
		txtpages.setText(wfp.getItemPages());
		//txtvideos.setText(wfp.getItemVideos());
		if(wfp.getItemDescription()==null || wfp.getItemDescription().length()==0)
		{
			txtdesc.setText(R.string.dummy);
		}
		else
		{
			txtdesc.setText(wfp.getItemDescription());
		}
		txtdesc.setMovementMethod(new ScrollingMovementMethod());
		imgmgr.DisplayImage(wfp.getItemWordMark(), image);
		
	    }
		
		
		
		
	}

}
