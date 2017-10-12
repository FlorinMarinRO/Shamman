// SQL DRIVER Library 
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**********Database manager ************/
public class dbManager {
	
	
// MySQL Server address and parameters
	final String DB_ADDRESS= "localhost";
	final String DB_PORT= "3306";	
	final String DB_NAME= "shamman";
	final String DB_USER= "root";
	final String DB_PASSWORD= "";
	String url= "jdbc:mysql://"+ DB_ADDRESS +":"+ DB_PORT +"/"+ DB_NAME +"?autoReconnect=false&useSSL=false";
	
	
	/********** DB Work method
	interogationType => false = ask from db; true = add / modify db
	SQL = SQL Statement
	*********/
	String bdInterogate(Boolean interogationType, String SQL, String look4){
		
		try {
			Connection connection = (Connection) DriverManager.getConnection(url, DB_USER, DB_PASSWORD); // Connect to DB
		    
		// Execute connection statement
			Statement st= null;
			try{
				st=  (Statement) connection.createStatement();
			}catch (SQLException e) { return "NO_CON";  }
		    
			if(interogationType){ 
		    //GET Data
		    	ResultSet rs = st.executeQuery( SQL );
		    	
		    	
		    	String record= "";
		    	while(rs.next()) record = rs.getString( look4 ); 
		    	
		    	st.close(); //Close connection to db
		    	return record;
		    }
		    
		    else {
		    // Add to DB
		    	st.executeUpdate( SQL );
		    	st.close(); //Close connection to db
		    	return "OK";
		    }
		    
		} catch (SQLException e) { return "BAD"; }
	}
	/********** DB Work method END ********/
}
