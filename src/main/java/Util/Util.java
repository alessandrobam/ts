/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
//import org.controlsfx.control.action.Action;
//import org.controlsfx.dialog.Dialogs;
//import org.controlsfx.control.action.Action;
//import org.controlsfx.dialog.Dialogs;
//import org.controlsfx.control.action.Action;
//import org.controlsfx.dialog.Dialogs;



/**
 *
 * @author Alessandro
 */
public class Util {
        
        public static final String color_WAITING = "#F04646";
        public static final String color_WORKING = "#FFED63";
        public static final String color_DONE =    "#6699ff";
        public static final int opINSERT = 1;
        public static final int opUPDATE = 2;
        
        
static public String DateToText(Date pDate, String format) //deprecated
	{
    
		if (pDate !=null){
	     	SimpleDateFormat dateformat;
	     	dateformat = new SimpleDateFormat(format);
		    return dateformat.format(pDate);
		} else
		{
			return "";
		}
	}
        

static public boolean onWindows()
{
            try {
                return  !( Util.Read("Config.ini", "OS").equals("OSX"));
                } catch (IOException ex) {
                Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
}
public static void AlertMessagebox(String msg) {
      
	//Rewrite afterwards
	
	   //Action response = Dialogs.create()
      //.title(msg)                                      dialogs
      //.masthead(msg)
      //.message( msg)
      //.showConfirm();
}

public static void OpenDir(String pPath) throws IOException {
		
                File file = new File (pPath);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
                
	}
    

public static String removeAccents(String str) {  
   str = Normalizer.normalize(str, Normalizer.Form.NFKD);  
   str = str.replaceAll("[^\\p{ASCII}]", "");    
   return str;  
} 

public static Optional<ButtonType> YesNoDialogbox(String header, String confmsg) {
 
Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     
    alert.setTitle (header);
    alert.setContentText(confmsg);
     
    return alert.showAndWait();
     
    
    
//      Action response = Dialogs.create()
//      .title(header)
//      .message(confmsg)
//      .showConfirm();
//      
//       return response;
}

public static String InputText    (String title, String head, String msg, String inputText) {
//            Optional<String> response;
//            response = Dialogs.create()
//                    .title(title)
//                    .masthead(head)
//                    .message(msg)
//                    .showTextInput(inputText);
//            return response.get();
//            
            
    String response;
    TextInputDialog alert = new TextInputDialog(inputText);
     
     
    alert.setTitle (title);
    //alert.setResult(inputText);
    alert.setHeaderText(head);
             
    Optional<String> result = alert.showAndWait();
     
    if (result.isPresent()){
        return result.get();
    }
    return "";
}


public static String CreateDir(String pPath)
	{
		boolean success = (new File(pPath)).mkdirs();
		if (!success) {
		    System.out.println("directory failed");
		}
		return pPath;
	}
	
public static void CreateTextFile(String pDir, String pFileName)
	{
		File file = new File(pDir, pFileName );
		
		try {
			file.createNewFile();
		} catch (IOException e) {
		}
	}
	    

public static String Read(String pFilename, String pChave) throws IOException{
	Properties props = new Properties();
        FileInputStream fileIn = new FileInputStream( pFilename);
        props.load(fileIn);
        
        System.out.println(System.getProperty("user.dir"));
        return props.getProperty(pChave);
	}	    
    
public static String getAppPath() {
    	 try {
			return new java.io.File( "." ).getCanonicalPath();
		} catch (IOException e) {
		}
		return null;
    }
    
 public static String MinutesToHoursText (long pMinutes){
       	int Hour;
       	int Min;
       	Hour = (int) (pMinutes / 60);
       	return String.valueOf(Hour)
       			+ ":" +
       			String.format("%2s", String.valueOf((pMinutes%60))).replace(' ', '0');
    }
 
public static String LimpaFileName(String fname)
    {
    	if (Util.onWindows()) {
           fname = fname.replace("/", "_");    
        } else
        {
          fname = fname.replace("\"", "_");
        }

        fname = fname.replace(":", "_");
        fname = fname.replace("*", "_");
        fname = fname.replace("?", "_");
        fname = fname.replace(">", "_");
    	fname = fname.replace("<", "_");
    	fname = fname.replace("|", "_");
        
        fname = removeAccents(fname);
    	return fname;
    }
     
 public static Timestamp getCurrentDateMinute() throws ParseException 
    {
    	SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    	Date date = new Date();
        date = date_format.parse(date_format.format(new Date()));
    	return new Timestamp(date.getTime());
    }
    
 public static long diffInMinutes(Timestamp t1, Timestamp t2){
     return  (t2.getTime() - t1.getTime())/60000;
 }
 
 public static java.sql.Date  DateToSQLDate (java.util.Date d){
    return new java.sql.Date(d.getTime());
 }
}