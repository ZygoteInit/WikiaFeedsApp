package wikia.com.util;

public class WikiaUrls {
	
	public static String BaseUrl="http://www.wikia.com/wikia.php?controller=WikisApi&method=";
	
	public static String HUB="&hub=gaming";
	
	public static String LANGUAGE="&language=en";
	
	public static String BATCH="&batch=";
	
	public static String IDS="&ids=";
	
	public static String WikiGetList=BaseUrl+"getList"+HUB+LANGUAGE+BATCH;
	
	public static String WikiaGetDetail=BaseUrl+"getDetails"+IDS;

}
