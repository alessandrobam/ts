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
import java.net.URL;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Alessandro
 */
public class Util {

	public static final String color_WAITING = "#F04646";
	public static final String color_WORKING = "#FFED63";
	public static final String color_DONE = "#6699ff";
	public static final int opINSERT = 1;
	public static final int opUPDATE = 2;

	public static void openURL(String url) throws IOException, URISyntaxException {
		Desktop d = Desktop.getDesktop();
		d.browse(new URI(url));
	}

	public static boolean isValidURL(String urlString) {

		final String regex = "[(\\/)]";
		final String string = urlString;

		final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		final Matcher matcher = pattern.matcher(string);

		return matcher.find();

	}

	static public String DateToText(Date pDate, String format) // deprecated
	{

		if (pDate != null) {
			SimpleDateFormat dateformat;
			dateformat = new SimpleDateFormat(format);
			return dateformat.format(pDate);
		} else {
			return "";
		}
	}

	static public java.sql.Date sqlDate(LocalDate date) {
		return java.sql.Date.valueOf(date);
	}

	static public boolean onWindows() {
		try {
			return !(Util.Read("Config.ini", "OS").equals("OSX"));
		} catch (IOException ex) {
			Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
		}
		return false;
	}

	public static void AlertMessagebox(String msg) {
		AlertMessagebox(msg, "");
	}

	public static void AlertMessagebox(String msg, String details) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(msg);
		alert.setContentText(details);
		alert.showAndWait().ifPresent(rs -> {
			if (rs == ButtonType.OK) {
				System.out.println("Pressed OK.");
			}
		});
	}

	
	public static void openFile(String pPath) throws IOException {
	
		File file = new File(pPath);
		Desktop desktop = Desktop.getDesktop();
		desktop.open(file);
	}
	
	public static void OpenDir(String pPath) throws IOException {

		
		//2020m05 - Replacing double slashes with one
		pPath = pPath.replace("\\\\", "\\");
		
		
		
		
		File file = new File(pPath);
		Path path = Paths.get(pPath);
		
		
		if (Files.exists(path)) {

			if (file.isFile()) {
				openFile(pPath);
			} else {
				//desktop.open(new File(pPath));
				Runtime.getRuntime().exec("explorer.exe " + "\"" + pPath + "\"");
				Util.clipIt(pPath);
			}

		} else {
			Util.AlertMessagebox("File or Directory does not exist", pPath);
		}
	}

	public static String removeAccents(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFKD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str;
	}

	public static Optional<ButtonType> YesNoDialogbox(String header, String confmsg) {

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

		alert.setTitle(header);
		alert.setContentText(confmsg);

		return alert.showAndWait();

//      Action response = Dialogs.create()
//      .title(header)
//      .message(confmsg)
//      .showConfirm();
//      
//       return response;
	}

	public static String InputText(String title, String head, String msg, String inputText) {
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

		alert.setTitle(title);
		// alert.setResult(inputText);
		alert.setHeaderText(head);

		Optional<String> result = alert.showAndWait();

		if (result.isPresent()) {
			return result.get();
		}
		return "";
	}

	public static void logStatusChange(String status, String message, String filename) {
		logStatusChange(status, message, filename, true);
	}

	public static void logStatusChange(String status, String message, String filename, boolean add_time_stamp) {

		// 2014m10 - Alessandro - Decidi que não atualizar o status as notas
		// ficam bem mais clean.

		status = status.length() > 0 ? " - [" + status + "] - " : "";

		TextFileHandler file = new TextFileHandler();
		java.util.Date a = new java.util.Date();

		String statusMessage;

		if (!message.isEmpty() && message != null) {
			statusMessage = status + message;
		} else {
			statusMessage = status;
		}

		if (add_time_stamp) {
			statusMessage = Util.DateToText(a, "dd MMM yyyy - EEE - HH:mm - ") + statusMessage;
		}

		try {
			file.InsertLine("\n" + statusMessage, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String CreateDir(String pPath) {
		Path path = Paths.get(pPath);
		boolean isDir = Files.isDirectory(path);
		boolean success = true;

		if (!isDir) {
			// Creating the path if NOT under a BOX directory tree
			if (pPath.toUpperCase().contains("BOX")) {
				AlertMessagebox("Can't create a directory on a BOX directory tree", pPath);
				success = false;
			} else {
				if (pPath.contains(".")) {
					AlertMessagebox("Files cannot be created with this option, only directories", pPath);
					success = false;
				} else {
					success = (new File(pPath)).mkdirs();
				}
			}
		}
		return success ? pPath : "";
	}

	public static void CreateTextFile(String pDir, String pFileName) {
		File file = new File(pDir, pFileName);

		try {
			file.createNewFile();
		} catch (IOException e) {
		}
	}

	public static String Read(String pFilename, String pChave) throws IOException {
		Properties props = new Properties();
		FileInputStream fileIn = new FileInputStream(pFilename);
		props.load(fileIn);

//        System.out.println(System.getProperty("user.dir"));
		return props.getProperty(pChave);
	}

	
	public static void clipIt (String text) {
		
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(text);
		clipboard.setContent(content);
	}
	public static String getAppPath() {
		try {
			return new java.io.File(".").getCanonicalPath();
		} catch (IOException e) {
		}
		return null;
	}

	public static String MinutesToHoursText(long pMinutes) {
		int Hour;
		int Min;
		Hour = (int) (pMinutes / 60);
		return String.valueOf(Hour) + ":" + String.format("%2s", String.valueOf((pMinutes % 60))).replace(' ', '0');
	}

	public static String LimpaFileName(String fname) {
		if (Util.onWindows()) {
			fname = fname.replace("/", "_");
		} else {
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

	public static LocalDate weekStart(LocalDate dateReference) {
		return dateReference.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)).plusDays(-6);
	}

	public static LocalDate weekEnd(LocalDate dateReference) {
		return dateReference.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
	}

	public static Timestamp getCurrentDateMinute() throws ParseException {
		SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		date = date_format.parse(date_format.format(new Date()));
		return new Timestamp(date.getTime());
	}

	public static long diffInMinutes(Timestamp t1, Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / 60000;
	}

	public static java.sql.Date DateToSQLDate(java.util.Date d) {
		return new java.sql.Date(d.getTime());
	}
}