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
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;


public class TextFileHandler {
  
  
  final static String FILE_NAME = "C:\\Temp\\input.txt";
  final static String OUTPUT_FILE_NAME = "C:\\Temp\\output.txt";
  final static Charset ENCODING = StandardCharsets.UTF_8;

  public static String newline = System.getProperty("line.separator");
  
  //For smaller files
  
  
  public void InsertLine (String line, String filename) {
	  //System.out.println("escrevendo status" + Util.getCurrentDateMinute());
	  File file = new File(filename);
	  String lines = null;
	  try {
		FileUtils.write(file, line, true);
	} catch (IOException e) {
	}
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
  
  public static void renameFile(String oldfile, String newfile) throws IOException{
      if (!oldfile.isEmpty() && !newfile.isEmpty() ){
        System.out.print("Getting ready to rename. Both file name are not empty");
        File oldf = new File(oldfile);
        File newf = new File(newfile);
        
        File oldf_dir = oldf.getParentFile();
        File newf_dir = newf.getParentFile();
        
        System.out.print("Old :" + oldfile);
        System.out.print("New :" + newfile);
        
        //renaming path if necessary
        if (!oldf_dir.getPath().equals(newf_dir.getPath())){
           System.out.println("Path Rename is necessary. Paths are different");
           oldf_dir.renameTo(newf_dir);    
           //rebuilding file names
           oldf = new File(newf_dir.getPath() + "\\" + oldf.getName() );
           newf = new File(newf_dir.getPath() + "\\" + newf.getName());
        }
        if (oldf.renameTo(newf))     {
            System.out.println("Rename worked");
        }else
        {
            System.out.println("Rename failed");
            //Util.AlertMessagebox("Note renaming failed, please do it manually");
            //Util.OpenDir(oldf_dir.getPath());
        }
        
      }
      
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
  
  private String pythonOrPath(String command) {
	if (!command.isEmpty()) {
	  	if (command.substring(0, 2).toUpperCase().equals("PY")) {
				return "Python";
	  	} else
	  		return "Dir";
	}else
		return "None";
  }

  public String getPythonScript(String fileName) throws IOException {
	  
	  String bracketCommand = getBracketCommandFromFile(fileName, 1); //python commands are expected only on line 1
	  if (pythonOrPath(bracketCommand).equals("Python")){
		  return bracketCommand;
	  }else {
		  return "";
	  }
  }
  
  
public String getPathFromNotes(String fileName) throws IOException {
	  for (int x = 1; x < 3; x++) { //For loop to eval 2 first lines of the file 
		  String bracketCommand = getBracketCommandFromFile(fileName, x); 
		   if (pythonOrPath(bracketCommand).equals("Dir")){
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
    return !Name.contains("\\");
  } else
  {
      return !Name.contains("/");
  }
}

  
} 



