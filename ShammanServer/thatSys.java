public class thatSys implements java.io.Serializable {
	/**
	 *  Local object to be saved
	 */
	private static final long serialVersionUID = 1L;
	public static final boolean DEBUG = true;
	
// Identification details
	public static double version = 1.0;
	public String UUID = "";
	
//Jobs
	public static String [] globalJob= new String[3];
	public static String [] myJobs= new String[3];
	
// Status values
	public static String status= "Working";
	public static boolean isOnline = false;
	public static int errorLevel= 0;
	
	public static int timeToRefresh= 3000;
	
}