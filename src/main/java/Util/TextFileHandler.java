/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

import Control.MainWindowController.fileSystemAccessMode;

import java.net.URL;


public class TextFileHandler {
  
  
  final static String FILE_NAME = "C:\\Temp\\input.txt";
  final static String OUTPUT_FILE_NAME = "C:\\Temp\\output.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;

  public static String newline = System.getProperty("line.separator");
  
  //For smaller files
  
  
  public void InsertLine (String line, String filename) throws IOException {
	  
	  List<String> lines_from_file =  new ArrayList<String>();
	  if (Files.exists(Paths.get(filename))) {
		  lines_from_file = Files.readAllLines(Paths.get(filename));
	  }
	  
	  int lineNo = 0;
	  for (String s : lines_from_file) {
		   lineNo += 1;  
		   if (s.toUpperCase().indexOf("#NEWENTRIESONTOP#") > -1) {
			   break;
		   } 		    	
	    }
	   
	  
	  lines_from_file.add(lineNo, line); // for example

	  Files.write(Paths.get(filename), lines_from_file, StandardCharsets.UTF_8); 
      
  }

    
    public void InsertLineAtTop (String line, String filename) {
	  //System.out.println("escrevendo status" + Util.getCurrentDateMinute());
	  File file = new File(filename);
	  String lines = null;
	  try {
                List<String> text = FileUtils.readLines(file);
                text.add(0, line);
		FileUtils.writeLines(file, text);
	} catch (IOException e) {
	}
  }

  boolean DirExist(String pPath){
	  File folderExisting = new File(pPath);	
	  return folderExisting.exists();
  }
  
  public static boolean renameFile(String oldfile, String newfile) throws IOException{
      if (!oldfile.isEmpty() & !newfile.isEmpty() ){
        System.out.print("Getting ready to rename. Both file name are not empty");
        File oldf = new File(oldfile);
        File newf = new File(newfile);
        
        File oldf_dir = oldf.getParentFile();
        File newf_dir = newf.getParentFile();
        
//        System.out.print("Old :" + oldfile);
//        System.out.print("New :" + newfile);
        
        //renaming path if necessary
        if (!oldf_dir.getPath().equals(newf_dir.getPath())){
           System.out.println("Path Rename is necessary. Paths are different");
           oldf_dir.renameTo(newf_dir);    
           //rebuilding file names
           oldf = new File(newf_dir.getPath() + "\\" + oldf.getName() );
           newf = new File(newf_dir.getPath() + "\\" + newf.getName());
        }
        
        
        return oldf.renameTo(newf);
        
      }
      return true;
      
  }
  
  public String readFile(String fileName) throws IOException {
      
      FileInputStream fileInputStream = new FileInputStream(fileName);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "ISO-8859-15");
              
      BufferedReader br = new BufferedReader(inputStreamReader);
       StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            sb.append("\n");
            line = br.readLine();
        }
        br.close();
        fileInputStream.close();
        inputStreamReader.close();
        return sb.toString();
        
    
}
  
  private String stringType(String command) {
	  if (!command.isEmpty()) {
	  	if (command.substring(0, 2).toUpperCase().equals("PY")) {
				return "Python";
	  	} else
	  	{ if (Util.isValidURL(command)) {
	  	    return "URL";
	  	  } else {
	  		if (TextFileHandler.isFile(command)) {
	  		   return "File";	
	  		} else
	  		{
	  		   return "Dir";
	  		}
	  		
	  	  }	
	  	}
	  }
	  else
	  {
		  return "None";
	  }
  }

  public String getPythonScript(String fileName) throws IOException {
	  for (int x = 1; x < 4; x++) { //For loop to eval 3 first lines of the file 
		  String bracketCommand = getBracketCommandFromFile(fileName, x); 
		   if (stringType(bracketCommand).equals("Python")){
			  return bracketCommand;
		   }
	  }
	  return "";
	  
  }
  

  
  public String getURLFromNotes(String fileName) throws IOException {
	  for (int x = 1; x < 4; x++) { //For loop to eval 3 first lines of the file 
		  String bracketCommand = getBracketCommandFromFile(fileName, x); 
		   if (stringType(bracketCommand).equals("URL")){
			  return bracketCommand;
		   }
	  }
	  return "";
  }
  
  

  
  public String locationFromFile(String fileName, fileSystemAccessMode accessMode) throws IOException {
	  
	  String file = "";
	  String hostDir= "";
	  String referenceDir = "";
	  String URL = "";
	  String pythonScript = "";
	  
	  for (int x = 4; x > 0; x--) { //For loop to eval 2 first lines of the file 
		    String bracketCommand = getBracketCommandFromFile(fileName, x);
		  
		   switch (stringType(bracketCommand )) {
		   		case "Dir":
		   			referenceDir = bracketCommand;
		   			break;
		   		case "File":
		   			file = bracketCommand;
		   			break;
		   		case "URL":
		   			URL = bracketCommand;
		   			break;
		   		case "Python":
		   			pythonScript =  bracketCommand;
		   			break;
		    }
	  }
	
	  if (!file.isEmpty()) {
		  hostDir = new File(file).getParent();
	  } else
	  {
		  file = referenceDir;
		  hostDir = referenceDir;
	  }
	  
	  if (referenceDir.isEmpty()) {
		  referenceDir = hostDir;
	  }

	  String response = "";
	  
	  switch (accessMode) {
	  	case FILE:
	  		response  = file;
	  		break;
	  	case FILE_HOSTDIR:
	  		response = hostDir;
	  		break;
	  	case URL:
	  		response = URL;
	  		break;
	  	case REFERENCEDIR:
	  	case RECENT_FILE:
	  		response = referenceDir;
	  		break;
	  }
	  return response;  
  }

  
