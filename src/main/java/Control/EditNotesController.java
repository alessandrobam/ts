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

import Model.task;
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
    
	public enum landingPosition { TOPICS, TIMELINE};
    
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
    
    
	String meetingFrame = "\r\n" + "\r\n" + "Participants:\r\n" + "	- \r\n" + "\r\n" + "Key Topics Discussed\r\n"
			+ "	- \r\n" + "\r\n" + "Actions\r\n" + "	- \r\n" + "	- \r\n" + "\r\n" + "Key Takeaways\r\n" + "	- \r\n"
			+ "\r\n" + "";
	
	
    public void setStageAndSetupListeners(Stage stage, String taskName, int taskStatus, String biteName  )  {
    	
    	
         edPath.setText(pFilename);
        
         stage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
        	 
        	 if (e.isShortcutDown() | e.getCode()==KeyCode.ESCAPE ) { //control is pressed
        		 
            	 switch (e.getCode()) {
            		case ENTER:
            			try {
                            btSaveClick(null);
                            stage.close();
                         } catch (IOException ex) {
                            Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
                         }
            			break;

            		case ESCAPE:
            			stage.close();
            			break;
            			
            		case E: 
              			  stage.close();
	                      try {
	                          btEditor(null);
	                      } catch (IOException ex) {
	                          Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
	                      }
            			
            			
            			break;
            		case F:
            			edNota.insertText(edNota.getCaretPosition(),  meetingFrame);
     					break;
     			
            		case Q:
            			edNota.insertText(edNota.getCaretPosition(),   "#NEWENTRIESONTOP#" );
            			
     					break;
     					
            		case M:
            			edNota.insertText(edNota.getCaretPosition(),   "NEXT MEETING TOPICS");
            			
     					break;

            		case B:
            			edNota.insertText(edNota.getCaretPosition(),   biteName);
            			
            			break;
     					
            		case P:
            			
            			edNota.insertText(edNota.getCaretPosition(),   taskName);
            			break;
            			
            		case T:
            			edNota.insertText(edNota.getCaretPosition(),   Util.DateToText(new java.util.Date(), "dd MMM yyyy - EEE - HH:mm")+ " - " );
     					break;
     					
            		case L:
            			edNota.insertText(edNota.getCaretPosition(),   "----------------------------------tasks below are on ascending order by date of creation -----------------------");
     					break;
            	 }
        	 }
         	    
         });
         
        TextFileHandler handler = new TextFileHandler();
        try {
            edNota.textProperty().setValue(handler.readFile(this.getpFilename()));
        } catch (IOException ex) {
            Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        java.util.Date a = new java.util.Date();
        
        /*
        May 10th, 2020 - if the text #NEWENTRIESONTOP# is found, new log entries will added immediately after it. If not found, entries will be 
        inserted to the bottom of the file.  
 	
       */
        
        String goTopics = "NEXT MEETING TOPICS";
        String goTimeline = "#NEWENTRIESONTOP#";
        
        int goTopics_pos = findStringPos(edNota.getText(), goTopics);
  	    int goTimeline_pos = findStringPos(edNota.getText(), goTimeline);
  	    
  	    
  	    boolean isMeeting = taskName.toUpperCase().indexOf("MEETING") > -1 ;
  	    boolean isRunning = taskStatus == task.stWORKING;
  	    
  	  int position = goTimeline_pos;
  	    
  	    if (isMeeting) {
  	    	if (!isRunning & goTopics_pos > 0 ) {
  	    		position = goTopics_pos;
  	    	}
  	    }
  	    
  	    
  	  edNota.positionCaret( position);
  	  if (position > 0) {
  		  edNota.insertText(position, "");
  	  }
  	  
        
//        
//        int position = 0;
//        switch (PositionStyle) {
//          case NEXTMEETING:
//        	  position = findStringPos(edNota.getText(),"NEXT MEETING TOPICS");
//        	  
//
//        	  break;
//
//          case TIMELINE:
//        	  position = findStringPos(edNota.getText(),"#NEWENTRIESONTOP#");
//
//        	  break;
//          
//          case FIRST:
//        	  
//        	  int positionMeeting = findStringPos(edNota.getText(),"NEXT MEETING TOPICS");
//        	  int positionTimeline = findStringPos(edNota.getText(),"#NEWENTRIESONTOP#");
//        	  
//        	  position  = Math.min(positionMeeting, positionTimeline);
//        	  if (position==0  ) {
//        		  position = Math.max(positionMeeting, positionTimeline);
//        	  } 
//        	  
//        	  break;  
//        	  
//        }

//        edNota.positionCaret( position);
//        if (position > 0) {
//        	edNota.insertText(position, "\n");
//        } else
//        {
//        	
//        }
        
    }
    
    
    private int findStringPos(String text, String searchingString) {

    	
    	int position = text.toUpperCase().indexOf(searchingString) ;
    	if (position >-1) {
    		position = position + searchingString.length();
    	} else
    	{
    		position = 0;
    	}
    	return position;
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
//        String cmd = "explorer " + "\"" + fileName + "\"" ;
//        Runtime.getRuntime().exec(cmd);
        
        Util.openFile(fileName);
   
	
    }
    
}
