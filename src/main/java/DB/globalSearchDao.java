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
    private final String GlobalSearch  = "SELECT \n" +
"        m.ID AS masterid,\n" +
"        0 as taskid,\n" +
"        0 as actionid,\n" +
"        m.NAME AS name,\n" +
"        \"Project\" type\n" +
"    FROM\n" +
"        mastertasks m\n" +
"        where m.name like ? \n" +
"union all \n" +
"    SELECT \n" +
"       m.id, \n" +
"       t.id as taskid,\n" +
"	   null as actionid,\n" +
"       t.NAME AS name,\n" +
"       \"Task\" as type\n" +
"    FROM\n" +
"        (mastertasks m\n" +
"        LEFT JOIN tasks t ON ((t.MASTERID = m.ID)) \n" +
"        LEFT JOIN roles r  on ( r.id = m.role_id)\n" +
"        )\n" +
"        where t.name like ? \n" +
"union all        \n" +
"    SELECT \n" +
"       m.id, \n" +
"       t.id as taskid,\n" +
"       a.id as actionid,\n" +
"	   a.NAME AS name,\n" +
"       \"Bite\" as type\n" +
"    FROM\n" +
"        (mastertasks m\n" +
"        LEFT JOIN tasks t ON ((t.MASTERID = m.ID)) \n" +
"        LEFT JOIN roles r  on ( r.id = m.role_id)\n" +
"        LEFT JOIN actions a on (a.MASTERID = m.ID and a.TASKID = t.id)\n" +
"        )        \n" +
"        where a.name like ?";
    

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