public String getPathFromNotes(String fileName) throws IOException {
	  for (int x = 1; x < 4; x++) { //For loop to eval 2 first lines of the file 
		  String bracketCommand = getBracketCommandFromFile(fileName, x); 
		   if (stringType(bracketCommand).equals("Dir")){
			  return bracketCommand;
		   }
	  }
	  return "";
  }
  

public String getRowFromFile(String fileName, int lineNumber) throws IOException {
	String line = "";
		if (fileName.length() > 0) {
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-15"));
		    StringBuilder sb = new StringBuilder();
		    for (int row=0; row < lineNumber; row++) {
		    	line = br.readLine();	
		    }
		}
		return line;
}

  
  
  public String getBracketCommandFromFile(String fileName, int fromRow) throws IOException {
	   if (fileName.length() > 0) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-15"));
	    StringBuilder sb = new StringBuilder();

	    String line = ""; 
	    for (int row=0; row < fromRow; row++) {
	    	line = br.readLine();	
	    }
	        
	    if (line != null && !line.isEmpty()) {
	     if (Character.toString(line.charAt(0)).equals("[")) { //Verifies is first line of notes is a command -> Directory
	      for (int vCounter = 1; vCounter <= 256; vCounter++) {
	       if (Character.toString(line.charAt(vCounter)).equals("]")) {
	        return line.substring(1, vCounter);
	       }
	      }
	     }
	    }
	   }
	   return "";
	  }
 
 
  
  public String getPathFromNotes2(String fileName) throws IOException {
	   if (fileName.length() > 0) {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "ISO-8859-15"));
	    StringBuilder sb = new StringBuilder();
	    String line = br.readLine();
//	    System.out.print(line);
	    if (line != null && !line.isEmpty()) {
	     if (Character.toString(line.charAt(0)).equals("[")) { //Verifies is first line of notes is a command -> Directory
	      for (int vCounter = 1; vCounter <= 256; vCounter++) {
	       if (Character.toString(line.charAt(vCounter)).equals("]")) {
	        return line.substring(1, vCounter);
	       }
	      }
	     }
	    }
	   }
	   return "";
	  }
  
  
  public void writeFile (String fileName, String content ) throws IOException {
      Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                  new FileOutputStream(fileName), "ISO-8859-15"));
            writer.write(content);
        } catch (IOException ex) {
          // report
        } finally {
           try {writer.close();} catch (IOException ex) {}
        }
  }

  public static String GetNotesFile (String Mname, String Tname, String TID, boolean create ){
	    //String Mname = "Project_Tal"; //Master name
	    //String Tname = "01 - Fazer um levantamento das áreas que preciso ser mais organizado"; //Task name
	    //String TID   = "2"; //Task ID
	  String pFileName = "";
	  Tname = Tname.length()>40 ? Tname.substring(0, 40):Tname;
	  String DirName = "";
          String DirSeparator ="";
                  
      try {
          if ( Util.Read("Config.ini", "OS").equals("OSX"))
          {
            DirSeparator = "/";
          }
          else
          {
            DirSeparator = "\\";
          }                      
          DirName = Util.Read("Config.ini", "HOME_DIR") + DirSeparator + "Notes" + DirSeparator + Mname;          
      } catch (IOException ex) {
          Logger.getLogger(TextFileHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
          pFileName =  DirSeparator + String.format("%5s", String.valueOf(TID)).replace(' ', '0') +"_" + Tname + ".txt";
          if (create){
           DirName = Util.CreateDir(DirName );
           Util.CreateTextFile(DirName, pFileName);  
           }
          return (DirName + Util.LimpaFileName(pFileName)).trim(); 
        }
  
  
  
  
public static boolean DirExists(String directoryName)
{
  File theDir = new File(directoryName);
  return theDir.exists();
}

public static boolean isFile(String Name)
{
	
	
  if (Util.onWindows()) {
    return Files.isRegularFile(new File(Name).toPath());
  } else
  {
      return !Name.contains("/");
  }
  
  
}

  
} 



