/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import DB.GenericDao.dbresult;
import DB.catDao;
import DB.masterDao;
import DB.roleDao;
import Model.master;
import Model.task;
import Util.TextFileHandler;
import Util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class EditMasterController implements Initializable {

    
    private TableView tbMasterTasks;
    private masterDao mDao;
    private master m;
    private catDao cDao;
    private roleDao rDao;
    private int operation;

    
    @FXML Button btCancel;
    @FXML Button btOK;
    @FXML TextField edName;
    @FXML ComboBox edCategory;
    @FXML ComboBox edRole;
    @FXML
    private TextField edName1;
    
    
    @FXML
    public void okButtonClick(ActionEvent event) throws SQLException, ParseException, IOException  {
      String oldfilename = "";    
      switch (this.operation){
          case Util.opINSERT:
                 m = new master(mDao.getnextid(), "", Util.getCurrentDateMinute() );
                 tbMasterTasks.getItems().add(0, m);
                 tbMasterTasks.getSelectionModel().clearAndSelect(0);
                 tbMasterTasks.scrollTo(m);           
              break;
          case Util.opUPDATE:
                m.setLastupdate(Util.getCurrentDateMinute());
                //return TextFileHandler.GetNotesFile(getCurrentMaster().getName(), fName, TID, true);
                oldfilename = TextFileHandler.GetNotesFile(m.getName(), m.getName(), "Master", false);
              break;
      }
 
     m.setName(edName.getText());
     m.setCategory((String) edCategory.getSelectionModel().getSelectedItem());
     
     m.setRoleName((String) edRole.getSelectionModel().getSelectedItem());
     m.setRoleid(rDao.GetRoleId(m.getRoleName()));
     
     m.setLastupdate(Util.getCurrentDateMinute());
     mDao.persist(m);
     
     String newfilename = TextFileHandler.GetNotesFile(m.getName(), m.getName(), "Master",false);
     TextFileHandler.renameFile (oldfilename, newfilename  );
     
     Stage stage = (Stage) btOK.getScene().getWindow();
     stage.close();    
    }

    
    
        @FXML
    public void cancelButtonClick(ActionEvent event) throws SQLException  {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();    
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
    
    
    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStageAndSetupListeners(Stage stage, TableView ptbMaster, masterDao pmdao, catDao pcdao, roleDao rdao, master pm) throws SQLException  {
        tbMasterTasks =  ptbMaster;
        btOK.setDefaultButton(true);
        btCancel.setCancelButton(true);
        mDao = pmdao;
        cDao = pcdao;
        rDao = rdao;
        m = pm;
        
        
        
        ResultSet rs;
        dbresult dbs;
        
        dbs = cDao.getallrecords();
        rs = dbs.getRs();

        ObservableList<String> tvCatData = FXCollections.observableArrayList();
        while (rs.next()) {
            tvCatData.add(rs.getString("catname"));
        }
        
        edCategory.setItems(tvCatData);
        rs.close();
        
        
        dbs = rDao.getallrecords();
        rs = dbs.getRs();
        ObservableList<String> tvRoleData = FXCollections.observableArrayList();
        while (rs.next()) {
            tvRoleData.add(rs.getString("role_name"));
        }
        edRole.setItems(tvRoleData);
        rs.close();
        
        if (m!=null){
            edName.setText(m.getName());
            edRole.getSelectionModel().select(m.getRoleName());
            edCategory.getSelectionModel().select(m.getCategory());
            
            
        }
    }
}
