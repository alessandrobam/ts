/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;


import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro
 */
public class reportDao extends GenericDao {
    //oracle private final String WORKREPORT = "select w.id, t.id taskid, m.id masterid, m.name mname, t.name tname, to_char(w.started, 'dd/mm/yyyy') pdate,to_char(w.started,'hh24:mi') started, to_char(w.finished,'hh24:mi') finished, w.minutes from waits w, mastertasks m, tasks t where w.taskid  = t.id    and w.masterid = m.id   and t.masterid = m.id   and trunc(w.finished) between trunc(?) and trunc(?) order by w.started desc";
    
    //private final String WORKREPORT = "select r.role_name, w.id, t.id taskid, m.id masterid, m.name mname, t.name tname, date_format(w.started,'%d/%m/%Y')  pdate, date_format(w.started,'%a')  pWeekday, date_format(w.started,'%H:%i') started, date_format(w.finished,'%H:%i') finished,w.minutes from waits w, mastertasks m, tasks t, roles r where r.id = ifnull(t.role_id, m.role_id)  and w.taskid  = t.id    and w.masterid = m.id   and t.masterid = m.id and date(w.finished) between date(?) and date(?) order by w.started desc";
    
    private final String WORKREPORT = "select ifnull(r_t.role_name, r_m.role_name) role_name, w.id, t.id taskid, m.id masterid, m.name mname, t.name tname, date_format(w.started,'%d/%m/%Y')  pdate, date_format(w.started,'%a')  pWeekday, date_format(w.started,'%H:%i') started, date_format(w.finished,'%H:%i') finished,w.minutes from waits w left join mastertasks m on (w.masterid = m.id) left join tasks t on (t.masterid = m.id and t.id = w.taskid)  left join roles r_t on (t.role_id = r_t.id) left join roles r_m on (m.role_id = r_m.id) where  date(w.finished) between date(?) and date(?) order by w.started desc";
    private final String WORKREPORT_role = "select ifnull(r_t.role_name, r_m.role_name) role_name, w.id, t.id taskid, m.id masterid, m.name mname, t.name tname, date_format(w.started,'%d/%m/%Y')  pdate, date_format(w.started,'%a')  pWeekday, date_format(w.started,'%H:%i') started, date_format(w.finished,'%H:%i') finished,w.minutes from waits w left join mastertasks m on (w.masterid = m.id) left join tasks t on (t.masterid = m.id and t.id = w.taskid)  left join roles r_t on (t.role_id = r_t.id) left join roles r_m on (m.role_id = r_m.id) where  date(w.finished) between date(?) and date(?) and ifnull(r_t.role_name, r_m.role_name) = ?  order by w.started desc";
    
    private final String ROLESUMMARY = "select ifnull(r_t.role_name, r_m.role_name) role_name,  ifnull(r_t.PRODUCTIVE, r_m.productive) productive, sum(w.minutes) minutes from waits w left join mastertasks m on (w.masterid = m.id) left join tasks t on (t.masterid = m.id and t.id = w.taskid) left join roles r_t on (t.role_id = r_t.id)  left join roles r_m on (m.role_id = r_m.id) where  date(w.finished) between date(?) and date(?)  group by role_name, productive order by minutes desc";
    
    private final String TASKSESSIONS = "select ' ' as role_name, w.id, t.id taskid, m.id masterid, m.name mname, t.name tname, date_format(w.started, '%d/%m/%Y') pdate, date_format(w.started,'%a')  pWeekday, date_format(w.started,'%d/%m/%Y %H:%i') started, date_format(w.finished,'%d/%m/%Y %H:%i') finished, w.minutes from waits w, mastertasks m, tasks t where w.taskid  = t.id    and w.masterid = m.id   and t.masterid = m.id   and m.id=? and t.id = ? order by w.started desc";
    //oracle private final String TASKSESSIONS = "select w.id, t.id taskid, m.id masterid m.name mname, t.name tname, to_char(w.started, 'dd/mm/yyyy') pdate,to_char(w.started,'dd/mm/yyyy hh24:mi') started, to_char(w.finished,'dd/mm/yyyy hh24:mi') finished, w.minutes from waits w, mastertasks m, tasks t where w.taskid  = t.id    and w.masterid = m.id   and t.masterid = m.id   and m.id=? and t.id = ? order by w.started desc";
    
    
    private final String TOTALSUMARY = "select count(case t.status when 1 then 1 else null end) open, count(case t.status when 2 then 1 else null end) waiting, count(case t.status when 0 then 1 else null end) working from tasks t";
    
    
    
//oracle private final String TOTALSUMARY = "select count(decode(status,1,'OPEN',null)) open, count(decode(status,2,'WAITING',null)) waiting, count(decode(status,0,'WORKING',null)) working from tasks";
    
    
    
    public reportDao(Connection db) {
        super(db);
    }
    
    public ResultSet getallrecords(LocalDate ini, LocalDate fim) throws SQLException {
       try {
            return super.getrecord(WORKREPORT, Date.valueOf(ini), Date.valueOf(fim)).rs;
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
//public ResultSet getallrecords_role(LocalDate ini, LocalDate fim, String roleName) throws SQLException {
//       try {
//            return super.getrecord(WORKREPORT_role, Date.valueOf(ini), Date.valueOf(fim), roleName).rs;
//        } catch (SQLException ex) {
//            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }

       public ResultSet getRoleSummaryForInterval(LocalDate ini, LocalDate fim) throws SQLException {
       try {
            return super.getrecord(ROLESUMMARY, Date.valueOf(ini), Date.valueOf(fim)).rs;
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    public ResultSet getTASKSESSIONS(int masterid, int taskid) throws SQLException {
       try {
            return super.getrecord(TASKSESSIONS, masterid, taskid).rs;
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ResultSet getTotalSummary() throws SQLException {
       try {
            return super.getrecord(TOTALSUMARY).rs;
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
}
