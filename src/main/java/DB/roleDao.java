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

/**
 *
 * @author Alessandro
 */
public class roleDao extends GenericDao  {
        private final String ALL_ROLES = "SELECT * FROM ROLES order by ROLE_NAME";
        private final String GET_ID = "SELECT ID FROM ROLES where ROLE_NAME=?";
        private final String GET_name = "SELECT ROLE_NAME FROM ROLES where ID=?";
	

    public roleDao(Connection db) {
        super(db);
    }
    
    
    public int GetRoleId(String pRoleName) throws SQLException {
		   dbresult rs= super.getrecord(GET_ID, pRoleName);
		   if (rs.getRs().next()){
                   if (rs.getRs().getString("ID")!=null) {
                     return rs.getRs().getInt("id");
                   }    
                   }
            return 0;
	    }
     
       public String GetRoleName(int roleID) throws SQLException {
		   dbresult rs= super.getrecord(GET_name, roleID);
		   if (rs.getRs().next()){
                   if (rs.getRs().getString("ROLE_NAME")!=null) {
                     return rs.getRs().getString("ROLE_NAME");
                   }    
                   }
            return "";	    }
     
    
       public GenericDao.dbresult getallrecords() throws SQLException {
        try {
         return super.getallrecords(ALL_ROLES);
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }

}
