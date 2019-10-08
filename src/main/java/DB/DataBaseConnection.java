package DB;

import Util.Util;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import oracle.jdbc.pool.OracleDataSource;

        


	public class DataBaseConnection {
		 static String jdbcUrl = null;
	 	 String userid = null;
	 	 String password = null; 
	 	 static Connection conn; 
	 	   
	 	 public static Connection getConnection() throws SQLException, IOException  {
                    String parDatabase = "XE"; //Util.Read("Config.ini", "DATABASE");
                    String parDBUser = Util.Read("Config.ini", "DB_USER");
                    String parDBPass = Util.Read("Config.ini", "DB_PASS");

                    System.out.println("Conectando ao Banco de Dados");

   //                  OracleDataSource ds = null;
     //               ds = new OracleDataSource();
                    String jdbcUrl = "jdbc:oracle:thin:@HouserverWin";
                    
       //              ds.setURL(jdbcUrl);
                    
          //          conn = ds.getConnection(parDBUser, parDBPass);
                    //conn = ds.getConnection("production", "production");
                    
                    return conn;
             	}
                 
                 
                 public static Connection getConnectionMySQL() throws SQLException, IOException, ClassNotFoundException {

                //String JDBC_DRIVER = "com.mysql.jdbc.Driver";
                
                String JDBC_DRIVER = "com.mysql.jdbc.Driver";
                String DB_URL = "jdbc:mysql://localhost/" + Util.Read("Config.ini", "DB_USER").toLowerCase() + "?autoReconnect=true" ;
                //String DB_URL = "jdbc:mysql://localhost/" + Util.Read("Config.ini", "DB_USER").toLowerCase();
                
                System.out.println("DB URL é: " + DB_URL );
                
                String USER = "root";
                String PASS = "root";
                
                Connection conn = null;
                Statement stmt = null;

                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    System.out.println("Connecting to database...");
                    
                    conn = DriverManager.getConnection(DB_URL, USER, PASS);

                } catch (ClassNotFoundException | SQLException  ex) {
                       System.out.println (ex.getMessage() );
                }

                return conn;
            }
	}

