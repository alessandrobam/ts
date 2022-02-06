package DB;

import Util.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alessandro
 */
public class globalSearchDao extends GenericDao {
	private final String GlobalSearch = "SELECT m.id      AS masterid,\r\n" + 
			"       0         AS taskid,\r\n" + 
			"       0         AS actionid,\r\n" + 
			"       m.NAME    AS NAME,\r\n" + 
			"       \"project\" TYPE,\r\n" + 
			"       \"\"        AS tag,\r\n" + 
			"       m.NAME AS MASTER\r\n" +
			"FROM   mastertasks m\r\n" + 
			"WHERE  m.NAME LIKE ?\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT m.id,\r\n" + 
			"       t.id       AS taskid,\r\n" + 
			"       NULL       AS actionid,\r\n" + 
			"       t.NAME     AS NAME,\r\n" + 
			"       \"task\"     AS TYPE,\r\n" + 
			"       t.category tag,\r\n" + 
			"       m.NAME AS MASTER\r\n" +
			"FROM   (mastertasks m\r\n" + 
			"        LEFT JOIN tasks t\r\n" + 
			"               ON (( t.masterid = m.id ))\r\n" + 
			"        LEFT JOIN roles r\r\n" + 
			"               ON ( r.id = m.role_id ) )\r\n" + 
			"WHERE  Concat(t.NAME, t.category) LIKE ?\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT m.id,\r\n" + 
			"       t.id   AS taskid,\r\n" + 
			"       a.id   AS actionid,\r\n" + 
			"       a.NAME AS NAME,\r\n" + 
			"       \"bite\" AS TYPE,\r\n" + 
			"       \"\"     AS tag,\r\n" +
			"       m.NAME AS MASTER\r\n" +
			"FROM   (mastertasks m\r\n" + 
			"        LEFT JOIN tasks t\r\n" + 
			"               ON (( t.masterid = m.id ))\r\n" + 
			"        LEFT JOIN roles r\r\n" + 
			"               ON ( r.id = m.role_id )\r\n" + 
			"        LEFT JOIN actions a\r\n" + 
			"               ON ( a.masterid = m.id\r\n" + 
			"                    AND a.taskid = t.id ) )\r\n" + 
			"WHERE  a.NAME LIKE ?";
	
	    

    public globalSearchDao(Connection conn) {
       super(conn);
    }

   
    
      public ResultSet runGlobalSearch(String textFind) throws SQLException {
       try {
           textFind = "%" + textFind + "%";
            return super.getrecord(GlobalSearch, textFind, textFind, textFind).rs;
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
	
}
