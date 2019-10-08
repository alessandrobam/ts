/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import Util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 *
 * @author Alessandro
 */
public class waitDao extends GenericDao {
    private final String NEXTID  = "SELECT max(id)+1 FROM WAITS";
    private final String OPENWAIT = "SELECT id FROM WAITS where masterid=? and taskid=? and finished is null";
    private final String ADDWORKSESSION  = "INSERT INTO WAITS(id,masterid,taskid,started,finished,minutes) VALUES (?,?,?,?,?,?)";
    private final String STARTWAIT  = "INSERT INTO WAITS(id,masterid, taskid,started,finished,minutes) VALUES (?,?,?,?,?,?)";
    private final String FINISHWAIT = "UPDATE waits set finished=?, minutes=extract(minute from (?-started)) where id=?";
    private final String UPDATETOTAL = "UPDATE waits set total=round((finished - started)*24*60,0) where id=?";
    private final String UPDATEWORKSESSION = "UPDATE waits set started=?, finished=?, minutes=? where id=?";
    
    
    private final String SOMAWAITS = "select COALESCE (sum(minutes),0) from waits where masterid=? and taskid=?";
    private final String DELETE = "delete from waits where id=?";
    private final String DELETEWAITS_FOR_MASTER = "delete from waits where masterid=?";
    private final String DELETEWAITS_FOR_TASK = "delete from waits where masterid=? and taskid=?";

    public waitDao(Connection conn) {
       super(conn);
    }

   private int getnextwaitid() throws SQLException{
		return super.getnextid(NEXTID);
	}

	public long getSumWait(int pMID, int pTID) throws SQLException{
		long retorno;
                dbresult dbs;
                dbs = super.getrecord(SOMAWAITS, pMID, pTID);
		while (dbs.rs.next()) {
	           retorno = dbs.rs.getLong(1);
               dbs.rs.close();
               dbs.stm.close();
               return retorno;    
        }
	return 0;		
				
	}

        public void delete (int pID) throws SQLException {
		super.delete(DELETE, pID );
	}
	
	public void deleteWaitsforMaster (int pMID) throws SQLException {
		super.delete(DELETEWAITS_FOR_MASTER, pMID);
	}
	
        public void deleteWaitsforTask (int pMID, int pID) throws SQLException {
		super.delete(DELETEWAITS_FOR_TASK, pMID, pID );
	}
	
        
        public void updateWorkSession (int id, Timestamp ts_start, Timestamp ts_finish) throws SQLException {
            
             
               //Timestamp ts = new Timestamp();
               super.updateNew(UPDATEWORKSESSION, ts_start, ts_finish,Util.diffInMinutes(ts_start, ts_finish), id );
               
     //"UPDATE waits set started=?, finished=?, minutes=extract(minute from (finish-started)) where id=?";
	}
	
	
	private String getopenwait(int pMID, int pTID) throws SQLException{
                dbresult dbs;
                String retorno;
                dbs =  super.getrecord(OPENWAIT, pMID, pTID );
		while (dbs.rs.next()) {
	           retorno = String.valueOf(dbs.rs.getInt(1));
                dbs.getRs().close();
                dbs.getStm().close();
                return retorno;
        }
		return "0";
	}
        
        
	
	protected void update(String updateSql, Object... parametros) throws SQLException {
        try ( //   PreparedStatement pstmt = getConnection().prepareStatement(updateSql);
        /* for (int i = 0; i < parametros.length; i++) {
        //System.out.println("PARAMETRO " + i + "  " + parametros[i].toString());
        pstmt.setObject(i+1, parametros[i]);
        }*/
        //pstmt.setObject(parametros.length + 1, id);
                Statement stmt = null) {
            stmt.executeUpdate(updateSql  + parametros[0].toString());
        }
	    
	    }
	 
	
	/**
	 * @throws SQLException 
	 */
	public void start_wait (int pMID, int pTID, Timestamp ts) throws SQLException, ParseException{
		insertNew(STARTWAIT,getnextwaitid(), pMID, pTID, ts ,null,0);
	}
	
        
        public void addWorkSession (int pMID, int pTID, Timestamp ts_start, Timestamp ts_finish, long minutes) throws SQLException, ParseException{
		insertNew(ADDWORKSESSION,getnextwaitid(), pMID, pTID, ts_start ,ts_finish, minutes);
	}
	
	public void finish_wait(int pMID, int pTID, Timestamp ts) throws SQLException, ParseException {
		String wID =getopenwait(pMID, pTID); 
                System.out.println(ts.toString());
		super.updateNew(FINISHWAIT, ts, ts, wID );
		
//		super.updateNew(UPDATETOTAL, wID);
	}
 
}
