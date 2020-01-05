package Control;

import DB.GenericDao;
import DB.masterDao;
import DB.roleDao;
import DB.taskDao;
import Model.master;
import Model.task;
import Util.TextFileHandler;
import Util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class EditTaskController implements Initializable {
    
    private TableView tbTasks;
    private taskDao tDao;
    private roleDao rDao;
    
    private master m;
    private task t;
    private MainWindowController parent;

    
    
    @FXML Button btCancel;
    @FXML Button btOK;
    @FXML TextField edName;
    @FXML TextField edTag;
    @FXML ComboBox edRole;
    
    
        private int operation;

    /**
     * Get the value of operation
     *
     * @return the value of operation
     */
    public int getOperation() {
        return operation;
    }

    /**
     * Set the value of operation
     *
     * @param operation new value of operation
     */
    public void setOperation(int operation) {
        this.operation = operation;
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    @FXML
    public void okButtonClick(ActionEvent event) throws SQLException, ParseException, IOException  {
        String oldfilename = "";
        switch (this.operation){
          case Util.opINSERT:
                 t = new task(m.getId(), tDao.getnextid(m.getId()), "", Util.getCurrentDateMinute());
                 tbTasks.getItems().add(0,t);
                 t.setStatus(task.stOPEN);
                 t.setStatusprev(task.stUNSET);
                 t.setDeadline(Date.valueOf(LocalDate.now()));
                 //tbTasks.getSelectionModel().select(t);
                 //tbTasks.getSelectionModel().focus(0);
                 //tbTasks.scrollTo(t);           
              break;
          case Util.opUPDATE:
              oldfilename = TextFileHandler.GetNotesFile(m.getName(), t.getName(), String.valueOf(t.getId()), false);
          break;
      }
     t.setName(edName.getText());
     t.setTag(edTag.getText());
     t.setChangedon(Util.getCurrentDateMinute());
     
     
     t.setRoleName((String) edRole.getSelectionModel().getSelectedItem());
     t.setRoleid(rDao.GetRoleId(t.getRoleName()));

     
     
     if (t.getTag()!=null &&   t.getTag().toUpperCase().contains("[PIN]")) {
           t.setPinned(1);
        }else
        {
          t.setPinned(0);
        }
        
     
     
     tDao.persist(t);
     
     String newfilename = TextFileHandler.GetNotesFile(m.getName(), t.getName(), String.valueOf(t.getId()),false);
     
     System.out.println(newfilename);
     
     TextFileHandler.renameFile (oldfilename, newfilename  );
     Util.logStatusChange("CREATED", "Task Creation", newfilename );
     
     
     parent.refreshMaster();
     parent.selectTaskById(t.getId());
     Stage stage = (Stage) btOK.getScene().getWindow();
     stage.hide();
     parent.focusAndSelect(tbTasks);
     
    }

    @FXML
    public void cancelButtonClick(ActionEvent event) throws SQLException  {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();    
    }
    
    public void setStageAndSetupListeners(MainWindowController parent, task pt, master pm) throws SQLException  {
        this.parent = parent;
        tbTasks =  parent.tbTasks;
        btOK.setDefaultButton(true);
        btCancel.setCancelButton(true);
        tDao = parent.tDao;
        rDao = parent.roDao;
        t = pt;
        m = pm;
        
        ResultSet rs;
        GenericDao.dbresult dbs;
        

        
        dbs = rDao.getallrecords();
        rs = dbs.getRs();
        ObservableList<String> tvRoleData = FXCollections.observableArrayList();
        while (rs.next()) {
            tvRoleData.add(rs.getString("role_name"));
        }
        tvRoleData.add("");
        edRole.setItems(tvRoleData);
        rs.close();

        if (t!=null){
            edName.setText(t.getName());
            edTag.setText(t.getTag());
            edRole.getSelectionModel().select(t.getRoleName());
        }
        
        
    }
}
