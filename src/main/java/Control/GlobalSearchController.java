/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DB.globalSearchDao;
import Model.globalitem;
import Model.worksession;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author abarbosa
 */
public class GlobalSearchController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    Button btCancel;
    @FXML
    Button btOK;
    @FXML
    Button btGO;
    
    @FXML
    TableView tbReport;
    
    
    @FXML
    public TextField textFind;
    
     @FXML
    private TableColumn<globalitem, String> name;
    @FXML
    private TableColumn<globalitem, String> tag;
    
    @FXML
    private TableColumn<globalitem, String> type;

    @FXML
    private TableColumn<globalitem, String> master;
    
    
    private Stage stage;
    private MainWindowController parentcontroller;
    private globalSearchDao gsDao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    public void setStageAndSetupListeners(MainWindowController parentcontroller, Stage stage, globalSearchDao gsDao) throws SQLException {
        this.stage = stage;
        this.gsDao = gsDao;

        this.parentcontroller = parentcontroller;

        
        
        
        stage.getScene().getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                        if (event.getCode() == KeyCode.ENTER) {
                            try {
                                if (!tbReport.isFocused()) {
                                    goButtonClick(null);
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }  else
            { if (event.getCode() == KeyCode.ESCAPE) {
                 cancelButtonClick(null);
            }}
        });

        tbReport.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                globalitem gi;
                gi = (globalitem) tbReport.getSelectionModel().getSelectedItem();
                
                 if (gi.getTaskid() == 0) {
                   parentcontroller.findAndSelectProjectByID(gi.getMasterid(), true);
                   parentcontroller.tbMasterTasks.requestFocus();
                } else
                {
                   parentcontroller.findAndSelectProjectByID(gi.getMasterid(), true); 
                   parentcontroller.findAndSelectTaskByID(gi.getTaskid(), true);
                   parentcontroller.tbTasks.requestFocus();
                }
                cancelButtonClick(null);
                event.consume();
            
            }
                
            
            
        });

        tbReport.requestFocus();
        
        //Colocando este listener por causa de um bug que nao atualiza o datepicker
        //goButtonClick(null);
    }

    public globalitem createGI(ResultSet rs) throws SQLException {
        return new globalitem(
                rs.getInt("MASTERID"),
                rs.getInt("TASKID"),
                rs.getInt("ACTIONID"),
                rs.getString("NAME"),
                rs.getString("TYPE"),
                rs.getString("TAG"),
                rs.getString("MASTER")
                
        );
    }
    
 
    
    private void getReport(String textFind) throws SQLException {

        
        ResultSet rs;
        rs = gsDao.runGlobalSearch(textFind);

        ObservableList<globalitem> tvReportData = FXCollections.observableArrayList();
        

        SetValueFactory();   
        globalitem gi;
        boolean add = true;
        while (rs.next()) {
            
               gi = createGI(rs);    
               tvReportData.addAll(gi);
            }
                        
        
        tbReport.setItems(tvReportData);
        

       
    }

    @SuppressWarnings("empty-statement")
    public void SetValueFactory() {
        int i;

        String[] variableNames = {"name", "tag", "type","master"};

        i = 0;

        name.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        tag.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        type.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        master.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        
        
        
    }

    @FXML
    public void okButtonClick(ActionEvent event) {
//        getReport(dpDateIni.getValue(),dpDateFim.getValue() );
        //globalitem gi_selected ;
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
       
    }

    @FXML
    public void goButtonClick(ActionEvent event) throws SQLException {
        getReport(textFind.getText());
        tbReport.getSelectionModel().select(0);
        tbReport.requestFocus();
        
        //Stage stage = (Stage) btOK.getScene().getWindow();
//        stage.hide();
        
    }

    @FXML
    public void cancelButtonClick(ActionEvent event) {

        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
    }

    
}
