package wikia.com.storage;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WikiaDBHandler {
	
	 private SQLiteDatabase database;
	 private WikiaDBManager db;
	    
	    
	 //Constructor and Open/Close methods begins
	   public WikiaDBHandler(Context context){
	        db= new WikiaDBManager(context);
	    }

	    public void open() throws SQLException {
	        database = db.getWritableDatabase();
	    }

	    public void close() {
	        db.close();
	    }
	  //Constructor and Open/Close methods ends

	 
	    
	    public ArrayList<WikiaFeedPojo>  GetAllStoredFeeds()
	    {

	        open();
	        
	        ArrayList<WikiaFeedPojo> feed_data = new ArrayList<WikiaFeedPojo>();
	        String selectQuery = "SELECT  * FROM " + WikiaDBManager.TABLE_WIKIA + " ORDER BY " + WikiaDBManager.ID + " ASC";
	            Cursor c = database.rawQuery(selectQuery, null);

	            if (c != null) {

	                for (int i = 0; i < c.getCount(); i++) {
	                    c.moveToPosition(i);

	                    WikiaFeedPojo feeds = new WikiaFeedPojo();
	                    feeds.setItemID(c.getString(c.getColumnIndex(WikiaDBManager.ITEMID)));
	                    feeds.setItemTitle(c.getString(c.getColumnIndex(WikiaDBManager.ITEMTITLE)));
	                    feeds.setItemName(c.getString(c.getColumnIndex(WikiaDBManager.ITEMNAME)));
	                    feeds.setItemLanguage(c.getString(c.getColumnIndex(WikiaDBManager.ITEMLANGUAGE)));
	                    feeds.setItemTopic(c.getString(c.getColumnIndex(WikiaDBManager.ITEMTOPIC)));
	                    feeds.setItemDomain(c.getString(c.getColumnIndex(WikiaDBManager.ITEMDOMAIN)));
	                    feeds.setItemUrl(c.getString(c.getColumnIndex(WikiaDBManager.ITEMDETAILURL)));
	                    feeds.setItemWordMark(c.getString(c.getColumnIndex(WikiaDBManager.ITEMDETAILWORDMARK)));
	                    feeds.setItemHub(c.getString(c.getColumnIndex(WikiaDBManager.ITEMHUB)));
	                    feeds.setItemDescription(c.getString(c.getColumnIndex(WikiaDBManager.ITEMDESC)));
	                    feeds.setItemActiveUsers(c.getString(c.getColumnIndex(WikiaDBManager.ITEMACTIVEUSERS)));
	                    feeds.setItemUsers(c.getString(c.getColumnIndex(WikiaDBManager.ITEMUSERS)));
	                    feeds.setItemImages(c.getString(c.getColumnIndex(WikiaDBManager.ITEMIMAGES)));
	                    feeds.setItemVideos(c.getString(c.getColumnIndex(WikiaDBManager.ITEMVIDEOS)));
	                    feeds.setItemPages(c.getString(c.getColumnIndex(WikiaDBManager.ITEMPAGES)));
	                    feeds.setItemEdits(c.getString(c.getColumnIndex(WikiaDBManager.ITEMEDITS)));
	                    feeds.setItemArticles(c.getString(c.getColumnIndex(WikiaDBManager.ITEMARTICLES)));
	                    

	                    feed_data.add(feeds);
	                }

	            }
	            close();

	            return feed_data;



	    }
	    
	    
	    public ArrayList<WikiaFeedPojo>  InsertWikiFeeds( ArrayList<WikiaFeedPojo> feed_data)
	    {

	        open();
	        
	        //ArrayList<WikiaFeedPojo> feed_data = new ArrayList<WikiaFeedPojo>();
	       // String selectQuery = "SELECT  * FROM " + WikiaDBManager.TABLE_WIKIA + " ORDER BY " + WikiaDBManager.ID + " ASC";
	            //Cursor c = database.rawQuery(selectQuery, null);

	            if (feed_data != null && feed_data.size()>0) {

	                for (int i = 0; i < feed_data.size(); i++) {
	                   

	                	ContentValues cv= new ContentValues();
	                	
	                    cv.put(WikiaDBManager.ITEMID, feed_data.get(i).getItemID() );
	                    cv.put(WikiaDBManager.ITEMNAME, feed_data.get(i).getItemName() );
	                    cv.put(WikiaDBManager.ITEMLANGUAGE, feed_data.get(i).getItemLanguage() );
	                    cv.put(WikiaDBManager.ITEMTOPIC, feed_data.get(i).getItemTopic() );
	                    cv.put(WikiaDBManager.ITEMDOMAIN, feed_data.get(i).getItemDomain() );
	                    cv.put(WikiaDBManager.ITEMDETAILURL, feed_data.get(i).getItemUrl() );
	                    cv.put(WikiaDBManager.ITEMDETAILWORDMARK, feed_data.get(i).getItemWordMark() );
	                    cv.put(WikiaDBManager.ITEMHUB, feed_data.get(i).getItemHub() );
	                    cv.put(WikiaDBManager.ITEMDESC, feed_data.get(i).getItemDescription() );
	                    cv.put(WikiaDBManager.ITEMACTIVEUSERS, feed_data.get(i).getItemActiveUsers() );
	                    cv.put(WikiaDBManager.ITEMUSERS, feed_data.get(i).getItemUsers() );
	                    cv.put(WikiaDBManager.ITEMIMAGES, feed_data.get(i).getItemImages() );
	                    cv.put(WikiaDBManager.ITEMVIDEOS, feed_data.get(i).getItemVideos() );
	                    cv.put(WikiaDBManager.ITEMPAGES, feed_data.get(i).getItemPages() );
	                    cv.put(WikiaDBManager.ITEMEDITS, feed_data.get(i).getItemEdits() );
	                    cv.put(WikiaDBManager.ITEMARTICLES, feed_data.get(i).getItemArticles() );
	                    cv.put(WikiaDBManager.ITEMSAVEDLOCALLY, 1);
	                   
	                    database.insert(WikiaDBManager.TABLE_WIKIA, null, cv);

	                    
	                }

	            }
	            close();

	            return feed_data;



	    }

}
