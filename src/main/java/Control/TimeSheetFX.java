/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Control;

import java.sql.Connection;

import DB.masterDao;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Alessandro
 */
public class TimeSheetFX extends Application {

	Connection conn;
	masterDao mDao;

	public TimeSheetFX() {

	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));

		Parent root = (Parent) loader.load();

		MainWindowController controller = (MainWindowController) loader.getController();
		// Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		// Scene scene = new Scene(root, screenBounds.getWidth(),
		// screenBounds.getHeight());

		Scene scene = new Scene(root);
		stage.setMaximized(true);
		scene.getStylesheets().add("test.css");
		stage.setScene(scene);
		stage.setTitle(Util.Util.Read("Config.ini", "TITLE"));

		System.out.println("TILE É: " + stage.getTitle());

		controller.setStageAndSetupListeners(stage);

		stage.show();

		controller.tbMasterTasks.requestFocus();
		// stage.setOnCloseRequest(closeEvent -> {
		// controller.timer.cancel();
		//
		// });
	}

	/**
	 * @param args
	 *            the command line arguments
	 */

}
