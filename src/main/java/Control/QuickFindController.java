/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.task;
import Model.todoitem;
import Util.Util;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alessandro
 */
public class QuickFindController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    public TextField textFind;
    @FXML
    private Button btOK;
    @FXML
    private Button btCancel;

    private int searchStart = 0;
    private boolean lastSeachSuccessful = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }

    public void setStageAndSetupListeners(TableView tb, String st) {
        searchStart = 0;
        lastSeachSuccessful = false;

        textFind.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            findAndSelectTaskByID(tb, newValue);
        });
        

       // Util.YesNoDialogbox(textFind.getSelectedText(), st);
        //btCancel.setCancelButton(true);
        //btOK.setDefaultButton(true);
        final KeyCombination scFindNext = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);

        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.getScene().getWindow().getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (scFindNext.match(event)) {
                //okButtonClick(null);
                if (!lastSeachSuccessful) {
                    searchStart = 0;
                } else {
                    searchStart = tb.getSelectionModel().getFocusedIndex() + 1;
                }
                findAndSelectTaskByID(tb, textFind.getText());
                event.consume();
            } else {
                if (event.getCode() == KeyCode.ESCAPE || event.getCode() == KeyCode.ENTER) {
                    cancelButtonClick(null);
                }
            }
        });
        //textFind.setText(st);


    }

    private String findAndSelectTaskByID(TableView tb, String name) {

    	String dataText; 	
        for (int x = searchStart; x <= tb.getItems().size() - 1; x++) {

            todoitem t = (todoitem) tb.getItems().get(x);
            
            
            dataText = t.getName();
            if (t instanceof task) {
            	dataText = t.getName().concat(((task)t).getTag());
              }
            
            
            
            if (Util.removeAccents(dataText).toUpperCase().contains(Util.removeAccents(name).toUpperCase())) {
                tb.getSelectionModel().clearAndSelect(x);
                tb.scrollTo(x);
                lastSeachSuccessful = true;
                return t.getName();
            }
        }
        //Util.AlertMessagebox("Bottom Reached, ");
        lastSeachSuccessful = false;
        return "";
    }

    @FXML
    public void okButtonClick(ActionEvent event) {
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
        //parent.focusAndSelect(tbTasks);
    }

    @FXML
    public void cancelButtonClick(ActionEvent event) {
        Stage stage = (Stage) btOK.getScene().getWindow();
        stage.hide();
        
        

    }

}
