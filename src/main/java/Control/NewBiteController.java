/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import DB.biteDao;
import Model.bite;
import Model.task;
import Util.Util;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class NewBiteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML Button btCancel;
    @FXML Button btOK;
    @FXML TextField edName;
     private MainWindowController parent;
     private biteDao bDao;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btCancel.setCancelButton(true);
        btOK.setDefaultButton(true);
    }    
    @FXML
    public void cancelButtonClick(ActionEvent event)  {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.hide();    
    }
    
    public void okButtonClick(ActionEvent event) throws SQLException  {
        
        //Adicionando o  bite
        
                bite b = new bite(parent.bDao.getnextid(),
                parent.getCurrentTask().getMasterid(),
                parent.getCurrentTask().getId(),
                //Util.InputText("New Bite", "What is next action for this task?", "Bite:", getCurrentTask().getName()),
                edName.getText(),
                Util.DateToSQLDate(parent.getCurrentTask().getDeadline()),
                task.stOPEN,
                bite.stNOTNEXT,
                2,
                Util.DateToSQLDate(new java.util.Date()),
                Util.DateToSQLDate(new java.util.Date()),                
                null,
                parent.getCurrentMaster().getName(),
                parent.getCurrentTask().getName(),
                0,
                0);
        
        parent.tbBites.getItems().add(0, b);
        parent.tbBites.getSelectionModel().select(0);
        parent.bDao.persist(b);
        Util.logStatusChange("BITE CREATION", b.getName(),parent.getCurrentFileName(2));
        
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.hide();    
    }
    
   public void setStageAndSetupListeners(MainWindowController parent)  {
        this.parent = parent;
       
        } 
  
}
