/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.master;

/**
 *
 * @author Alessandro
 */
public class masterDao extends GenericDao {

//    private final String ALL = "SELECT * FROM mastertasks_v";
    private final String ALL = "SELECT * FROM mastertasks_v where archived <= ? ";
    private final String REFRESH = "SELECT * FROM mastertasks_v where id=? and archived <= ?";
    
    //oracle private final String NEXT_ID = "SELECT COALESCE (MAX (ID),0)+1 id FROM MASTERTASKS";
    private final String NEXT_ID = "SELECT COALESCE (MAX(ID),0)+1 id FROM MASTERTASKS";
    
    	private final String INSERT = "insert into mastertasks (id, name, reference, category, workcount, waitcount, donecount, opencount, minutes, lastupdate, created, role_id) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    
    private final String UPDATE = "UPDATE MASTERTASKS set id=?, name=?, reference=?, category=?"
            + " ,workcount=?, waitcount=?, donecount=?, opencount=?, minutes=?, lastupdate=?, created=?, role_id =? , archived=? where id=?";
    private final String DELETE = "DELETE FROM MASTERTASKS where id=?";
    private final String EXISTS = "SELECT count(*) FROM mastertasks where id=?";
    

    public masterDao(Connection db) {
        super(db);
    }

    public dbresult getallrecords(int archived) throws SQLException {

        try {
            return super.getrecord(ALL, archived);
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   public void delete(int masterid) throws SQLException
   {
       super.delete(DELETE, masterid);
   }
   
   public dbresult getrecord(int masterid, int focusmode) throws SQLException
   {
       return super.getrecord(REFRESH, masterid, focusmode);
   }
    
    public int getnextid() throws SQLException {
        return super.getnextid(NEXT_ID);
    }

    public boolean databaseExists(master m) throws SQLException {
        return super.getCount(EXISTS, m.getId()) > 0;
    }

    public void persist(master master) throws SQLException {
        if (!databaseExists(master)) {
            insertNew(INSERT, master.getId(),
                    master.getName(),
                    master.getReference(),
                    master.getCategory(),
                    master.getWorkcount(),
                    master.getWaitcount(),
                    master.getDonecount(),
                    master.getOpencount(),
                    master.getMinutes(),
                    master.getLastupdate(),
                    master.getCreated(),
                    master.getRoleid()
            );
        } else {
            updateNew(UPDATE, master.getId(),
                    master.getName(),
                    master.getReference(),
                    master.getCategory(),
                    master.getWorkcount(),
                    master.getWaitcount(),
                    master.getDonecount(),
                    master.getOpencount(),
                    master.getMinutes(),
                    master.getLastupdate(),
                    master.getCreated(),
                    master.getRoleid(),
                    master.getArchived(),
                    master.getId()
                    );
        }
    }
}
