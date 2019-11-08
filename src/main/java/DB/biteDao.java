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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro
 */
public class biteDao extends GenericDao {

	
	
   public enum Sources {
		  PLANNING,
		  ACTION
		}

   public enum FilterModifier {
	   	  ALL, 
	      LATE,
		  OPEN,
		  OPEN_AND_LATE
		  
		}

   
   
	private final String getBitesRecords = "SELECT a.*,\r\n" + 
			"       10                              countdown2,\r\n" + 
			"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
			"       m.NAME                          mastername,\r\n" + 
			"       t.NAME                          taskname\r\n" + 
			"FROM   actions a,\r\n" + 
			"       tasks t,\r\n" + 
			"       mastertasks m\r\n" + 
			"WHERE " +
			"a.taskid = t.id\r\n" + 
			 "       AND t.masterid = a.masterid\r\n" + 
             "       AND m.id = a.masterid\r\n" +
			"{filter} \r\n" +
			"ORDER  BY a.status,\r\n" + 
			"          a.next DESC,\r\n" + 
			"          a.deadline DESC,\r\n" + 
			"          a.created  ";

	

	
	public final static String task_planning = "       AND a.name REGEXP '^(w|d|deliverable)\\\\s\\-'";
	public final static String dates_late    = "       AND a.deadline < ? \r\n";  
	public final static String dates_between = "       AND a.deadline BETWEEN ? AND ?\r\n";  
	public final static String status_range  = "       AND a.status BETWEEN ? AND ?\r\n" ; 
	public final static String master_id     = "       AND a.masterid = ?\r\n" ;
	public final static String task_id       = "       AND a.masterid = ? AND a.taskid = ? \r\n" ;
	public final static String type_planning = "       AND a.name REGEXP '^(w|d|deliverable)\\\\s\\-'"; 
	public final static String type_action   = "       AND not (a.name REGEXP '^(w|d|deliverable)\\\\s\\-')";
	  	
	
//	
//	private final String allRecords = "SELECT a.*,\r\n" + 
//										"       10                              countdown2,\r\n" + 
//										"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
//										"       m.NAME                          mastername,\r\n" + 
//										"       t.NAME                          taskname\r\n" + 
//										"FROM   actions a,\r\n" + 
//										"       tasks t,\r\n" + 
//										"       mastertasks m\r\n" + 
//										"WHERE  a.taskid = t.id\r\n" + 
//										"       AND t.masterid = a.masterid\r\n" + 
//										"       AND m.id = a.masterid\r\n" + 
//										"ORDER  BY a.status,\r\n" + 
//										"          a.next DESC,\r\n" + 
//										"          a.deadline DESC,\r\n" + 
//										"          a.created  ";
//
//	private final String allRecords_for_master = " SELECT a.*,\r\n" + 
//													"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
//													"       m.NAME                          mastername,\r\n" + 
//													"       t.NAME                          taskname\r\n" + 
//													"FROM   actions a,\r\n" + 
//													"       tasks t,\r\n" + 
//													"       mastertasks m\r\n" + 
//													"WHERE  a.taskid = t.id\r\n" + 
//													"       AND t.masterid = a.masterid\r\n" + 
//													"       AND ( a.NAME LIKE ?\r\n" + 
//													"              OR a.NAME LIKE ? )\r\n" + 
//													"       AND m.id = a.masterid\r\n" + 
//													"       AND a.masterid = ?\r\n" + 
//													"ORDER  BY a.status,\r\n" + 
//													"          a.deadline DESC,\r\n" + 
//													"          a.created";
//											
//	
//	
//	private final String alllateRecords_for_master = " SELECT a.*,\r\n" + 
//													"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
//													"       m.NAME                          mastername,\r\n" + 
//													"       t.NAME                          taskname\r\n" + 
//													"FROM   actions a,\r\n" + 
//													"       tasks t,\r\n" + 
//													"       mastertasks m\r\n" + 
//													"WHERE  a.taskid = t.id\r\n" + 
//													"       AND t.masterid = a.masterid\r\n" + 
//													"       AND ( a.NAME LIKE ?\r\n" + 
//													"              OR a.NAME LIKE ? )\r\n" + 
//													"       AND m.id = a.masterid\r\n" + 
//													"       AND a.masterid = ?\r\n" + 
//													"       AND a.deadline <= Sysdate()\r\n" + 
//													"       AND a.status < 3\r\n" + 
//													"ORDER  BY a.status,\r\n" + 
//													"          a.deadline DESC,\r\n" + 
//													"          a.created  ";
//											
//	
//	private final String allRecords_for_task = " SELECT a.*,\r\n" + 
//												"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
//												"       m.NAME                          mastername,\r\n" + 
//												"       t.NAME                          taskname\r\n" + 
//												"FROM   actions a,\r\n" + 
//												"       tasks t,\r\n" + 
//												"       mastertasks m\r\n" + 
//												"WHERE  a.taskid = t.id\r\n" + 
//												"       AND t.masterid = a.masterid\r\n" + 
//												"       AND ( a.NAME LIKE ?\r\n" + 
//												"              OR a.NAME LIKE ? )\r\n" + 
//												"       AND m.id = a.masterid\r\n" + 
//												"       AND a.masterid = ?\r\n" + 
//												"       AND a.taskid = ?\r\n" + 
//												"ORDER  BY a.status,\r\n" + 
//												"          a.deadline DESC,\r\n" + 
//												"          a.created";
//
//	private final String alllateRecords_for_task = " SELECT a.*,\r\n" + 
//													"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
//													"       m.NAME                          mastername,\r\n" + 
//													"       t.NAME                          taskname\r\n" + 
//													"FROM   actions a,\r\n" + 
//													"       tasks t,\r\n" + 
//													"       mastertasks m\r\n" + 
//													"WHERE  a.taskid = t.id\r\n" + 
//													"       AND t.masterid = a.masterid\r\n" + 
//													"       AND ( a.NAME LIKE ?\r\n" + 
//													"              OR a.NAME LIKE ? )\r\n" + 
//													"       AND m.id = a.masterid\r\n" + 
//													"       AND a.masterid = ?\r\n" + 
//													"       AND a.taskid = ?\r\n" + 
//													"       AND a.deadline <= Sysdate()\r\n" + 
//													"       AND a.status < 3\r\n" + 
//													"ORDER  BY a.status,\r\n" + 
//													"          a.deadline DESC,\r\n" + 
//													"          a.created  ";
//
	private final String allRecordsBetweenDates = " SELECT a.*,\r\n" + 
													"       Datediff(a.deadline, Sysdate()) countdown,\r\n" + 
													"       m.NAME                          mastername,\r\n" + 
													"       t.NAME                          taskname\r\n" + 
													"FROM   actions a,\r\n" + 
													"       tasks t,\r\n" + 
													"       mastertasks m\r\n" + 
													"WHERE  a.taskid = t.id\r\n" + 
													"       AND t.masterid = a.masterid\r\n" + 
													"       AND m.id = a.masterid\r\n" + 
													"       AND ( a.NAME LIKE ?\r\n" + 
													"              OR a.NAME LIKE ? )\r\n" + 
													"       AND a.deadline BETWEEN ? AND ?\r\n" + 
													"       AND a.status BETWEEN ? AND ?\r\n" + 
													"ORDER  BY a.status,\r\n" + 
													"          a.next DESC,\r\n" + 
													"          a.golden DESC,\r\n" + 
													"          a.deadline DESC,\r\n" + 
													"          a.created  ";

