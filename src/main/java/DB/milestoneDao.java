/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.milestones;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import DB.GenericDao.dbresult;

/**
 *
 * @author abarbosa
 */
public class milestoneDao extends GenericDao {
    private final String allOpenMilesTone = "select * from milestones order by status, golden";
    private final String DELETE = "delete from milestones where id=?";
    private final String UPDATE = "update milestones set id=?, masterid=?, taskid=?, name=?, status=?, golden=?, created=?, finished=? where id=?";
    private final String INSERT    = "insert into milestones (id, masterid, taskid, name, status, golden, created, finished) values (?,?,?,?,?,?,?,?)";
    
    private final String current_week_deliverables = "SELECT t.ID, t.MASTERID, a.TASKID,  a.NAME, a.status, a.GOLDEN, a.CREATED, a.FINISHED from actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and m.id = a.masterid and (a.name like \"d -%\" or a.name like \"milestone -%\" or a.name like \"m -%\" or a.name like \"deliverable -%\") and a.deadline between ? and ? order by a.status, a.next desc, a.golden desc, a.deadline desc, a.created";
    
    private final String EXISTS = "SELECT count(*) FROM milestones where id=?";
    //private final String BITE_COUNT_FOR_A_DAY = "SELECT count(*) FROM actions where DEADLINE=? and status=" + task.stOPEN ;
    private final String NEXTID    = "select COALESCE (max(id),0)+1 from milestones";
    
    
    public int getnextid ( ) throws SQLException {   
	   return super.getnextid(NEXTID);
	}
        
     
      public void delete (int pID) throws SQLException {
		super.delete(DELETE, pID );
	}
	
      
  	public dbresult current_deliverables(LocalDate pDate1, LocalDate pDate2)
			throws SQLException {
		
		return super.getrecord(current_week_deliverables, java.sql.Date.valueOf(pDate1),
				java.sql.Date.valueOf(pDate2));

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
