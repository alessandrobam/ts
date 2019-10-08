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
public class catDao extends GenericDao  {
        private final String ALL_CATS = "SELECT * FROM CATS order by catname";
	private final String NEXT_ID = "SELECT COALESCE (MAX (ID),0)+1 id FROM CATS";
	private final String GET_ID = "SELECT id FROM CATS where catname=?";
	private final String INSERT = "INSERT INTO CATS(id, catname, path) VALUES(?,?,?)";
	private final String UPDATE = "UPDATE CATS set id=?, catname=?, path=? where id=?";
	private final String DELETE = "DELETE FROM CATS where id=?";
	private final String SELECT_CAT = "SELECT * FROM CATS WHERe CATNAME=?";
	

    public catDao(Connection db) {
        super(db);
    }
    
     public String GetCatPath(String pCatName) throws SQLException {
		   dbresult rs= super.getrecord(SELECT_CAT, pCatName);
		   if (rs.getRs().next()){
                   if (rs.getRs().getString("PATH")!=null) {
                     return rs.getRs().getString("PATH");
                   }    
                   }
                   
            return "";
	    }
     
       public dbresult getallrecords() throws SQLException {
        try {
         return super.getallrecords(ALL_CATS);
        } catch (SQLException ex) {
            Logger.getLogger(masterDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }

}
