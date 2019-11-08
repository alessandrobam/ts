package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public abstract class GenericDao {
 
	final static int typeTS = 1;
	final static int typeDATE = 0;
	final static int typeNUMBER = 2;
	final static int typeVARCHAR = 3;
	final static int typeINT = 4;
	final static int typeLONG = 5;
        
	private Connection conn;
    
    protected GenericDao(Connection db)  {
      this.conn = db;
    }
 
    protected Connection getConnection() {
          
                    return conn;
          
    }
 
    protected void insert(String insertSql, int[] DataTypes, Object... parametros) throws SQLException {
    	PreparedStatement pstmt = getConnection().prepareStatement(insertSql);
        for (int i = 0; i < parametros.length; i++) {
        	switch (DataTypes[i]) {
      	     case typeINT:    pstmt.setInt(i+1, (int) parametros[i]); break;
      	     case typeTS:   pstmt.setTimestamp(i+1, (Timestamp)parametros[i]); break;
      	     case typeDATE:   pstmt.setDate(i+1, (java.sql.Date)parametros[i]); break;
      	     case typeNUMBER: pstmt.setFloat(i+1, (Float)parametros[i]); break;
         	 case typeVARCHAR:pstmt.setString(i+1, (String)parametros[i]); break;
         	 case typeLONG   :pstmt.setLong(i+1, (Long)parametros[i]); break; 
      	  }
        	pstmt.setObject(i+1,parametros[i]);
        }
 
        System.out.println("insert statemete: " + pstmt.toString());
    pstmt.execute();
    
    pstmt.close();
    }
 
    
    protected void insertNew(String insertSql, Object... parametros) throws SQLException {
    	PreparedStatement pstmt = getConnection().prepareStatement(insertSql);
        for (int i = 0; i < parametros.length; i++) {
            pstmt.setObject(i+1,parametros[i]);
        }
 
        System.out.println("insert statemete: " + pstmt.toString());
    pstmt.execute();
    
    pstmt.close();
    }
 
    
    
    protected int getCount (String select_record, Object... parametros) throws SQLException {
    
        int retorno = 0;
        PreparedStatement pstmt = getConnection().prepareStatement(select_record);
        for (int i = 0; i < parametros.length; i++) {
        pstmt.setObject(i+1, parametros[i]);
        }
        ResultSet rs = pstmt.executeQuery();

        int a = 1;
        while (rs.next()) {
           retorno = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
    	return retorno;
    } 
    
    
    protected dbresult getallrecords(String Stmt) throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(Stmt);
    	
        //Statement stmt = conn.createStatement();
    	ResultSet rs = pstmt.executeQuery(Stmt);
	dbresult dbs = new dbresult(rs);
        
        dbs.stm = pstmt;
                
        //pstmt.close();
        return dbs;
	}

        
    protected dbresult getrecord(String select_record, Object... parametros) throws SQLException {
    	
    	PreparedStatement pstmt = getConnection().prepareStatement(select_record);
        for (int i = 0; i < parametros.length; i++) {
        pstmt.setObject(i+1, parametros[i]);
        }
        ResultSet rs = pstmt.executeQuery();
        
        dbresult dbs = new dbresult(rs);
        //dbs.rs = rs;
        
        System.out.println(pstmt.toString());
               
        dbs.stm = pstmt;
        
        
        //pstmt.close(); 
        
        return dbs;
	}
    
    protected void updateNew(String updateSql, Object... parametros) throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(updateSql);
        for (int i = 0; i < parametros.length; i++) {
        	  //System.out.println("PARAMETRO " + i + "  " + parametros[i].toString());
              pstmt.setObject(i+1, parametros[i]);
        }
        //pstmt.setObject(parametros.length + 1, id);
        System.out.println ("linha atualizadas " + pstmt.executeUpdate() + updateSql);
        
        pstmt.close();
        }
     
    protected void updatetype(String updateSql, int[] DataTypes, Object... parametros) throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(updateSql);
        for (int i = 0; i < parametros.length; i++) {
        	  switch (DataTypes[i]) {
        	    case typeINT:    pstmt.setInt(i+1, (int) parametros[i]); break;
        	    case typeTS:     pstmt.setTimestamp(i+1, (Timestamp)parametros[i]); break;
        	    case typeDATE:   pstmt.setDate(i+1, (java.sql.Date)parametros[i]); break;
        	    case typeNUMBER: pstmt.setFloat(i+1, (Float)parametros[i]); break;
           	    case typeVARCHAR:pstmt.setString(i+1, (String)parametros[i]); break;
           	    case typeLONG   :pstmt.setLong(i+1, (Long)parametros[i]); break; 
        	  }
        }
        //pstmt.setObject(parametros.length + 1, id);
        pstmt.execute();
        pstmt.close();
        }
     
    
    protected void delete(String deleteSql, Object... parametros) throws SQLException {
    PreparedStatement pstmt = getConnection().prepareStatement(deleteSql);
    for (int i = 0; i < parametros.length; i++) {
                pstmt.setObject(i+1, parametros[i]);
    }
     pstmt.execute();
     pstmt.close();
    }
    
    protected int getnextid(String stmt, Object... parametros) throws SQLException  {
    	PreparedStatement pstmt = getConnection().prepareStatement(stmt);
    	 for (int i = 0; i < parametros.length; i++) {
             pstmt.setObject(i+1, parametros[i]);
 }
        int a = 1;
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
           a = rs.getInt(1);
        }
        rs.close();
        pstmt.close();
        
    	return a;
        }
    
    protected int getid(String stmt,Object... parametros) throws SQLException  {
    	PreparedStatement pstmt = getConnection().prepareStatement(stmt);
    	for (int i = 0; i < parametros.length; i++) {
            pstmt.setObject(i+1, parametros[i]);
         }
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
           return rs.getInt(1);
        }
    	return 0;
        }
    
    public int getRows(ResultSet res){
        int totalRows = 0;
        try   {
              res.last();
              totalRows = res.getRow();
              res.beforeFirst();
               } catch(Exception ex)  {
                 System.out.println(ex.getMessage());
            	  return 0;
                 
               }
              return totalRows ;    
    }
    
    public class dbresult
    {

        public dbresult(ResultSet rs) {
            this.rs = rs;
        }
         ResultSet rs;
        PreparedStatement stm;

        public void setRs(ResultSet rs) {
            this.rs = rs;
        }

        
        public ResultSet getRs() {
            return rs;
        }

        public PreparedStatement getStm() {
            return stm;
        }
    }
}