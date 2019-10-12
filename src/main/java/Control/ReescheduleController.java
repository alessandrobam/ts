/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import DB.biteDao;
import DB.taskDao;
import Model.bite;
import Model.rescheduler;
import Model.task;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import static java.time.DayOfWeek.*;
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
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.lang3.text.WordUtils;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class ReescheduleController implements Initializable {

    /**
     * Initializes the controller class.
     */
//    @FXML ComboBox cbInterval;
//    @FXML ComboBox cbNext;
    @FXML
    DatePicker dpDate;
    @FXML
    Button btOK;
    @FXML
    Button btCancel;
    @FXML
    ListView lvNext;
    @FXML
    ListView lvInterval;

    @FXML
    Text tfDiaSemana;
    @FXML
    Text tfBitesCounts;

    private rescheduler r;
    private task t;
    private bite b;
    private taskDao tDao;
    private biteDao bDao;
    private Date biteortaskdate;
    
    
    private boolean dpControl; //parece que tem um bug no date picker que faz o evento keypress ser executado 2 vezes

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void comboNextItemSelected() {
        lvInterval.getSelectionModel().clearSelection();
        switch (lvNext.getSelectionModel().getSelectedItem().toString()) {
            case "Monday":
                dpDate.setValue(r.getNext(MONDAY));
                break;
            case "Tuesday":
                dpDate.setValue(r.getNext(TUESDAY));
                break;
            case "Wednesday":
                dpDate.setValue(r.getNext(WEDNESDAY));
                break;
            case "Thursday":
                dpDate.setValue(r.getNext(THURSDAY));
                break;
            case "Friday":
                dpDate.setValue(r.getNext(FRIDAY));
                break;
            case "Saturday":
                dpDate.setValue(r.getNext(SATURDAY));
                break;
            case "Sunday":
                dpDate.setValue(r.getNext(SUNDAY));
                break;
        }
    }

    @FXML
    private void comboIntervalItemSelected() {
        lvNext.getSelectionModel().clearSelection();
        dpDate.setValue(r.getInterval(lvInterval.getSelectionModel().getSelectedItem().toString()));
    }

    public void setStageAndSetupListeners(task pt, bite pb,taskDao ptDao, biteDao btDao) {
        t = pt;
        b = pb;
        r = new rescheduler();
        tDao = ptDao;
        bDao = btDao;
        dpControl = false;

        if (t!=null) {
           biteortaskdate = t.getDeadline();
           
        } else
        {
           biteortaskdate = b.getDeadline();
        }
        r.setOriginalDate( biteortaskdate.toLocalDate());
        
        btOK.setDefaultButton(true);
        btCancel.setCancelButton(true);

        ObservableList<String> listinterval = FXCollections.observableArrayList(
                "Today",
                "Tomorrow",
                "In a Week",
                "In two Weeks",
                "In a Month",
                "In a Quater",
                "End of the Month");
        lvInterval.setItems(listinterval);

        ObservableList<String> listnext = FXCollections.observableArrayList(
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday");
        lvNext.setItems(listnext);

        lvInterval.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String NewValue) {
                if (NewValue != null) {
                    comboIntervalItemSelected();
                }
            }
        });

        lvNext.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String NewValue) {
                if (NewValue != null) {
                    comboNextItemSelected();
                }
            }
        });
        
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.getScene().getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            try {
                if (event.getCode() == KeyCode.ENTER) {
                    okButtonClick(null);
                    event.consume();
                    
                } else {
                    if (event.getCode() == KeyCode.SPACE) {
                        todayOrTomorrow();
                    } else {
                        if (event.getCode() == KeyCode.ESCAPE) {
                            cancelButtonClick(null);
                        }                        
                        
                    }                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(ReescheduleController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        lvInterval.requestFocus();

        dpDate.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (dpDate.getValue() != null && !dpDate.isShowing()) {
                if (event.getCode() == KeyCode.UP) {
                    if (dpControl) {
                        dpDate.setValue(dpDate.getValue().plusDays(1));
                        event.consume();
                        dpControl = false;
                    } else {
                        dpControl = true;
                    }
                } else {
                    if (event.getCode() == KeyCode.DOWN) {
                        if (dpControl) {
                            dpDate.setValue(dpDate.getValue().plusDays(-1));
//                            System.out.println("Minus 1");
                            event.consume();
                            dpControl = false;
                        } else {
                            dpControl = true;
                        }
                    }
                }

            }
        });

        dpDate.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue ov, LocalDate t, LocalDate NewValue) {
                if (NewValue != null) {
                    try {
                        tfDiaSemana.setText(WordUtils.capitalize(NewValue.getDayOfWeek().toString().toLowerCase()));
                        tfBitesCounts.setText("Bite count: " + String.valueOf(bDao.getCountForDay(NewValue)));
                    } catch (SQLException ex) {
                        Logger.getLogger(ReescheduleController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
       todayOrTomorrow();

    }

    private void todayOrTomorrow() {
        if (biteortaskdate!= null) {
            if (biteortaskdate.compareTo(Date.valueOf(LocalDate.now()))<0) {
                lvInterval.getSelectionModel().select(0); //today
            } else {
                lvInterval.getSelectionModel().select(1);
            }
            lvInterval.requestFocus();
        }
    }

    
    @FXML
    public void okButtonClick(ActionEvent event) throws SQLException {
        
        //verifying whether a bite or a task is being reschedule
        
        if (t != null) {
            t.setDeadline(Date.valueOf(dpDate.getValue()));
            //t.incReschedule();
            tDao.persist(t);
        } else {
            if (b != null) {
            	
            	
            	b.incReschedule(b.getDeadline().toLocalDate(), dpDate.getValue());
            	b.setDeadline(Date.valueOf(dpDate.getValue()));
                
                bDao.persist(b);
            }
        }
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
        //parent.focusAndSelect(tbTasks);
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
