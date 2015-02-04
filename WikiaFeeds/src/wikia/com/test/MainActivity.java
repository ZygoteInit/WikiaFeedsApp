package wikia.com.test;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import wikia.com.storage.WikiaDBHandler;
import wikia.com.storage.WikiaFeedPojo;
import wikia.com.util.JSONTags;
import wikia.com.util.ServiceHandler;
import wikia.com.util.WikiaUrls;
import wikia.com.util.WikiaUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	//Declaring Variables
	ListView feedslist;
	WikiaListRowAdapter dataAdapter;
	ProgressDialog pDialog;
	int Batch=0;
	ArrayList<Integer> FeedsIdList;
	ArrayList<WikiaFeedPojo> feedsArrayList,feedsforDB;
	int TotalBatches;
	int PageSize=25,NextItemCount=0;
	Boolean loadfirst=false;
	WikiaDBHandler wikiadbHandler;
	//Declaring Variables
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		feedsArrayList=new ArrayList<WikiaFeedPojo>();
		wikiadbHandler= new WikiaDBHandler(MainActivity.this);
		//Finding controls
		feedslist=(ListView)findViewById(R.id.feedslist);
		//Finding controls
		
		 dataAdapter= new WikiaListRowAdapter(MainActivity.this,MainActivity.this.feedsArrayList);
   	     feedslist.setAdapter(dataAdapter);
		 feedslist.setOnScrollListener(new OnScrollListener() {
			
			 int currentFirstVisibleItem;
			 int currentVisibleItemCount;
			 int currentScrollState;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				 //new FetchFeeds().execute();
				 this.currentScrollState = scrollState;
				    this.isScrollCompleted();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				this.currentFirstVisibleItem = firstVisibleItem;
			    this.currentVisibleItemCount = visibleItemCount;
			  
			}
			
			private void isScrollCompleted() {
			    if (this.currentVisibleItemCount > 0 &&  this.currentFirstVisibleItem+this.currentVisibleItemCount == PageSize  && this.currentScrollState == SCROLL_STATE_IDLE) {
			        /*** In this way I detect if there's been a scroll which has completed ***/
			        /*** do the work! ***/
			    	if(MainActivity.this.TotalBatches>MainActivity.this.Batch)
			    	{
			    	ExecuteAsyncTask();
			    	PageSize+=NextItemCount;
			    	} 	
			    	
			    }
			}
		});
		
		 ExecuteAsyncTask();	
		
	}
	
	 @Override
	    public void onBackPressed()
	    {
	        new AlertDialog.Builder(this)
	                .setTitle("Confirmation")
	                .setMessage("Are you sure you want to quit the application?")
	                .setNegativeButton(android.R.string.no, null)
	                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

	                    public void onClick(DialogInterface arg0, int arg1) {
	                        MainActivity.this.finish();
	                    }
	                }).create().show();
	    }
	
	
	public void ExecuteAsyncTask()
	{
		if(WikiaUtil.isNetworkAvailable(MainActivity.this))
		new FetchFeeds().execute();
		else
		{
			WikiaUtil.ShowDialogueAndStartActivity(MainActivity.this, "Damn!", "Ah! Crap where is my wifi?! Well let me see what i've saved locally");
			// MainActivity.this.feedsArrayList=new ArrayList<WikiaFeedPojo>();
			MainActivity.this.feedsArrayList.addAll(wikiadbHandler.GetAllStoredFeeds());
			 //.this.feedsArrayList=wikiadbHandler.GetAllStoredFeeds();
			// MainActivity.this.dataAdapter= new WikiaListRowAdapter(MainActivity.this, MainActivity.this.feedsArrayList);
			 UpdateAdapter();
		}
	}
	
	
	private class FetchFeeds extends AsyncTask<Void,Void,Void>
    {
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setIcon(R.drawable.ic_launcher);
            pDialog.setMessage("Fetching Feeds...");
            pDialog.setCancelable(false);
            if(!loadfirst)
            pDialog.show();

        }
        
        @Override
        protected Void doInBackground(Void... params) {

        	FetchFeedsAndDetails();
            return null; 
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            loadfirst=true;
            UpdateAdapter();
            SaveWikisOffline(MainActivity.this.feedsforDB);   
            //ends
        }
    }
	
	//Updates the adapter that is notifies the listview's adapter for data changes
	public void UpdateAdapter()
	{
		if(MainActivity.this.dataAdapter!=null && MainActivity.this.feedsArrayList!=null && MainActivity.this.feedsArrayList.size()>0 && !(MainActivity.this.Batch>10))
        {
        	MainActivity.this.dataAdapter.notifyDataSetChanged();   	
        }
	}
	//ends
	
	//Saves wikis to sqlite db for further offline use
	public void SaveWikisOffline(ArrayList<WikiaFeedPojo> feeds)
	{
		if(feeds!=null && feeds.size()>0)
		MainActivity.this.wikiadbHandler.InsertWikiFeeds(feeds);
	}
	//ends
	
	public void FetchFeedsAndDetails()
	{
		
		MainActivity.this.Batch++;
		//MainActivity.this.feedsArrayList= new ArrayList<WikiaFeedPojo>();
		ServiceHandler sv= new ServiceHandler();
		//Getting the feed Ids
        String jsonStr = sv.GetList(WikiaUrls.WikiGetList+MainActivity.this.Batch, ServiceHandler.GET);
        
        if (jsonStr != null) {
            try {

            	JSONObject feedsObjects= new JSONObject(jsonStr);
            	//Items and Page size parsing
            	MainActivity.this.TotalBatches=Integer.parseInt(feedsObjects.getString(JSONTags.TAG_ITEM_BATCHES));
            	MainActivity.this.NextItemCount=Integer.parseInt(feedsObjects.getString(JSONTags.TAG_ITEM_NEXTITEMCOUNT));
            	
            	//Items and Page Size parsing ends
            	
            	if(feedsObjects!=null && feedsObjects.length()>0)
            	{
            		//JsonArray itemsArray= feedsArray.get
            		JSONArray feedsArray =feedsObjects.getJSONArray(JSONTags.TAG_ITEMTAG);
            		
            		if(feedsArray!=null && feedsArray.length()>0)
            		{
            			//Adding the ids to arraylist for further detail fetch http call
            			MainActivity.this.FeedsIdList= new ArrayList<Integer>();
            		for(int i=0;i<feedsArray.length();i++)
            		{
            			JSONObject tempObj= feedsArray.getJSONObject(i);
            			MainActivity.this.FeedsIdList.add(Integer.parseInt(tempObj.getString(JSONTags.TAG_ITEMID)));
            			
            		}
            		// id's adding end
            		
            		}
            	}
            
            }
            catch (Exception ex) {
                Log.d("Exception on Token call:",ex.getMessage() );
            }
            
            //Now we will fetch the Feed details by comma seperating the ArrayList<int>
            if(MainActivity.this.FeedsIdList!=null && MainActivity.this.FeedsIdList.size()>0)
            {
            	 String jsonStrDetail = sv.GetList(WikiaUrls.WikiaGetDetail+TextUtils.join(",", MainActivity.this.FeedsIdList), ServiceHandler.GET);
            	MainActivity.this.feedsforDB= new ArrayList<WikiaFeedPojo>();
            	 if(jsonStrDetail!=null)
            	 {
            		 try {

                     	JSONObject feedsObjects= new JSONObject(jsonStrDetail);
                     	
                     	if(feedsObjects!=null && feedsObjects.length()>0)
                     	{
                     		//JsonArray itemsArray= feedsArray.get
                     		JSONObject feedsIdDetailObj =feedsObjects.getJSONObject(JSONTags.TAG_ITEMTAG);
                     		
                     		if(feedsIdDetailObj!=null && feedsIdDetailObj.length()>0)
                     		{
                     			//JSONArray feedsIdArray = feedsIdDetailObj.getJSONArray(name)
                     			//MainActivity.this.feedsArrayList= new ArrayList<WikiaFeedPojo>();
                     		for(int i=0;i<feedsIdDetailObj.length();i++)
                     		{
                     			JSONObject tempObj= feedsIdDetailObj.getJSONObject(MainActivity.this.FeedsIdList.get(i)+"");
                     			//MainActivity.this.FeedsIdList.add(Integer.parseInt(tempObj.getString(JSONTags.TAG_ITEMID)));
                     			
                     			JSONObject statsObject=tempObj.getJSONObject(JSONTags.TAG_ITEMDETAISTATS);
                     			
                     			WikiaFeedPojo pojo= new WikiaFeedPojo();
                     			pojo.setItemUrl(tempObj.getString(JSONTags.TAG_ITEMDETAILURL));
                     			pojo.setItemTitle(tempObj.getString(JSONTags.TAG_ITEMTITLE));
                     			pojo.setItemWordMark(tempObj.getString(JSONTags.TAG_ITEMDETAILWORDMARK));
                     			pojo.setItemDescription(tempObj.getString(JSONTags.TAG_ITEMDESC));
                     			pojo.setItemID(tempObj.getString(JSONTags.TAG_ITEMID));
                     			
                     			//set Wiki Stats for Desc activity
                     			pojo.setItemEdits(statsObject.getString(JSONTags.TAG_ITEMDETAILSTATSEDIT));
                     			pojo.setItemUsers(statsObject.getString(JSONTags.TAG_ITEMDETAILUSERS));
                     			pojo.setItemArticles(statsObject.getString(JSONTags.TAG_ITEMDETAILARTICLES));
                     			pojo.setItemImages(statsObject.getString(JSONTags.TAG_ITEMDETAILIMAGES));
                     			pojo.setItemVideos(statsObject.getString(JSONTags.TAG_ITEMDETAILVIDEOS));
                     			pojo.setItemPages(statsObject.getString(JSONTags.TAG_ITEMDETAILPAGES));
                     			pojo.setItemActiveUsers(statsObject.getString(JSONTags.TAG_ITEMDETAILACTIVEUSERS));
                     			//set wiki stats for Desc activity
                     			
                     			//To avoid duplicate insertion in db and iterating over listview's feedsArrayList 
                     			//i'm creating a seperate arrlist to hold 
                     			//only new items to be inserted
                     			MainActivity.this.feedsforDB.add(pojo);
                     			//ends
                     			
                     			//This list holds all the data for Listview's adapter
                     			MainActivity.this.feedsArrayList.add(pojo);
                     		}
                     		
                     		}
                     	}
                     
                     }
            		 catch (Exception ex) {
                         Log.d("Exception on Token call:",ex.getMessage() );
                     }
            	 }
            }
            //ends
        }
        else
        {
            
        }
        
		}

	

	
}
