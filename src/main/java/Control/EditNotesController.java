/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import Util.TextFileHandler;
import Util.Util;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
public class EditNotesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField edPath;
    @FXML Button btEditor;
    @FXML Button btCancel;
    @FXML Button btOK;
    @FXML TextArea edNota;
    
    private String pFilename;

    public String getpFilename() {
        return pFilename;
    }

    public void setpFilename(String pFilename) {
        this.pFilename = pFilename;
    }
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    
    
    
    public void setStageAndSetupListeners(Stage stage)  {
         final KeyCombination scSave = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_DOWN);
         final KeyCombination scEditor = new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN);
         
         
         edPath.setText(pFilename);
        
         stage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
             if (scSave.match(e)) {
                 try {
                     btSaveClick(null);
                     stage.close();
                 } catch (IOException ex) {
                     Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             else
              if (e.getCode()==KeyCode.ESCAPE) {
                  stage.close();
              }
              else
               {
                  if (scEditor.match(e)) {
                      stage.close();
                      try {
                          btEditor(null);
                      } catch (IOException ex) {
                          Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  };  
               };
         });
         
        TextFileHandler handler = new TextFileHandler();
        try {
            edNota.textProperty().setValue(handler.readFile(this.getpFilename()));
        } catch (IOException ex) {
            Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        java.util.Date a = new java.util.Date();
        
        
//        edNota.appendText( Util.DateToText(a, "HH:mm dd/MM/yyyy - u")+ " - ");
        edNota.appendText( Util.DateToText(a, "d MMM yyyy - EEE - HH:mm")+ " - ");
        
        
        
        
        edNota.positionCaret( edNota.getLength());
    }
    
    private void saveToFile() throws IOException
    {
       TextFileHandler handler = new TextFileHandler();
       handler.writeFile(this.getpFilename(), edNota.getText());
    }
    
     @FXML
    public void btSaveClick(ActionEvent event) throws IOException  {
       saveToFile();
       Stage stage = (Stage) btOK.getScene().getWindow();
       stage.close();
    }
    
    @FXML
    private void btCloseClick(ActionEvent event) throws IOException  {
       //saveToFile(); 
       Stage stage = (Stage) btOK.getScene().getWindow();
       stage.close();
    }
    
    @FXML
    private void btEditor(ActionEvent event) throws IOException  {
       saveToFile();
       openEditor(edPath.getText());
       
       
    }
    
    @FXML
    private void onTextAreaTab(KeyEvent event)   {
       /*if (event.getCode()== KeyCode.TAB) {
           btOK.requestFocus();
       } */
               
    }

    private void openEditor(String fileName) throws IOException
    {
        String cmd = "explorer " + "\"" + fileName + "\"" ;
        Runtime.getRuntime().exec(cmd);
	
    }
    
}
