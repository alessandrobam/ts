/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.worksummary;
import DB.reportDao;
import Model.worksession;
import Util.Util;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class WorkReportController implements Initializable {

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
    TableView tbSummary;
    
    
    @FXML
    DatePicker dpDateIni;
    @FXML
    DatePicker dpDateFim;
    @FXML
    Text tfTotal;

    @FXML
    private TableColumn<worksession, String> id;
    @FXML
    private TableColumn<worksession, String> project;
    @FXML
    private TableColumn<worksession, String> task;
    @FXML
    private TableColumn<worksession, String> date;
    @FXML
    private TableColumn<worksession, String> pweekday;
    @FXML
    private TableColumn<worksession, String> started;
    @FXML
    private TableColumn<worksession, String> finished;
    @FXML
    private TableColumn<worksession, String> total;
    
    @FXML
    private TableColumn<worksession, String> role;
    
    
    @FXML
    private TableColumn<worksummary, String> role_name;
    @FXML
    private TableColumn<worksummary, String> total_summary;

    
    
    private Stage stage;
    private MainWindowController parentcontroller;
    private reportDao rDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SetValueFactory();
        addEventListeners();
    }

    public void setStageAndSetupListeners(MainWindowController parentcontroller, Stage stage, reportDao rDao) throws SQLException {
        this.stage = stage;
        this.rDao = rDao;

        this.parentcontroller = parentcontroller;

        final KeyCombination scReportGo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_DOWN);
        final KeyCombination scToday = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);
        final KeyCombination scPreviousDay = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN);
        final KeyCombination scNextDay = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN);

        dpDateIni.setValue(LocalDate.now());
        dpDateFim.setValue(LocalDate.now());

        stage.getScene().getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER && !event.isControlDown()) {
                //goButtonClick(null);
                //event.consume();
            } else {
                if (event.getCode() == KeyCode.ESCAPE) {
                    cancelButtonClick(null);
                } else {
                    if (scPreviousDay.match(event)) {
                        dpDateIni.setValue(dpDateIni.getValue().plusDays(-1));
                        dpDateFim.setValue(dpDateFim.getValue().plusDays(-1));
                        try {
                            goButtonClick(null);
                        } catch (SQLException ex) {
                            Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {
                        if (scNextDay.match(event)) {
                            dpDateIni.setValue(dpDateIni.getValue().plusDays(1));
                            dpDateFim.setValue(dpDateFim.getValue().plusDays(1));
                            try {
                                goButtonClick(null);
                            } catch (SQLException ex) {
                                Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        if (scReportGo.match(event)) {
                            try {
                                goButtonClick(null);
                            } catch (SQLException ex) {
                                Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            if (scToday.match(event)) {
                                dpDateIni.setValue(LocalDate.now());
                                dpDateFim.setValue(LocalDate.now());
                                try {
                                    goButtonClick(null);
                                } catch (SQLException ex) {
                                    Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }
                        }
                    }
                }

            }
        });

        tbReport.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER && !event.isControlDown()) {
                worksession ws;
                ws = (worksession) tbReport.getSelectionModel().getSelectedItem();
                parentcontroller.findAndSelectProjectByID(ws.getMasterid(), true);
                parentcontroller.findAndSelectTaskByID(ws.getTaskid(), true);
                parentcontroller.tbTasks.requestFocus();
                okButtonClick(null);
                event.consume();
            }
        });

        tbReport.requestFocus();
        
        //Colocando este listener por causa de um bug que nao atualiza o datepicker
        
        dpDateIni.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                dpDateIni.setValue(dpDateIni.getConverter().fromString(dpDateIni.getEditor().getText()));
            }
        });
        
        dpDateFim.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue){
                dpDateFim.setValue(dpDateFim.getConverter().fromString(dpDateFim.getEditor().getText()));
            }
        });
       
        
        
        goButtonClick(null);
    }

    private void addEventListeners() {
        tbSummary.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (tbSummary.getSelectionModel().getSelectedItem() != null) {
                try {
                    String rname = ((worksummary) tbSummary.getSelectionModel().getSelectedItem()).getRolename();
                    if (rname.equals("TOTAL") )
                    {
                        rname = "";
                    }
                    getReport(dpDateIni.getValue(), dpDateFim.getValue(),rname );
                } catch (SQLException ex) {
                    Logger.getLogger(WorkReportController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        };

    
    
    public worksession createWS(ResultSet rs) throws SQLException {
        return new worksession(
                rs.getString("ID"),
                rs.getInt("TASKID"),
                rs.getInt("MASTERID"),
                rs.getString("MNAME"),
                rs.getString("TNAME"),
                rs.getString("PDATE"),
                rs.getString("PWEEKDAY"),
                rs.getString("STARTED"),
                rs.getString("FINISHED"),
                rs.getString("MINUTES"),
                rs.getString("ROLE_NAME")
        );
    }
    
    public worksummary createWSU(ResultSet rs) throws SQLException {
        return new worksummary(
                rs.getString("ROLE_NAME"),
                rs.getInt("MINUTES"),
                rs.getInt("PRODUCTIVE")
        );
    }

    
    private void getReport(LocalDate ini, LocalDate fim, String roleName) throws SQLException {

        long totalminutes = 0;
        int totalminutesSu = 0;
        int totalminutesSuProd = 0;
        
        System.out.printf(ini.toString() + "    "  + fim.toString());
        
        ResultSet rs;
        rs = rDao.getallrecords(ini, fim);
//        if  (roleName.isEmpty()) {
//           
//        }else 
//        {
//         rs = rDao.getallrecords_role(ini, fim, roleName);
//        }

        ObservableList<worksession> tvReportData = FXCollections.observableArrayList();
        ObservableList<worksummary> tvReportDataSummary = FXCollections.observableArrayList();

        //MasterSetValueFactory();   
        worksession ws;
        boolean add = true;
        while (rs.next()) {
            
            if (!roleName.isEmpty()) {
                    add =rs.getString("ROLE_NAME").equals(roleName);
            }
            
            
            if (add) {
               ws = createWS(rs);    
               tvReportData.addAll(ws);
               totalminutes = totalminutes + Integer.valueOf(ws.getTotal());
               
            }
            //tvReportData.set
            
        }
        tbReport.setItems(tvReportData);
        

        rs = rDao.getRoleSummaryForInterval(ini, fim);
        
            worksummary wsu; //workSummary
        while (rs.next()) {
         wsu = createWSU(rs);
            tvReportDataSummary.addAll(wsu);
            totalminutesSu = totalminutesSu + Integer.valueOf(wsu.getHoras());
            
            if (wsu.getProdutivo() == 1) {
               totalminutesSuProd = totalminutesSuProd + Integer.valueOf(wsu.getHoras());
            }
        }
         wsu =  new worksummary("PRODUCTIVE", totalminutesSuProd,1 );         
         tvReportDataSummary.addAll(wsu);
         wsu =  new worksummary("TOTAL", totalminutesSu,1 );         
         tvReportDataSummary.addAll(wsu);        
         
                 
        tbSummary.setItems(tvReportDataSummary);
        tfTotal.setText(StringUtils.leftPad(Util.MinutesToHoursText(totalminutes), 5, "0"));
    }

    @SuppressWarnings("empty-statement")
    public void SetValueFactory() {
        int i;

        String[] variableNames = {"id", "project", "task", "date", "pweekday", "start", "finish", "totaltext","rolename"};

        i = 0;

        id.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        project.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        task.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        date.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        pweekday.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        started.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        finished.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        total.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        role.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
        
        
        String[] summaryVariableNames = {"rolename","totaltext"};

        i = 0;
        role_name.setCellValueFactory(new PropertyValueFactory<>(summaryVariableNames[i++]));
        total_summary.setCellValueFactory(new PropertyValueFactory<>(summaryVariableNames[i++]));

    }

    @FXML
    public void okButtonClick(ActionEvent event) {
//        getReport(dpDateIni.getValue(),dpDateFim.getValue() );
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();

        //parent.focusAndSelect(tbTasks);
    }

    @FXML
    public void goButtonClick(ActionEvent event) throws SQLException {
        getReport(dpDateIni.getValue(), dpDateFim.getValue(),"");
        tbReport.getSelectionModel().select(0);

        //Stage stage = (Stage) btOK.getScene().getWindow();
//        stage.hide();
        //parent.focusAndSelect(tbTasks);
    }

    @FXML
    public void cancelButtonClick(ActionEvent event) {

        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
    }

}