	private final String DELETE = " DELETE FROM actions\r\n" + 
			                      "WHERE  id = ?  ";
	
	
	private final String DELETE_FOR_MASTER = " DELETE FROM actions\r\n" + 
			                                 "WHERE  masterid = ?  ";
	
	
	private final String DELETE_FOR_TASK = " DELETE FROM actions\r\n" + 
											"WHERE  masterid = ?\r\n" + 
											"       AND taskid = ?  ";
	private final String UPDATE = " UPDATE actions\r\n" + 
									"SET    NAME = ?,\r\n" + 
									"       deadline = ?,\r\n" + 
									"       finished = ?,\r\n" + 
									"       lastreschedule = ?,\r\n" + 
									"       status = ?,\r\n" + 
									"       next = ?,\r\n" + 
									"       priority = ?,\r\n" + 
									"       golden = ?\r\n" + 
									"WHERE  id = ?\r\n" + 
									"       AND masterid = ?\r\n" + 
									"       AND taskid = ?  ";
	
	private final String INSERT = " INSERT INTO actions\r\n" + 
									"            (id,\r\n" + 
									"             masterid,\r\n" + 
									"             taskid,\r\n" + 
									"             name,\r\n" + 
									"             deadline,\r\n" + 
									"             finished,\r\n" + 
									"             status,\r\n" + 
									"             NEXT,\r\n" + 
									"             priority,\r\n" + 
									"             created,\r\n" + 
									"             lastreschedule,\r\n" + 
									"             golden)\r\n" + 
									"VALUES      (?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?,\r\n" + 
									"             ?)  ";
	
	private final String EXISTS = " SELECT Count(*)\r\n" + 
									"FROM   actions\r\n" + 
									"WHERE  masterid = ?\r\n" + 
									"       AND taskid = ?\r\n" + 
									"       AND id = ?  ";
	
	private final String BITE_COUNT_FOR_A_DAY = " SELECT Count(*)\r\n" + 
												"FROM   actions\r\n" + 
												"WHERE  deadline=?\r\n" + 
												"AND    status= " + task.stOPEN;
	
	
	private final String NEXTID = "select COALESCE (max(id),0)+1 from actions";

	
	public String parseSQL (String sql_template, String filter) throws SQLException {
		String sql;
		
		sql = sql_template.replace("{filter}", filter); //
	    
		return sql;
	}
	
	
//	public dbresult getPlanningTasks (LocalDate pStartDate, LocalDate pEndDate, boolean pOpenOnly) throws SQLException {
//		
//		String sql = this.parseSQL(Sources.PLANNING, this.task_planning + this.dates_between +  this.status_range);
//		
//		int statusIni, statusFim;
//		
//		statusIni = 0;
//		statusFim = 0;
//	
//		if (!pOpenOnly) {
//			statusIni = 0;
//			statusFim = 3;
//		}
//		
//		return super.getrecord(sql , java.sql.Date.valueOf(pStartDate) , java.sql.Date.valueOf(pEndDate) , statusIni, statusFim);
//
//	}
//	
	
	public dbresult getBites(Sources source, String filter, Object... parametros) throws SQLException {
		filter = (source == Sources.ACTION) ? filter + type_action : filter + type_planning;
		String sql = this.parseSQL(getBitesRecords, filter);
		return super.getrecord(sql , parametros);
	}

	
	
	
	
	
	
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
