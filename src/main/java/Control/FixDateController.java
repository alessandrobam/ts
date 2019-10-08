/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import DB.taskDao;
import DB.waitDao;
import Model.master;
import Model.task;
import Model.worksession;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class FixDateController implements Initializable {

    
    @FXML Button btCancel;
    @FXML Button btOK;
    @FXML TextField edIni;
    @FXML TextField edFim;
    waitDao wDao;
    worksession wSession;
    
    taskDao tDao;
    task t;
    
    MainWindowController parent;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setStageAndSetupListeners(MainWindowController parent, worksession ws, waitDao wDao)  {
        edIni.setText(ws.getStart());
        edFim.setText(ws.getFinish());
        this.wDao = wDao;
        wSession = ws;
        this.parent = parent;
        
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.getScene().getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            try {
                if (event.getCode() == KeyCode.ENTER) {
                    btOKClick(null);
                    event.consume();
                } else {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        cancelButtonClick(null);
                    }                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReescheduleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    public void btOKClick(ActionEvent event) throws SQLException  {
       //saveToFile();
       //parsing
        
       int tableIndex = parent.tbWs.getSelectionModel().getFocusedIndex();
               
       int taskid = wSession.getTaskid();
       int masterid = wSession.getMasterid();
       
       String ini = edIni.getText();
       String fim = edFim.getText();

       LocalDateTime ts_ini = LocalDateTime.parse(ini, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
       LocalDateTime ts_fim = LocalDateTime.parse(fim, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
       
       LocalDateTime ts_ini_old = LocalDateTime.parse(wSession.getStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
       LocalDateTime ts_fim_old = LocalDateTime.parse(wSession.getFinish(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        
       wDao.updateWorkSession( Integer.parseInt(wSession.getId()), Timestamp.valueOf(ts_ini), Timestamp.valueOf(ts_fim));
       parent.tDao.updateSFW(masterid, taskid, Timestamp.valueOf(ts_ini_old), Timestamp.valueOf(ts_ini), "START");
       parent.tDao.updateSFW(masterid, taskid, Timestamp.valueOf(ts_fim_old), Timestamp.valueOf(ts_fim), "FINISH");
       
       long waitminutes = parent.wDao.getSumWait(wSession.getMasterid(), wSession.getTaskid());
       parent.getCurrentTask().setMinutes(waitminutes);
       parent.tDao.persist(parent.getCurrentTask());
       
       
       parent.getWorkSessionData(masterid, taskid);
       parent.refreshMaster();
       parent.tbWs.getSelectionModel().select(tableIndex);
       
       Stage stage = (Stage) btOK.getScene().getWindow();
       stage.close();
    }

    @FXML
    public void cancelButtonClick(ActionEvent event) throws SQLException {
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
        //System.out.println("cancelButtonClick");
        //String a = WordUtils.capitalize(tfDiaSemana.getText().toLowerCase());
        //tfDiaSemana.setText(a);
    }
}
