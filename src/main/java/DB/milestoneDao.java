/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.milestones;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abarbosa
 */
public class milestoneDao extends GenericDao {
    private final String allOpenMilesTone = "select * from milestones order by status, golden";
    private final String DELETE = "delete from milestones where id=?";
    private final String UPDATE = "update milestones set id=?, masterid=?, taskid=?, name=?, status=?, golden=?, created=?, finished=? where id=?";
    private final String INSERT    = "insert into milestones (id, masterid, taskid, name, status, golden, created, finished) values (?,?,?,?,?,?,?,?)";
    
    private final String EXISTS = "SELECT count(*) FROM milestones where id=?";
    //private final String BITE_COUNT_FOR_A_DAY = "SELECT count(*) FROM actions where DEADLINE=? and status=" + task.stOPEN ;
    private final String NEXTID    = "select COALESCE (max(id),0)+1 from milestones";
    
    
    public int getnextid ( ) throws SQLException {   
	   return super.getnextid(NEXTID);
	}
        
     
      public void delete (int pID) throws SQLException {
		super.delete(DELETE, pID );
	}
	
    
      public dbresult getallrecords() throws SQLException {

        try {
            return super.getallrecords(allOpenMilesTone);
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

      
    public milestoneDao(Connection db) {
        super(db);
    }

   
  public boolean databaseExists(milestones b) throws SQLException
       {
          return  super.getCount(EXISTS, b.getId())>0;
       }
  
    public void persist(milestones miles) throws SQLException {
        if (databaseExists(miles)) {
            updateNew(UPDATE, 
                    miles.getId(),
                    miles.getMasterid(),
                    miles.getTaskid(),
                    miles.getName(),
                    miles.getStatus(),
                    miles.getGolden(),
                    miles.getCreated(),
                    miles.getFinished(),
                    miles.getId()
            );
        } else
            insertNew(INSERT, 
                    miles.getId(),
                    miles.getMasterid(),
                    miles.getTaskid(),
                    miles.getName(),
                    miles.getStatus(),
                    miles.getGolden(),
                    miles.getCreated(),
                    miles.getFinished()
            );
        {
            
        }
    }

  

}
