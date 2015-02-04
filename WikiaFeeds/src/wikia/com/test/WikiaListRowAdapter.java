package wikia.com.test;

import java.util.ArrayList;

import wikia.com.storage.WikiaFeedPojo;
import wikia.com.util.ImageLoader;
import wikia.com.util.WikiaUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WikiaListRowAdapter extends BaseAdapter {
	
	    private MainActivity mContext;
	   // private ArrayList<WikiaFeedPojo> data;
	    private ArrayList<WikiaFeedPojo> data;
	    private ProgressDialog pDialoge;
	    public ImageLoader imageManager;
	    LayoutInflater inflater;
	
	public  WikiaListRowAdapter(MainActivity ctx, ArrayList<WikiaFeedPojo> data) {
		// TODO Auto-generated constructor stub
		
		this.mContext= ctx;
		this.data=data;
		imageManager = new ImageLoader(ctx.getApplicationContext());
		 inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
	
		  View vi=convertView;
	        ViewHolder holder;
	         
	        if(convertView==null){
	             
	            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
	            vi = inflater.inflate(R.layout.feedlistrow, null);
	             
	            /****** View Holder Object to contain tabitem.xml file elements ******/

	            holder = new ViewHolder();
	            holder.txt_title = (TextView) vi.findViewById(R.id.text_title);
	            holder.txt_url=(TextView)vi.findViewById(R.id.text_url);
	            holder.img_wikiimg=(ImageView)vi.findViewById(R.id.wikiimage);
	             
	           /************  Set holder with LayoutInflater ************/
	            vi.setTag( holder );
	        }
	        else 
	            holder=(ViewHolder)vi.getTag();
	        
	        
	        holder.txt_title.setText(data.get(position).getItemTitle());
	        holder.txt_url.setText(data.get(position).getItemUrl());
	        ImageView image = holder.img_wikiimg;
	        
	        //DisplayImage function from ImageLoader Class
	        imageManager.DisplayImage(data.get(position).getItemWordMark(), image);
	        
	        
	        vi.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					WikiaFeedPojo wfp=data.get(position);
					
					Intent intent= new Intent(mContext,WikiDescription.class);
					intent.putExtra("wikia", wfp);
					mContext.startActivity(intent);
					
				}
			});
	        
	        /******** Set Item Click Listner for LayoutInflater for each row ***********/
	       // vi.setOnClickListener(new OnItemClickListener(position));
	        return vi;


       // return convertView;
	}
	
	
	
	 public class ViewHolder {
	        TextView txt_title;
	        TextView txt_url;
	        ImageView img_wikiimg;
	    }

}
