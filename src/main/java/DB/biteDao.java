/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DB;

import Model.bite;
import Model.task;
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
public class biteDao extends GenericDao {

	private final String allRecords = "SELECT a.*, 10 countdown2, datediff(a.deadline,sysdate()) countdown,  m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and m.id = a.masterid order by a.status, a.next desc, a.deadline desc, a.created";

	private final String allRecords_for_master = "SELECT a.*,datediff(a.deadline,sysdate()) countdown,   m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and (a.name like ? or a.name like ?)  and m.id = a.masterid and a.masterid=? order by a.status, a.deadline desc, a.created";
	private final String alllateRecords_for_master = "SELECT a.*,datediff(a.deadline,sysdate()) countdown,   m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and (a.name like ? or a.name like ?) and m.id = a.masterid and a.masterid=? and a.deadline <= sysdate() and a.status < 3 order by a.status, a.deadline desc, a.created";
	
	
	

	private final String allRecords_for_task = "SELECT a.*, datediff(a.deadline,sysdate()) countdown,  m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and (a.name like ? or a.name like ?) and m.id = a.masterid and a.masterid=? and a.taskid=? order by a.status, a.deadline  desc, a.created";

	private final String alllateRecords_for_task = "SELECT a.*, datediff(a.deadline,sysdate()) countdown,  m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and (a.name like ? or a.name like ?)  and m.id = a.masterid and a.masterid=? and a.taskid=? and a.deadline <= sysdate() and a.status < 3 order by a.status, a.deadline  desc, a.created";

	private final String allRecordsBetweenDates = "SELECT a.*, datediff(a.deadline,sysdate()) countdown,  m.name mastername, t.name taskname FROM actions a, tasks t, mastertasks m WHERE a.taskid = t.id and t.masterid = a.masterid and m.id = a.masterid and (a.name like ? or a.name like ?) and a.deadline between ? and ? and a.status between ? and ? order by a.status, a.next desc, a.golden desc, a.deadline desc, a.created";

	private final String DELETE = "delete from actions where id=?";
	private final String DELETE_FOR_MASTER = "delete from actions where masterid=?";
	private final String DELETE_FOR_TASK = "delete from actions where masterid=? and taskid=?";
	private final String UPDATE = "update actions set name=?, deadline=?, finished=?, lastreschedule=?, status=?, next=?, priority=?, golden=? where id=? and masterid=? and taskid=?";
	private final String INSERT = "insert into actions (id, masterid, taskid, name, deadline, finished, status, next, priority, created, lastreschedule, golden) values (?,?,?,?,?,?,?,?,?,?,?,?)";
	private final String EXISTS = "SELECT count(*) FROM actions where masterid=? and taskid=? and id=?";
	private final String BITE_COUNT_FOR_A_DAY = "SELECT count(*) FROM actions where DEADLINE=? and status="
			+ task.stOPEN;
	private final String NEXTID = "select COALESCE (max(id),0)+1 from actions";

	public biteDao(Connection db) {
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

	public dbresult selectrecords(String pLike, String pLike2, LocalDate pDate1, LocalDate pDate2, int pStatusIni, int pStatusFim)
			throws SQLException {
		System.out.println("Select record");
		return super.getrecord(allRecordsBetweenDates, pLike, pLike2, java.sql.Date.valueOf(pDate1),
				java.sql.Date.valueOf(pDate2), pStatusIni, pStatusFim);

	}



	
	public dbresult selectrecords_for_master(String pLike, String pLike2, int pMasterid) throws SQLException {
		System.out.println("Select record");
		return super.getrecord(allRecords_for_master, pLike, pLike2, pMasterid);
	}

	public dbresult selectlaterecords_for_master(String pLike, String pLike2, int pMasterid) throws SQLException {
		System.out.println("Select record");
		return super.getrecord(alllateRecords_for_master, pLike, pLike2, pMasterid);
	}

	public dbresult selectrecords_for_task(String pLike, String pLike2, int pMasterid, int pTaskid) throws SQLException {
		System.out.println("Select record");
		return super.getrecord(allRecords_for_task, pLike, pLike2, pMasterid, pTaskid);
	}

	public dbresult selectlaterecords_for_task(String pLike, String pLike2, int pMasterid, int pTaskid) throws SQLException {
		System.out.println("Select record");
		return super.getrecord(alllateRecords_for_task, pLike, pLike2, pMasterid, pTaskid);
	}

	public void delete(int pID) throws SQLException {
		super.delete(DELETE, pID);
	}

	public void deleteBitesforMaster(int pMID) throws SQLException {
		super.delete(DELETE_FOR_MASTER, pMID);
	}

	public void deleteBitesforTask(int pMID, int pID) throws SQLException {
		super.delete(DELETE_FOR_TASK, pMID, pID);
	}

	public boolean databaseExists(bite b) throws SQLException {
		return super.getCount(EXISTS, b.getMasterid(), b.getTaskid(), b.getId()) > 0;
	}

	public int getCountForDay(LocalDate day) throws SQLException {
		return super.getCount(BITE_COUNT_FOR_A_DAY, Date.valueOf(day));
	}

	public int getnextid() throws SQLException {
		return super.getnextid(NEXTID);
	}

	public void persist(bite bite) throws SQLException {
		if (databaseExists(bite)) {
			updateNew(UPDATE, bite.getName(), bite.getDeadline(), bite.getFinished(), bite.getLastreschedule(),
					bite.getStatus(), bite.getNext(), bite.getPriority(), bite.getGolden(), bite.getId(),
					bite.getMasterid(), bite.getTaskid()

			);
		} else
			insertNew(INSERT, bite.getId(), bite.getMasterid(), bite.getTaskid(), bite.getName(), bite.getDeadline(),
					bite.getFinished(), bite.getStatus(), bite.getNext(), bite.getPriority(), bite.getCreated(),
					bite.getLastreschedule(), bite.getGolden());
		{

		}
	}

}
