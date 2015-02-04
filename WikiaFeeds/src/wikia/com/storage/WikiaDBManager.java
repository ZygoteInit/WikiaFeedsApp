package wikia.com.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WikiaDBManager extends SQLiteOpenHelper {
	
	  private static final String LOG = "DatabaseHelper";

      // Database Version
      private static final int DATABASE_VERSION = 1;

      // Database Name
      private static final String DATABASE_NAME = "WikiaApp";

      // Table Names
      public static final String TABLE_WIKIA = "WikiaFeeds";
      
      
      //Table Columns
    public static String ID="Id";
  	public static String ITEMID="itemid";
  	public static String ITEMNAME="name";
  	public static String ITEMTITLE="title";
  	public static String ITEMHUB="hub";
  	public static String ITEMLANGUAGE="language";
  	public static String ITEMTOPIC="topic";
  	public static String ITEMDOMAIN="domain";
  	public static String ITEMDETAILWORDMARK="wordmark";
  	public static String ITEMDETAILURL="url";
  	public static String ITEMSAVEDLOCALLY="isSavedLocally";
  	public static String ITEMACTIVEUSERS="activeusers";
  	public static String ITEMUSERS="users";
  	public static String ITEMPAGES="pages";
  	public static String ITEMVIDEOS="videos";
  	public static String ITEMIMAGES="images";
  	public static String ITEMEDITS="edits";
  	public static String ITEMARTICLES="articles";
  	public static String ITEMDESC="desc";
  	
  

    private static final String CREATE_TABLE_WIKIAITEMS = "CREATE TABLE "
    + TABLE_WIKIA + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ITEMID  + " INTEGER not null unique," + ITEMNAME + " TEXT," + ITEMHUB+ " TEXT," + ITEMLANGUAGE
    + " Text," + ITEMTOPIC + " Text,"+ ITEMDOMAIN+ " TEXT,"+ ITEMDETAILWORDMARK + " TEXT,"+ ITEMDETAILURL  + " TEXT,"+ ITEMACTIVEUSERS  + " TEXT,"+
    ITEMUSERS  + " TEXT,"+ ITEMIMAGES  + " TEXT,"+ ITEMVIDEOS  + " TEXT,"+ ITEMEDITS  + " TEXT,"+ ITEMARTICLES  + " TEXT,"+ ITEMPAGES  + " TEXT,"+
    ITEMDESC  + " TEXT,"+ITEMTITLE  + " TEXT,"+ITEMSAVEDLOCALLY  + " INTEGER"+ ")";

      

	public WikiaDBManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		 db.execSQL(CREATE_TABLE_WIKIAITEMS);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIKIA);
         onCreate(db);
	}

}
