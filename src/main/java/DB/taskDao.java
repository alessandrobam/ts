/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import Model.master;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.task;
import java.sql.Timestamp;

/**
 *
 * @author Alessandro
 */
public class taskDao extends GenericDao {

    private final String allRecords = "SELECT * FROM tasks";
    
    private final String GET_TASKS = "SELECT a.*,(CASE status when 3 then null else datediff(deadline,sysdate()) end) as too, t.name as mastername FROM TASKS a, MASTERTASKS t where masterid = ? and t.id = masterid order by status, pinned desc, too, changedon";
    
    private final String GET_OPEN_OVERDUE_TASKS = "SELECT     a.*,    (CASE status        WHEN 3 THEN NULL        ELSE DATEDIFF(deadline, SYSDATE())    END) AS too, t.name as mastername FROM    TASKS a, MASTERTASKS t WHERE  t.id = masterid and  status <> 3     and deadline <= sysdate() ORDER BY status , pinned DESC , too , changedon";
    
    private final String GET_OPEN_TASKS_WITHOUT_NEXT_ACTION = "SELECT a.*,(CASE a.status when 3 then null else datediff(a.deadline,sysdate()) end) as too, t.name as mastername FROM TASKS a, MASTERTASKS t where t.id = masterid and not exists (select MASTERID, ID from actions ac where ac.masterid = a.masterid and ac.taskid = a.id and ac.status <> 3)and a.status <> 3  order by a.status, pinned desc, too, changedon";
    //oracle private final String GET_TASKS = "select a.*, trunc(decode(status,3,to_date(null),deadline)-sysdate,0) too from  (SELECT * FROM TASKS where masterid = ? order by status, changedon desc) a order by status, pinned desc, too, changedon";
    
    
    
    private final String UPDATE = "update tasks set masterid=?, id=?, name=?, category=?, deadline=?, toverdue=?, started=?, restarted=?, finished=?, waiting=?, status=?, minutes= ?, reference=?, changedon=?, created=?, pinned=?, marked=?, golden=?, role_id=? where id=? and masterid =?";
    
    //update start finish or wait time (worksession adjusment)
    
    
    private final String UPDATES  = "update tasks set started = case started when ? then ? else started end where id=? and masterid=?   ";
        
    //oracle private final String UPDATES  = "update tasks set started =  decode(started, ?,?,started)  where id =? and masterid=?";
    //private final String UPDATES  = "update tasks set started = ?-started where id =? and masterid=?";
    
    private final String UPDATEFW = "update tasks set finished = case finished when ? then ? else finished end,  waiting = case waiting when ? then ? else waiting end where id =? and masterid=?";
    //oracle private final String UPDATEFW = "update tasks set finished = decode(finished, ?,?,finished),  waiting = decode(waiting, ?,?,waiting) where id =? and masterid=?";
    
    
    //private final String UPDATE = "update tasks set masterid=?, id=?, name=?, category=?, deadline=?, toverdue=?, started=?, finished=?, waiting=?, status=?, minutes= (to_date(decode(status, 0, null ,1, null,2, WAITING,3, FINISHED))- started)*24*60, reference=?, changedon=?, created=? where id=? and masterid =?";
    private final String NEXTID    = "select COALESCE (max(id),0)+1 from tasks where masterid=?";
    private final String INSERT    = "insert into tasks (masterid, id, name, category, deadline, toverdue, started, restarted, finished, waiting, status, minutes, reference, changedon,created, pinned, marked, golden, role_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String EXISTS = "SELECT count(*) FROM tasks where masterid=? and id=?";
    private final String DELETE = "DELETE from Tasks where masterid=? and id=?";
    private final String DELETE_by_Master = "DELETE from Tasks where masterid=?";
    private final String LASTWORKED = "select masterid, id, status, name from tasks where status > 0 and changedon is not null order by changedon desc";
    
    public taskDao(Connection db) {
        super(db);
    }

    public dbresult getallrecords() throws SQLException {
        try {
            return super.getallrecords(allRecords);
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getnextid (int masterid ) throws SQLException {   
	   return super.getnextid(NEXTID, masterid);
	 }
       
    
    public dbresult getTasks_by_Master(int pID) {
        System.out.println("Selecting taks records for masterid " + pID);
        
        try {
            
            /*-1 ---> All open tasks
              -2 ---> All open tasks with no next actions
              -3 ---> All tasks
           */
            if (pID>0){
               return super.getrecord(GET_TASKS, pID);
            } else
            {
                switch (pID) {
                    case -1:
                        return super.getallrecords(GET_OPEN_OVERDUE_TASKS);
                   case -2:
                        return super.getallrecords(GET_OPEN_TASKS_WITHOUT_NEXT_ACTION);     
                }
            }
        } catch (SQLException e) {
        }
        return null;
    }
   public dbresult getLastTaskWorked() {
        try {
            return super.getrecord(LASTWORKED);
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean databaseExists(task t) throws SQLException
       {
          return  super.getCount(EXISTS, t.getMasterid(), t.getId())>0;
       }
    
    public void delete(int masterid, int id) throws SQLException{
      super.delete(DELETE,masterid, id);
    }
    
    public void deleteTasks(int masterid) throws SQLException{
      super.delete(DELETE_by_Master, masterid);
    }
    
    public void updateSFW(int masterid, int taskid, Timestamp ts_old, Timestamp ts_new, String type) throws SQLException{
      if (type.equals("START")){
        super.updateNew(UPDATES, ts_old,ts_new,  taskid, masterid  );  
        //super.updateNew(UPDATES, taskid, masterid, ts_old  );   
          
        System.out.println(ts_old + "  " + ts_new);
      }else
      {
        super.updateNew(UPDATEFW, ts_old, ts_new, ts_old, ts_new, taskid, masterid );    
      }
    }
    
    public void persist(task task) throws SQLException {
        if (databaseExists(task)) {
            updateNew(UPDATE, task.getMasterid(),
                    task.getId(),
                    task.getName(),
                    task.getTag(),
                    task.getDeadline(),
                    task.getTooverdue(),
                    task.getStart(),
                    task.getRestart(),
                    task.getFinish(),
                    task.getWait(),
                    task.getStatus(),
                    task.getMinutes(),
                    task.getReference(),
                    task.getChangedon(),
                    task.getCreated(),
                    task.getPinned(),
                    task.getMarked(),
                    task.getGolden(),
                    task.getRoleid(),
                    task.getId(),
                    task.getMasterid()
            );
        } else
            insertNew(INSERT, 
                    task.getMasterid(),
                    task.getId(),
                    task.getName(),
                    task.getTag(),
                    task.getDeadline(),
                    task.getTooverdue(),
                    task.getStart(),
                    task.getRestart(),
                    task.getFinish(),
                    task.getWait(),
                    task.getStatus(),
                    task.getMinutes(),
                    task.getReference(),
                    task.getChangedon(),
                    task.getCreated(),
                    task.getPinned(),
                    task.getMarked(),
                    task.getGolden(),
                    task.getRoleid()
            );
        {
            
        }
    }

}
