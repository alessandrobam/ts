/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Control;

import DB.DataBaseConnection;
import DB.GenericDao.dbresult;
import DB.biteDao;
import DB.biteDao.Sources;
import DB.catDao;
import DB.globalSearchDao;
import DB.masterDao;
import DB.milestoneDao;
import DB.reportDao;
import DB.roleDao;
import DB.taskDao;
import DB.waitDao;
import Model.bite;
import Model.master;
import Model.milestones;
import Model.task;
import Model.todoitem;
import Model.worksession;
import Util.TextFileHandler;
import Util.Util;
import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.Fidelity;
import javax.swing.text.Utilities;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;

import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
//import org.controlsfx.dialog.Dialog;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author Alessandro
 */
public class MainWindowController implements Initializable {

	public masterDao mDao;
	public milestoneDao miDao;

	public taskDao tDao;
	public biteDao bDao;
	public waitDao wDao;
	public catDao cDao;
	public reportDao rDao;
	public roleDao roDao;
	public globalSearchDao gsDao;
//	SimpleStringProperty statusBar;
	private final StringProperty statusText = new SimpleStringProperty("");
	// public Timer timer = new java.util.Timer();

	private int goToRunningSearchStart = 0;
	private long lastBitePrioritization;
	
	private long lastLevelJump;
	private int currentLevel;
	

	// 2014m11 - vCurrWeekPartialOn diz se o filtro de semana parcial esta ativo
	private boolean vCurrWeekPartialOn = false;
	
	// 2020m01 - vCurrMilestoneFilter diz qual filtro esta ativo. 
	private int vCurrMilestoneFilterLevel = 0;

	// 2014m11 - Quando iniciar uma tarefa, todas as outras tarefas rodando vão
	// ser colocadas em Wait
	ArrayList<master> runningMaster = new ArrayList<master>();
	ArrayList<task> runningTask = new ArrayList<task>();
	
	// 2020m01 - Saving database query results to allow for in screen filtering
	
	ObservableList<bite> tvBiteData, tvMilestoneData, tvObservable ;
	ObservableList<worksession> tvWorkSessionData; 
	ObservableList<master> tvMasterData;
	ObservableList<task> tvTaskData;
	
	ObservableList<bite> tvBiteData_tb_items; //observable list dedicate to loading table items.
	ObservableList<bite> tvMilesData_tb_items; //observable list dedicate to loading table items.
	
	
	
	
	
	
	

	public boolean deleting = false;
	final SimpleBooleanProperty doFocus = new SimpleBooleanProperty(false);

	@FXML
	public Tab taskPane;
	@FXML
	public TabPane tabPane;

	@FXML
	public TableView tbMasterTasks;

	@FXML
	private TableColumn<master, String> master_sign;
	@FXML
	private TableColumn<master, String> master_projectCol;
	@FXML
	private TableColumn<master, String> master_categoryCol;

	@FXML
	private TableColumn<master, String> master_roleCol;

	@FXML
	private TableColumn<master, String> master_lupCol;
	@FXML
	private TableColumn<master, String> master_cdCol;
	@FXML
	private TableColumn<master, String> master_idCol;
	@FXML
	private TableColumn<master, String> master_percdoneCol;
	@FXML
	private TableColumn<master, String> master_openCol;
	@FXML
	private TableColumn<master, String> master_workingCol;
	@FXML
	private TableColumn<master, String> master_waitingCol;
	@FXML
	private TableColumn<master, String> master_statusCol;

	@FXML
	public TableView tbTasks;

	@FXML
	private TableColumn<task, String> task_golden;

	@FXML
	private TableColumn<task, String> task_marked;
	@FXML
	private TableColumn<task, String> task_nameCol;
	@FXML
	private TableColumn<task, String> task_tagCol;
	@FXML
	private TableColumn<task, String> task_deadlineCol;
	@FXML
	private TableColumn<task, String> task_tooverdueCol;
	@FXML
	private TableColumn<task, String> task_startCol;
	@FXML
	private TableColumn<task, String> task_finishCol;
	@FXML
	private TableColumn<task, String> task_waitingCol;
	@FXML
	private TableColumn<task, String> task_statusCol;

	@FXML
	public TableView tbWs;

	@FXML
	private TableColumn<worksession, String> ws_idCol;
	@FXML
	private TableColumn<worksession, String> ws_dateCol;

	@FXML
	private TableColumn<worksession, String> ws_dowCol;

	@FXML
	private TableColumn<worksession, String> ws_startCol;

	@FXML
	private TableColumn<worksession, String> ws_finishCol;

	@FXML
	private TableColumn<worksession, String> ws_totalCol;

	@FXML
	TableView tbBites;

	@FXML
	private TableColumn<bite, String> bite_statusCol;

	@FXML
	private TableColumn<bite, String> bite_deadlineCol;
	@FXML
	private TableColumn<bite, String> bite_weekdayCol;
	@FXML
	private TableColumn<bite, String> bite_nameCol;
	@FXML
	private TableColumn<bite, String> bite_CDCol;
	@FXML
	private TableColumn<bite, String> bite_GoldenCol;

	@FXML
	private TableColumn<bite, String> bite_nextCol;

	public enum BITE_FILTER_OPTION {

		ALL, TASK_SPECIFIC, MASTER_SPECIFIC, INTERVAL, TASK_SPECIFIC_LATE, MASTER_SPECIFIC_LATE
	};

	@FXML
	public TableView tbMiles;

	@FXML
	private TableColumn<bite, String> deliverable_statusCol;
	@FXML
	private TableColumn<bite, String> deliverable_deadlineCol;
	@FXML
	private TableColumn<bite, String> deliverable_weekdayCol;
	@FXML
	private TableColumn<bite, String> deliverable_nameCol;
	@FXML
	private TableColumn<bite, String> deliverable_CDCol;
	@FXML
	private TableColumn<bite, String> deliverable_GoldenCol;

	@FXML
	private TableColumn<bite, String> deliverable_nextCol;

	private LocalDate bitesStart;
	private LocalDate bitesFinish;

	@FXML
	private AnchorPane apTasks;

	@FXML
	private AnchorPane apMilestones;

	@FXML
	private SplitPane spPaneMiles;

	@FXML
	private SplitPane spPane;

	@FXML
	private SplitPane spPaneVertical;

	@FXML
	private Pane spPaneOpen;

	@FXML
	private Pane spPaneWorking;

	@FXML
	private Pane spPaneWaiting;

	@FXML
	private Text tfBiteFilter;

	@FXML
	private Text textStatus;

	@FXML
	private Text textStatusOpen;

	@FXML
	private Text textStatusWaiting;

	@FXML
	private Text textStatusWorking;

	@FXML
	private Text textTotalTaskCountAndMarked;

	@FXML
	private TextField BiteListed;

	int taskVisible; // 1 all master, 2 half, 3 all task
	int milestoneFilterCurrentLevel = 0; // 1 - Current Week Open, 2 - Current Week Open and Closed, 3 - All Time Open,
											// 4 - All Time Open and Closed

	Connection conn;
	String DashboardFile;

	@FXML
	private void helloworldButtonAction(ActionEvent event) throws IOException, SQLException {
		getData();
		// readExcel();
	}

	@FXML
	private void openWorkReport(ActionEvent event) throws IOException, SQLException {
		// getData();
		// readExcel();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/WorkReport.fxml"));
		Parent root = (Parent) loader.load();
		WorkReportController controller = (WorkReportController) loader.getController();

		Stage stage = new Stage(StageStyle.UTILITY);
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");

		stage.initModality(Modality.APPLICATION_MODAL);
		// controller.setpFilename(pfileName);
		controller.setStageAndSetupListeners(this, stage, rDao);

		stage.show();
	}

	private void updateStatusBar() {

		if (tbBites.isFocused() && getCurrentBite() != null) {
			this.statusText.set(getCurrentBite().getMastername() + " | " + getCurrentBite().getTaskname() + " | "
					+ getCurrentBite().getName());
		} else {
			if (tbTasks.isFocused() && getCurrentTask() != null) {
				if (getCurrentMaster().getId() < 0) {
					this.statusText.set(getCurrentTask().getMasterName() + " | " + getCurrentTask().getName());
				}
			} else {
				this.statusText.set("");
			}

		}
	}

	private void statusBarAnnoucement(String annoucementText) {
		this.statusText.set(annoucementText);
//			updateStatusBar();
	}

	private void updateTaskCountStats(ObservableList c) {

		int marked = 0;
		for (Object object : c) {
			if (((task) object).getMarked() == 1) {
				marked++;
			}
		}
		textTotalTaskCountAndMarked.setText(String.valueOf(marked) + "/" + String.valueOf(c.size()));
	}

	@FXML
	private void biteFilterBackOneDay() throws IOException, SQLException {
		bitesStart = bitesStart.plusDays(-1);
		bitesFinish = bitesStart;
		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));
	}

	@FXML
	private void biteFilterFwdOneDay() throws IOException, SQLException {
		bitesStart = bitesStart.plusDays(1);
		bitesFinish = bitesStart;

		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));

	}

	private void biteForTask(biteDao.FilterModifier mod) throws SQLException {

		if (tbTasks.isFocused() || tbMasterTasks.isFocused()) {
			showBites(mod, biteDao.Sources.PLANNING, biteDao.task_id, getCurrentMaster().getId(),
					getCurrentTask().getId());
		}

		showBites(mod, biteDao.Sources.ACTION, biteDao.task_id, getCurrentMaster().getId(), getCurrentTask().getId());

	}

	private void biteForMaster(biteDao.FilterModifier mod) throws SQLException {
		if (tbTasks.isFocused() || tbMasterTasks.isFocused()) {
			showBites(mod, biteDao.Sources.PLANNING, biteDao.master_id, getCurrentMaster().getId());
		}
		showBites(mod, biteDao.Sources.ACTION, biteDao.master_id, getCurrentMaster().getId());

	}

	private void deliverablesForTask(biteDao.FilterModifier mod) throws SQLException {
		showBites(mod, detectSource(), biteDao.task_id, getCurrentMaster().getId(), getCurrentTask().getId());
	}

	private void deliverablesForMaster(biteDao.FilterModifier mod) throws SQLException {
		showBites(mod, detectSource(), biteDao.master_id, getCurrentMaster().getId());
	}

	private void ctrl_shift_right() throws IOException, SQLException {
		if (tbBites.isFocused() || tbMiles.isFocused()) {
			biteNextKeeDay();
		} else {
			if (tbTasks.isFocused()) {
				biteForTask(biteDao.FilterModifier.OPEN);
			} else {
				if (tbMasterTasks.isFocused()) {
					biteForMaster(biteDao.FilterModifier.OPEN);
				}
			}
		}
	}

	private void space(KeyEvent event) throws IOException, SQLException {

		if (tbBites.isFocused() || tbMiles.isFocused()) {

			if (event.isShortcutDown()) {
				rescheduleTaskorBite();
			} else if (event.isShiftDown()) {
				changeBiteGoldenStatus();
			} else {
				changeBiteStatus();
			}

		} else {
			if (tbTasks.isFocused()) {
				if (event.isShortcutDown()) {
					rescheduleTaskorBite();
				} else if (event.isShiftDown()) {
					changeTaskGolden();
				} else {
					changeTaskMark();
				}
			}
		}

////	   task
//		if (!tbMasterTasks.isFocused()) {
//			if (event.isShortcutDown()) {
//				rescheduleTaskorBite();
//			} else if (event.isShiftDown()) {
//				changeTaskGolden();
//			} else {
//				changeTaskMark();
//			}
//		}
//		
//		//bite
//		
//		if (event.getCode() == KeyCode.SPACE && !event.isShiftDown()) {
//			changeBiteStatus();
//		
//		
//		}
//		

		event.consume();
	}

	private void ctrl_right() throws IOException, SQLException {

		if (tbBites.isFocused() || tbMiles.isFocused()) {
			biteFilterFwdOneWeek();
		} else {
			if (tbTasks.isFocused()) {
				biteForTask(biteDao.FilterModifier.ALL);
			} else {
				if (tbMasterTasks.isFocused()) {
					biteForMaster(biteDao.FilterModifier.ALL);
				}
			}
		}

	}

	@FXML
	private void biteFilterToday(KeyEvent event) throws IOException, SQLException {

		bitesFinish = LocalDate.now();
		bitesStart = LocalDate.now();

		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));

	}

	private void biteLastKeeDay(KeyEvent event) throws IOException, SQLException {

		bitesStart = bitesStart.plusDays(-7);
		bitesFinish = bitesStart;
		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));

	}

	private void biteNextKeeDay() throws IOException, SQLException {
		bitesStart = bitesStart.plusDays(+7);
		bitesFinish = bitesStart;
		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));
	}

	private void biteFilterTheWeek() throws IOException, SQLException {

		bitesFinish = Util.weekEnd(bitesStart);
		bitesStart = Util.weekStart(bitesStart);

		if ((LocalDate.now().isAfter(bitesStart) || LocalDate.now().isEqual(bitesStart))
				&& LocalDate.now().isBefore(bitesFinish)) {
			if (!vCurrWeekPartialOn) {
				vCurrWeekPartialOn = true;
				bitesFinish = LocalDate.now();
			} else {
				vCurrWeekPartialOn = false;
			}
		}
		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));
	}

	private void biteFilterFwdOneWeek() throws IOException, SQLException {

		bitesFinish = Util.weekEnd(bitesFinish).plusDays(+7);
		bitesStart = Util.weekStart(bitesFinish);

		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));
	}

	private void biteFilterBackOneWeek() throws IOException, SQLException {

		bitesFinish = Util.weekEnd(bitesFinish).plusDays(-7);
		bitesStart = Util.weekStart(bitesFinish);

		showBites(biteDao.FilterModifier.ALL, detectSource(), biteDao.dates_between, Util.sqlDate(bitesStart),
				Util.sqlDate(bitesFinish));
	}

	private void biteFilter() throws IOException, SQLException {
		showBites(biteDao.FilterModifier.OPEN, detectSource(), biteDao.task_id, getCurrentTask().getMasterid(),
				getCurrentTask().getId());
	}

	private void biteFilterAll() throws IOException, SQLException {
		showBites(biteDao.FilterModifier.ALL, detectSource(), "");
	}

	private void biteFilterAllClosedOrOpen(biteDao.FilterModifier modifier) throws IOException, SQLException {
		showBites(modifier, detectSource(), "");
	}
	
	@FXML
	private void testbutton(ActionEvent event) throws IOException, SQLException {
		tabPane.getSelectionModel().selectNext();
		focusAndSelect(tbWs);
	}

	public String findAndSelectProjectByID(int y, boolean select) {
		for (int x = 0; x <= tbMasterTasks.getItems().size() - 1; x++) {
			master m = (master) tbMasterTasks.getItems().get(x);
			if (m.getId() == y) {
				if (select) {
					tbMasterTasks.getSelectionModel().clearAndSelect(x);
					tbMasterTasks.scrollTo(x);
				}
				return m.getName();
			}
		}
		Util.AlertMessagebox("Master task not found");
		return "";
	}

	private void goToTask(int masterID, int taskID) {
		findAndSelectProjectByID(masterID, true);
		findAndSelectTaskByID(taskID, true);

	}

	private void goToActionLess() {
		findAndSelectProjectByID(-2, true);
		tbTasks.requestFocus();
		tbTasks.getSelectionModel().clearAndSelect(0);
	}

	private void goToRunning() {
		try {
			boolean found = false;
			int a = goToRunningSearchStart;
			int lastfound = -1;
			do {
				if (getMasterAtIndex(a).getWorkcount() > 0) {
					tbMasterTasks.getSelectionModel().select(a);
					tbMasterTasks.scrollTo(a);
					getTasks((getMasterAtIndex(a)).getId());
					goToRunningSearchStart = a + 1;
					for (int b = 0; b <= tbTasks.getItems().size() - 1; b++) {
						if ((getTaskAtIndex(b)).getStatus() == task.stWORKING) {
							tbTasks.getSelectionModel().select(b);
							tbTasks.scrollTo(b);
							tbTasks.requestFocus();
						}
					}
					found = true;
					lastfound = a;
					goToRunningSearchStart = a + 1;
				}
				a++;
				if (a > tbMasterTasks.getItems().size() - 1) {
					found = goToRunningSearchStart == 0;
					a = 0;
					goToRunningSearchStart = 0;
				}

			} while (!found);

		} catch (SQLException ex) {
			Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void selectLastWorkedTask() throws SQLException {
		dbresult dbs;
		dbs = tDao.getLastTaskWorked();
		dbs.getRs().next();
		try {
			for (int a = 0; a <= tbMasterTasks.getItems().size() - 1; a++) {
				if (getMasterAtIndex(a).getId() == dbs.getRs().getInt("MASTERID")) {
					tbMasterTasks.getSelectionModel().select(a);
					getTasks((getMasterAtIndex(a)).getId());
					for (int b = 0; b <= tbTasks.getItems().size() - 1; b++) {
						if ((getTaskAtIndex(b)).getId() == dbs.getRs().getInt("ID")) {
							tbTasks.getSelectionModel().select(b);
							tbTasks.scrollTo(b);
							tbTasks.requestFocus();
						}
					}
				}
			}
			dbs.getRs().close();

		} catch (SQLException ex) {
			Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public String findAndSelectTaskByID(int y, boolean select) {
		for (int x = 0; x <= tbTasks.getItems().size() - 1; x++) {
			task t = (task) tbTasks.getItems().get(x);
			if (t.getId() == y) {
				if (select) {
					tbTasks.getSelectionModel().clearAndSelect(x);
					tbTasks.scrollTo(x);
				}
				return t.getName();
			}
		}
		Util.AlertMessagebox("Task not Found");
		return "";
	}

	private void locateBiteParent(bite b, boolean select) {
		if (b != null) {
			findAndSelectProjectByID(b.getMasterid(), select);
			if (isTasksOn()) {
				findAndSelectTaskByID(b.getTaskid(), select);
			}
		}
	}

	private void setFilterText() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Duration dDays = Duration.between(bitesStart, bitesFinish);
		String cText = "";

		int adDays = bitesStart.compareTo(LocalDate.now());
		switch ((int) adDays) {
		case 0:
			cText = "Hoje, ";
			break;
		case 1:
			cText = "Amanhã, ";
			break;
		case -1:
			cText = "Ontem, ";
			break;
		}
		// tfBiteFilter.setText(cText + bitesStart.format(formatter));
	}

	private void showTasks(boolean x) {

		boolean alreadyOn = isTasksOn() == true;

		apTasks.setVisible(x);
		if (isTasksOn()) {
			if (alreadyOn) {
				spPane.setDividerPosition(0, 0);
				taskVisible = 3;
			} else {
				spPane.setDividerPosition(0, 0.5);
				taskVisible = 2;
			}

			// tbMasterTasks.scrollTo(tbMasterTasks.getSelectionModel().getFocusedIndex());
			// tbMasterTasks.scrollTo(0
			// tbMasterTasks.getSelectionModel().selectBelowCell();;
		} else {
			if (alreadyOn && taskVisible == 3) {
				apTasks.setVisible(true);
				spPane.setDividerPosition(0, 0.5);
				taskVisible = 2;
			} else {
				taskVisible = 1;
				spPane.setDividerPosition(0, 1);
			}

			// tbMasterTasks.scrollTo(tbMasterTasks.getSelectionModel().getFocusedIndex());
			// tbMasterTasks.getSelectionModel().selectBelowCell();;
			// tbMasterTasks.scrollTo(0);
			// tbMasterTasks.scrollTo(tbMasterTasks.getSelectionModel().getFocusedIndex());
		}
	}

	private void showMilestones(boolean x) {

		apMilestones.setVisible(x);

		if (x) {
			spPaneMiles.setDividerPosition(0, 0.5);
			focusAndSelect(tbMiles);
		} else {
			spPaneMiles.setDividerPosition(0, 1);
			focusAndSelect(tbMiles);
		}
	}

	private boolean isTasksOn() {
		return apTasks.isVisible();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try {
			this.conn = DataBaseConnection.getConnectionMySQL();
			DashboardFile = Util.Read("Config.ini", "HOME_DIR") + "\\Dashboard.txt";

		} catch (SQLException | IOException | ClassNotFoundException ex) {
			System.out.println("Erro");
			Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println("De boa");
		SetValueFactory();
		addEventListeners();
	}

	private void addEventListeners() {
		tbMasterTasks.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			// Check whether item is selected and set value of selected item to
			// Label
			if (tbMasterTasks.getSelectionModel().getSelectedItem() != null) {
				if (isTasksOn()) {
					try {
						getTasks(getCurrentMaster().getId());
						if (tbTasks.getItems().size() > 0) {
							tbTasks.getSelectionModel().clearAndSelect(0);
						} else {
							tbTasks.getSelectionModel().clearSelection();

						}
					} catch (SQLException ex) {
						Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		});

		tbBites.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

			public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
				// Check whether item is selected and set value of selected item
				// to Label
				// bite b;
				// b = (bite) tbBites.getSelectionModel().getSelectedItem();
				// timer.cancel();

				updateStatusBar();

				// timer.schedule( new TimerTask(){
				// @Override
				// public void run() {
				// Platform.runLater(() -> {
				// updateStatusBar();
				// locateBiteParent((bite)
				// tbBites.getSelectionModel().getSelectedItem(), true);
				// });
				//
				// }
				// },1000);

			}
		});

		tbTasks.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
			// Check whether item is selected and set value of selected item to
			// Label
			if (tbTasks.getSelectionModel().getSelectedItem() != null) {
				task t = getCurrentTask();
				updateStatusBar();

			}
		});

		tabPane.setOnMouseReleased(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (!doFocus.get()) {
					return;
				}
				doFocus.set(false);
				switch (tabPane.selectionModelProperty().getValue().selectedIndexProperty().intValue()) {
				case 0:
					tbTasks.requestFocus();
					break;
				case 1:
					tbWs.requestFocus(); {
					try {
						getWorkSessionData(getCurrentMaster().getId(), getCurrentTask().getId());
						tbWs.getSelectionModel().select(0);

					} catch (SQLException ex) {
						Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
					break;
				default:
					break;
				}
			}
		});

		tabPane.selectionModelProperty().getValue().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				doFocus.set(true);
			}
		});

		// tabPane.getSelectionModel().selectedIndexProperty().addListener(new
		// ChangeListener<Number>() {
		// @Override
		// public void changed(ObservableValue<? extends Number> ov, Number t,
		// final Number t1) {
		// doFocus.
		// focusAndSelect(tbWs);
		// }
		// ;
		// }
		//
		// );
	}

	public void SetDefaults() throws SQLException {
		textStatus.setText(null);

		updateOverallStats();
		// spPaneWorking.getStyleClass().add("working");
		// spPaneWaiting.getStyleClass().add("waiting");
	}

	@SuppressWarnings("empty-statement")
	public void SetValueFactory() {
		int i;

		String[] variableNames = { "countdown", "name", "category", "rolename", "inactivity", "countdown", "id",
				"perccomplete", "opencount", "workcount", "waitcount", "statustext" };

		i = 0;

		master_sign.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_projectCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_categoryCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_roleCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));

		master_lupCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_cdCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_idCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_percdoneCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_openCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_workingCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));
		master_waitingCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));

		master_statusCol.setCellValueFactory(new PropertyValueFactory<>(variableNames[i++]));

		master_sign.setCellFactory(new masterNameCellFactory());
		master_openCol.setCellFactory(new masterOpenCellFactory());
		master_workingCol.setCellFactory(new masterCellWorkingFactory());
		master_waitingCol.setCellFactory(new masterWaitingCellFactory());

		String[] TaskvariableNames = { "golden", "marked", "name", "tag", "deadline", "tooverdue", "start", "finish",
				"wait", "minutes" };

		i = 0;

		task_golden.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_marked.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_nameCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_tagCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_deadlineCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_tooverdueCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_startCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_finishCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_waitingCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));
		task_statusCol.setCellValueFactory(new PropertyValueFactory<>(TaskvariableNames[i++]));

		task_golden.setCellFactory(new goldenCellFactory());
		task_marked.setCellFactory(new taskNameCellFactory());
		task_startCol.setCellFactory(new DateCellFactoryLong());
		task_finishCol.setCellFactory(new DateCellFactoryLong());
		task_waitingCol.setCellFactory(new DateCellFactoryLong());
		task_deadlineCol.setCellFactory(new DateCellFactory());
		task_statusCol.setCellFactory(new TaskStatusCellFactoryLong());

		String[] BitevariableNames = { "status", "next", "deadline", "deadline", "name", "countdown", "golden" };
		i = 0;

		bite_statusCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_nextCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_deadlineCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_weekdayCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_nameCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_CDCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_GoldenCol.setCellValueFactory(new PropertyValueFactory<>(BitevariableNames[i++]));
		bite_deadlineCol.setCellFactory(new DayMonthCellFactory());
		bite_weekdayCol.setCellFactory(new WeekDayCellFactory());
		bite_statusCol.setCellFactory(new StatusCellFactory());
		bite_nextCol.setCellFactory(new nextCellFactory());
		bite_GoldenCol.setCellFactory(new goldenCellFactory());

		String[] WsVariableNames = { "id", "date", "date", "start", "finish", "totaltext" };

		i = 0;

		ws_idCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));
		ws_dateCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));
		ws_dowCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));
		ws_startCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));
		ws_finishCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));
		ws_totalCol.setCellValueFactory(new PropertyValueFactory<>(WsVariableNames[i++]));

		String[] deliverableVariableNames = { "status", "next", "deadline", "deadline", "name", "countdown", "golden" };

		i = 0;

		deliverable_statusCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_nextCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_deadlineCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_weekdayCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_nameCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_CDCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_GoldenCol.setCellValueFactory(new PropertyValueFactory<>(deliverableVariableNames[i++]));
		deliverable_deadlineCol.setCellFactory(new DayMonthCellFactory());
		deliverable_weekdayCol.setCellFactory(new WeekDayCellFactory());
		deliverable_statusCol.setCellFactory(new StatusCellFactory());
		deliverable_nextCol.setCellFactory(new nextCellFactory());
		deliverable_GoldenCol.setCellFactory(new goldenCellFactory());

	}

	public worksession createWS(ResultSet rs) throws SQLException {
		return new worksession(rs.getString("ID"), rs.getInt("TASKID"), rs.getInt("MASTERID"), rs.getString("MNAME"),
				rs.getString("TNAME"), rs.getString("PDATE"), rs.getString("PWEEKDAY"), rs.getString("STARTED"),
				rs.getString("FINISHED"), rs.getString("MINUTES"), rs.getString("ROLE_NAME"));
	}

	// @FXML
	// private void TabSelectionChange() throws SQLException {
	// if (taskPane.isSelected()) {
	//
	// ©(getCurrentMaster().getId(), getCurrentTask().getId());
	//
	//
	// }
	//
	// tbWs.requestFocus();
	// tbWs.getSelectionModel().clearAndSelect(1);
	//
	// }
	public void getWorkSessionData(int masterid, int taskid) throws SQLException {
		tvWorkSessionData = FXCollections.observableArrayList();
		long totalminutes = 0;
		ResultSet rs;
		rs = rDao.getTASKSESSIONS(masterid, taskid);

		worksession ws;
		while (rs.next()) {
			ws = createWS(rs);
			tvWorkSessionData.addAll(ws);
			totalminutes = totalminutes + Integer.valueOf(ws.getTotal());
		}
		tbWs.setItems(tvWorkSessionData);
	}

	private void bringPlanningAndActionBites() throws SQLException {
		
//		tvBiteData = FXCollections.observableArrayList();
		bDao = new biteDao(this.conn);
//		dbresult dbs = null;
//		ResultSet rs = null;

		bitesFinish = LocalDate.now();
		bitesStart = LocalDate.now();

		LocalDate dateStart = Util.weekStart(LocalDate.now());
		LocalDate dateEnd = Util.weekEnd(LocalDate.now());

		showBites(biteDao.FilterModifier.ALL, biteDao.Sources.PLANNING, biteDao.dates_between, Util.sqlDate(dateStart),
				Util.sqlDate(dateEnd));
		
		showBites(biteDao.FilterModifier.ALL, biteDao.Sources.ACTION, biteDao.dates_between,
				Util.sqlDate(LocalDate.now()), Util.sqlDate(LocalDate.now()));

	}

	private void populateTable(Sources source, boolean claimfocus) throws SQLException {

//		tvObservable = FXCollections.observableArrayList();

		

//			if (claimfocus) {
//				focusAndSelect(tbBites);
//
//				if (tbTable.getSelectionModel().getFocusedIndex() < 0) {
//					tbTable.getSelectionModel().select(0);
//				}
//			}

//			focusAndSelect(tbTable);
	}

	private Object[] getNewPar(Object[] parametros, Object... newParameters) {
		Object[] newPar = new Object[parametros.length + newParameters.length];
		System.arraycopy(parametros, 0, newPar, 0, parametros.length);

		for (int i = 0; i < newParameters.length; i++) {
			newPar[parametros.length + i] = newParameters[i];
		}

		return newPar;
	}

	private biteDao.Sources detectSource() {
		biteDao.Sources source = (tbMiles.isFocused()) ? biteDao.Sources.PLANNING : biteDao.Sources.ACTION;
		return source;
	}

	private dbresult showBites(biteDao.FilterModifier modifier, biteDao.Sources source, String filter,
			Object... parametros) throws SQLException {

		vCurrMilestoneFilterLevel = 0;
		
		biteDao.FilterModifier[] modifiers = biteDao.FilterModifier.values();

		switch (modifier) {
		case ALL:
			filter = filter;
			break;
		case LATE:
			filter = filter + biteDao.dates_late;
			break;
			
		case CLOSED:
			filter = filter + biteDao.status_range;
			parametros = getNewPar(parametros, 3, 3);
			break;
			
		case OPEN:
			filter = filter + biteDao.status_range;
			parametros = getNewPar(parametros, 1, 2);
			break;
		}

		dbresult dbs = null;
		dbs = bDao.getBites(source, filter, parametros);
		
		
		int a = 0;
		
		tvObservable.clear();
		if (dbs.getRs() != null) {
			while (dbs.getRs().next()) {
				tvObservable.add(createBite(dbs.getRs()));
				a = a +1;
			}
		}
		
		System.out.println("Records loaaded " + a );
		
		dbs.getRs().close();
 		dbs.getStm().close();
		
 		
 		if (source == biteDao.Sources.PLANNING ) {
 			tvMilestoneData.clear();
			tvMilestoneData.addAll(tvObservable);
			tbMiles.setItems(tvMilestoneData);

			tvMilesData_tb_items.clear();
			tvMilesData_tb_items.addAll(tvObservable);

			
			focusAndSelect(tbMiles);
		} else {
			tvBiteData.clear();
			tvBiteData.addAll(tvObservable);
			
			tvBiteData_tb_items.clear();
			tvBiteData_tb_items.addAll(tvObservable);
			
			tbBites.setItems(tvBiteData);
			focusAndSelect(tbBites);
		}
		return dbs;
	}

	private void maintainRunningMasterList(master m) {
		if (m.getWorkcount() > 0) {
			if (runningMaster.indexOf(m) == -1) {
				runningMaster.add(m);
			}
		} else {
			if (runningMaster.indexOf(m) > -1) {
				runningMaster.remove(m);
			}
		}
	}

	private void maintainRunningTaskList(task t) {
		if (t.getStatus() == task.stWORKING) {
			if (runningTask.indexOf(t) == -1) {
				runningTask.add(t);
			}
		} else {
			if (runningTask.indexOf(t) > -1) {
				runningTask.remove(t);
			}
		}
	}

	public void getData() throws SQLException, IOException {

		mDao = new masterDao(this.conn);
		miDao = new milestoneDao(this.conn);
		tDao = new taskDao(this.conn);
		wDao = new waitDao(this.conn);
		cDao = new catDao(this.conn);
		rDao = new reportDao(this.conn);
		roDao = new roleDao(this.conn);
		gsDao = new globalSearchDao(this.conn);

		ResultSet rs;
		dbresult dbs = mDao.getallrecords();
		rs = mDao.getallrecords().getRs();

		tvMasterData = FXCollections.observableArrayList();
		tvTaskData = FXCollections.observableArrayList();
		tvBiteData = FXCollections.observableArrayList();
		tvMilestoneData = FXCollections.observableArrayList();
		tvObservable= FXCollections.observableArrayList();
		tvMilesData_tb_items= FXCollections.observableArrayList();
		tvBiteData_tb_items= FXCollections.observableArrayList();

		// carregando Masters
		master m;
		while (rs.next()) {
			m = createMaster(rs);
			maintainRunningMasterList(m);
			tvMasterData.addAll(m);
		}

//		System.out.println("Array size " + runningMaster.size());

		tvMasterData.add(new master(-1, "All Overdue Tasks", null));
		tvMasterData.add(new master(-2, "All Tasks with no next action", null));
		tbMasterTasks.setItems(tvMasterData);
		rs.close();

		rs = tDao.getTasks_by_Master(12).getRs();

		task t;
		while (rs.next()) {
			t = createTask(rs);
			maintainRunningTaskList(t);
			tvTaskData.addAll(t);

		}

		rs.close();

		tbTasks.setItems(tvTaskData);

		bitesStart = LocalDate.now();
		bitesFinish = LocalDate.now();

		bringPlanningAndActionBites();

		getWorkSessionData(1, 4);

	}

	public void getTasks(int masterid) throws SQLException {
		runningTask.clear();

		taskDao tDao;
		tDao = new taskDao(this.conn);

		ResultSet rs;

		tvTaskData = FXCollections.observableArrayList();

		dbresult dbs = tDao.getTasks_by_Master(masterid);
		rs = dbs.getRs();

		task t;
		while (rs.next()) {
			t = createTask(rs);
			maintainRunningTaskList(t);
			tvTaskData.addAll(t);
		}

		tvTaskData.addListener(new ListChangeListener<task>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends task> c) {
				updateTaskCountStats(c.getList());
			}
		});

		tbTasks.setItems(tvTaskData);
		updateTaskCountStats(tvTaskData);
		dbs.getRs().close();
		dbs.getStm().close();
	}

	/**
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static void copyFields(Object from, Object to) {
		// Obtenho os campos que estão declarados no objeto de origem
		Field fields[] = from.getClass().getDeclaredFields();
		for (Field fromField : fields) {
			try {
				// Obtenho o campo do objeto de destino que possue o mesmo nome
				// do campo do objeto de origem
				Field toField = to.getClass().getDeclaredField(fromField.getName());
				// Modifico a acessibilidade...
				toField.setAccessible(true);
				fromField.setAccessible(true);
				// Obtenho o valor do campo do objeto de origem
				Object value = fromField.get(from);
				// Coloco o valor no campo do objeto de destino
				toField.set(to, value);

			} catch (Exception e) {
				// Se der erro, não copio e passo para o próximo campo
			}
		}
	}

	public master getDBMaster(master m) throws SQLException {
		dbresult dbs;
		dbs = mDao.getrecord(m.getId());

		dbs.getRs().next();

		m = createMaster(dbs.getRs());

		dbs.getRs().close();
		dbs.getStm().close();

		// 2014m11 - usando reflection para atualizar meu meu
		return m;
	}

	public master createMaster(ResultSet rs) throws SQLException {
		return new master(rs.getInt("ID"), rs.getString("NAME"), rs.getString("CATEGORY"), rs.getInt("COUNTDOWN"),
				rs.getInt("INACTIVITY"), rs.getFloat("PERCCOMPLETE"), rs.getLong("MINUTES"), rs.getString("REFERENCE"),
				rs.getInt("WORKCOUNT"), rs.getInt("WAITCOUNT"), rs.getInt("DONECOUNT"), rs.getInt("OPENCOUNT"),
				rs.getTimestamp("LASTUPDATE"), rs.getTimestamp("CREATED"), rs.getString("ROLE_NAME"),
				rs.getInt("ROLE_ID"));
	}

	public task createTask(ResultSet rs) throws SQLException {
		return new task(rs.getInt("MASTERID"), rs.getInt("ID"), rs.getString("NAME"), rs.getString("CATEGORY"),
				rs.getDate("DEADLINE"), rs.getInt("TOO"), rs.getTimestamp("STARTED"), rs.getTimestamp("RESTARTED"),
				rs.getTimestamp("FINISHED"), rs.getTimestamp("WAITING"), rs.getInt("STATUS"), rs.getLong("MINUTES"),
				rs.getString("REFERENCE"), rs.getTimestamp("CHANGEDON"), rs.getTimestamp("CREATED"),
				rs.getInt("pinned"), rs.getInt("marked"), rs.getInt("golden"), rs.getString("MASTERNAME"),
				roDao.GetRoleName(rs.getInt("ROLE_ID")), rs.getInt("ROLE_ID"));
	}

	public bite createBite(ResultSet rs) throws SQLException {

		return new bite(rs.getInt("ID"), rs.getInt("MASTERID"), rs.getInt("TASKID"), rs.getString("NAME"),
				rs.getDate("DEADLINE"), rs.getInt("STATUS"), rs.getInt("NEXT"), rs.getInt("PRIORITY"),
				rs.getDate("CREATED"), rs.getDate("LASTRESCHEDULE"), rs.getDate("FINISHED"), rs.getString("MASTERNAME"),
				rs.getString("TASKNAME"), rs.getInt("golden"), rs.getInt("countdown"));

	}

	String getCurrentFileName() {
		return getCurrentFileName(3);
	}

	;

	String getCurrentFileName(int pType) {
		// pType = 1 -> Master
		// pType = 2 -> Task
		// pType = 3 -> Active

		String TID = "";
		String fName = "";

		try {
			switch (pType) {
			case 1:
				TID = "Master";
				fName = getCurrentMaster().getName();
				fName = Util.LimpaFileName(fName);
				fName = TextFileHandler.GetNotesFile(getCurrentMaster().getName(), fName, TID, true);
				break;
			case 2:
				TID = String.valueOf(getCurrentTask().getId());
				fName = getCurrentTask().getName();
				fName = Util.LimpaFileName(fName);
				fName = TextFileHandler.GetNotesFile(getCurrentTask().getMasterName(), fName, TID, true);
				break;
			case 3:
				if (tbMasterTasks.isFocused()) {
					TID = "Master";
					fName = getCurrentTask().getMasterName();
				} else {
					TID = String.valueOf(getCurrentTask().getId());
					fName = getCurrentTask().getName();
				}
				fName = Util.LimpaFileName(fName);
				fName = TextFileHandler.GetNotesFile(getCurrentTask().getMasterName(), fName, TID, true);
				break;
			}
			
			return fName;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
	}

	public master getCurrentMaster() {
		return (master) tbMasterTasks.getSelectionModel().getSelectedItem();
	}

	public bite getCurrentBite() {
		return (bite) tbBites.getSelectionModel().getSelectedItem();
	}

	public bite getSelectedBite() {

		if (tbMiles.isFocused()) {
			return (bite) tbMiles.getSelectionModel().getSelectedItem();
		} else {
			if (tbBites.isFocused()) {
				return (bite) tbBites.getSelectionModel().getSelectedItem();
			} else {
				return null;
			}
		}

	}

	public bite getCurrentMilestone() {
		return (bite) tbMiles.getSelectionModel().getSelectedItem();
	}

	public worksession getCurrentWS() {
		return (worksession) tbWs.getSelectionModel().getSelectedItem();
	}

	public task getCurrentTask() {

		return (task) tbTasks.getSelectionModel().getSelectedItems().get(0);

	}

	public todoitem getCurrentTodo(TableView tb) {
		return (todoitem) tb.getSelectionModel().getSelectedItems().get(0);

	}

	public master getMasterAtIndex(int index) {
		return (master) tbMasterTasks.getItems().get(index);
	}

	public task getTaskAtIndex(int index) {
		return (task) tbTasks.getItems().get(index);
	}

	public bite getBiteAtIndex(int index) {
		return (bite) tbBites.getItems().get(index);

	}

	class masterWaitingCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {

			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {

					getStyleClass().remove("waiting");
					super.updateItem((T) item, empty);
					setText("");
					if (item != null) {
						if (!item.toString().equals("0")) {
							setText(StringUtils.leftPad(item.toString(), 2, "0"));
							getStyleClass().add("waiting");
						} else {
							setText("");
						}
					}
				}
			};
			return cell;

		}
	}

	class masterNameCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {

			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					super.updateItem((T) item, empty);
					if (item != null) {
						getStyleClass().remove("waiting");
						if (Integer.parseInt(item.toString()) < 0) {
							// setText("teste teste teste teste teste");
							getStyleClass().add("waiting");
						}
					}
				}
			};
			return cell;

		}
	}

	class taskNameCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					super.updateItem((T) item, empty);
					getStyleClass().remove("taskmark");
					if (item != null) {
						if (Integer.parseInt(item.toString()) == 1) {
							setText(item.toString());
							getStyleClass().add("taskmark");
						}
					}
				}
			};
			return cell;
		}
	}

	class masterOpenCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {

			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					super.updateItem((T) item, empty);
					setText("");
					if (item != null) {
						if (!item.toString().equals("0")) {
							setText(StringUtils.leftPad(item.toString(), 2, "0"));
							getStyleClass().add("open");
						} else {
							setText("");
						}
					}
				}
			};
			return cell;

		}
	}

	class masterCellWorkingFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					getStyleClass().remove("working");
					super.updateItem((T) item, empty);

					setText("");
					if (item != null) {
						if (!item.toString().equals("0")) {
							setText(StringUtils.leftPad(item.toString(), 2, "0"));
							getStyleClass().add("working");
						} else {
							setText("");
						}
					}
				}
			};
			return cell;

		}
	}

	class TaskStatusCellFactoryLong<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					super.updateItem((T) item, empty);
					getStyleClass().remove("working");
					getStyleClass().remove("waiting");
					getStyleClass().remove("done");
					getStyleClass().remove("open");

					if (item != null) {
						String status;
//						System.out.println(String.valueOf(getTaskAtIndex(this.getIndex()).getStatus()));

						status = getTaskAtIndex(this.getIndex()).GetTextStatus();
						if (!"".equals(status)) {
							getStyleClass().add(status.toLowerCase());
						} else {
							getStyleClass().add("open");
						}

						setText(StringUtils.leftPad(Util.MinutesToHoursText(((Long) item)), 5, "0"));

					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class DateCellFactoryLong<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					if (item != null) {
						setText(Util.DateToText((Timestamp) item, "HH:mm dd/MMM/yyyy"));
						// setText( "Holaaaa");
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class DateCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					if (item != null) {
//						System.out.println(item);
//						System.out.println(Util.DateToText((Date) item, "dd/MM/yyyy"));
						setText(Util.DateToText((Date) item, "dd/MMM/yyyy"));
						getStyleClass().add("center");
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class DayMonthCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					if (item != null) {

						setText(Util.DateToText((Date) item, "dd-MMM-yy"));
						getStyleClass().add("center");

					} else {
						setText("");
					}
				}
			};
			return cell;

		}
	}

	class WeekDayCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					if (item != null) {
						setText(Util.DateToText((Date) item, "EEE"));
						getStyleClass().add("center");
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class StatusCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					getStyleClass().remove("bitedone");
					if (item != null) {

						if ((int) item == task.stDONE) {
							getStyleClass().add("bitedone");
						}
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class nextCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					getStyleClass().remove("bitenext");
					if (item != null) {
						switch ((int) item) {
						case bite.stNEXT:
							getStyleClass().add("bitenext");
							break;
						}
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	class goldenCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
		@Override
		public TableCell<S, T> call(TableColumn<S, T> p) {
			TableCell<S, T> cell;
			cell = new TableCell<S, T>() {
				@Override
				protected void updateItem(Object item, boolean empty) {
					getStyleClass().remove("Important");
					getStyleClass().remove("Urgent");
					getStyleClass().remove("DailyWin");
					if (item != null) {
						switch ((int) item) {
						case 1:
							getStyleClass().add("Urgent");
							break;
						case 2:
							getStyleClass().add("Important");
							break;
						case 3:
							getStyleClass().add("DailyWin");
							break;
						}
					} else {
						setText("");
					}
				}
			};
			return cell;
		}
	}

	void changeTableFocus(KeyEvent event) {

		ArrayList<TableView> tables = new ArrayList<>();

		tables.add(tbMasterTasks);
		tables.add(tbTasks);
		tables.add(tbBites);
		tables.add(tbMiles);

		int currentIndex = 0;
		for (TableView tb : tables) {
			if (tb.isFocused()) {
				currentIndex = tables.indexOf(tb);
			}
		}

		switch (event.getCode()) {
		case LEFT:
			if (currentIndex == 3) {
				currentIndex = 1;
			} else {
				currentIndex -= 1;
			}
			break;
		case RIGHT:
			currentIndex += 1;
			break;
		}

		if (currentIndex > tables.size() - 1) {
			currentIndex -= tables.size();
		} else {
			if (currentIndex < 0) {
				currentIndex = tables.size() - 1;
			}
		}
		focusAndSelect(tables.get(currentIndex));
	}

	@SuppressWarnings("empty-statement")
	public void setStageAndSetupListeners(Stage stage) throws SQLException, IOException {

		getData();
		SetDefaults();
		taskVisible = 2;

//		2019m06_02 - Binding Status Bar Screen Component to the variable

//		SimpleStringProperty 

		textStatus.textProperty().bind(statusText);

		spPaneVertical.setDividerPositions(0.70, 0.2);
		focusAndSelect(tbMasterTasks);

		final KeyCombination scGoToActionLess = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scTroubleshoot = new KeyCodeCombination(KeyCode.M, KeyCombination.ALT_DOWN);

		final KeyCombination scGolden = new KeyCodeCombination(KeyCode.SPACE, KeyCombination.SHIFT_DOWN);

		final KeyCombination scAbreDir = new KeyCodeCombination(KeyCode.J, KeyCombination.SHORTCUT_DOWN,
				KeyCombination.SHIFT_ANY);

		final KeyCombination scRescheduled = new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN);

		final KeyCombination scLogNotes = new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scWorkHistory = new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scGoToLastWorked = new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scGotoRun = new KeyCodeCombination(KeyCode.R, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scInsertMaster = new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scInsertTask = new KeyCodeCombination(KeyCode.I);

		final KeyCombination scTopMaster = new KeyCodeCombination(KeyCode.UP, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scFocusMaster = new KeyCodeCombination(KeyCode.LEFT); // ,
																					// KeyCombination.ALT_DOWN
		final KeyCombination scFocusTask = new KeyCodeCombination(KeyCode.RIGHT); // ,
																					// KeyCombination.ALT_DOWN

		final KeyCombination scChangeTaskStatus = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scChangeTaskStatusParallel = new KeyCodeCombination(KeyCode.ENTER,
				KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN);

		final KeyCombination scChangeToWait = new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scExpandProject = new KeyCodeCombination(KeyCode.DOWN, KeyCombination.ALT_DOWN);
		final KeyCombination scCollapseProject = new KeyCodeCombination(KeyCode.UP, KeyCombination.ALT_DOWN);

		// Bites shortcust
		final KeyCombination scSpecificBites = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scBitesDayBefore = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.ALT_DOWN);
		final KeyCombination scBitesDayAfter = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.ALT_DOWN);

		final KeyCombination scBitesWeekBefore = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scBitesWeekAfter = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scBitesToday = new KeyCodeCombination(KeyCode.T, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scBitesWeek = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scBitesAll = new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN);
		final KeyCombination scBitesAllPending = new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scBitesLastWeekDay = new KeyCodeCombination(KeyCode.LEFT, KeyCombination.SHORTCUT_DOWN,
				KeyCombination.SHIFT_DOWN);
		final KeyCombination scBitesNextWeekDay = new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.SHORTCUT_DOWN,
				KeyCombination.SHIFT_DOWN);

		final KeyCombination scAddBites = new KeyCodeCombination(KeyCode.M, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scDash = new KeyCodeCombination(KeyCode.G, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scQuickFind = new KeyCodeCombination(KeyCode.F, KeyCombination.SHORTCUT_DOWN);

		final KeyCombination scDelete = new KeyCodeCombination(KeyCode.DELETE);

		stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

			// if key is not M, reset the filter level for milestones
			TableView tbTable;
			tbTable = tbBites;

			
			if (event.getCode() != KeyCode.K) {
				milestoneFilterCurrentLevel = 0;
			}

			try {
				 System.out.println(event.getCode());	
				switch (event.getCode()) {

				case BACK_QUOTE:
				case DEAD_GRAVE:
					gotoQuickAccessTask2();
					break;
				case ESCAPE:
					gotoQuickAccessTask();
					break;
				case DIGIT0:
				case DIGIT1:
				case DIGIT2:
				case DIGIT3:
				case DIGIT4:
				case DIGIT5:
				case DIGIT6:
				case DIGIT7:
				case DIGIT8:
				case DIGIT9:
				case MINUS:
				case EQUALS:
					quickReschedule(event.getCode(),
							getCurrentTodo((TableView) stage.getScene().focusOwnerProperty().get()),
							event.isShortcutDown());
					break;
				case K:
					if (event.isShortcutDown()) {
						kickItDownTheRoad(getCurrentTodo((TableView) stage.getScene().focusOwnerProperty().get()));
					}

					break;
				case SPACE:

					if (!tbMasterTasks.isFocused()) {
						space(event);
					}
					break;
				case N:
					if (event.isShortcutDown()) {
						if (tbBites.isFocused()) {
							locateBiteParent((bite) tbBites.getSelectionModel().getSelectedItem(), true);
							Util.logStatusChange("", "[ " + getCurrentBite().getName() + " ]", getCurrentFileName(2) );
							openNota(getCurrentFileName(2));
						} else {

							if (tbMasterTasks.isFocused()) {
								openNota(getCurrentFileName(1));
							} else {
								openNota(getCurrentFileName(2));
							}
						}
					}
					break;
				case I:
					if (event.isShortcutDown() && event.isShiftDown()) {
						addMaster();
						focusAndSelect(tbMasterTasks);
					} else {
						if (event.isShortcutDown()) {
							addTask();
						}
					}
					break;

				case LEFT:
					if (event.isControlDown() && event.isShiftDown()) {
						biteLastKeeDay(event);
					} else {
						if (event.isControlDown()) {
							biteFilterBackOneWeek();
						} else {
							if (event.isAltDown()) {
								biteFilterBackOneDay();
							} else {
								changeTableFocus(event);
							}
						}
					}

					break;

				case RIGHT:
					if (event.isControlDown() && event.isShiftDown()) {
						ctrl_shift_right();
					} else {
						if (event.isShiftDown()) {
							markasnext();
							;
						} else {
							if (event.isControlDown()) {
								ctrl_right();
							} else {
								if (event.isAltDown()) {
									biteFilterFwdOneDay();
								} else {
									changeTableFocus(event);
								}
							}
						}
					}

					event.consume();
					break;
				case DOWN:
					if (event.isShortcutDown()) {
						if (event.isShiftDown()) {
//							System.out.println("General Script");
							String currentPath = openDir(true, true);
							String generalScript = removeLimiteChars(getQuickCopy(6)).replace("{}", currentPath);
//							System.out.println(generalScript);
							run(generalScript);
						} else {
//							System.out.println("General Script");
							String currentPath = openDir(true, true);
							String generalScript = removeLimiteChars(getQuickCopy(3)).replace("{}", currentPath);
//							System.out.println(generalScript);
							run(generalScript);
						}
//						
						event.consume();

					} else {
						if (event.isAltDown() && event.isShiftDown()) {
							showTasks(false);

						} else {
							if (event.isAltDown()) {
								showMilestones(false);
								event.consume();
							}
						}
					}

					break;
				case UP:
					if (event.isAltDown() && event.isShiftDown()) {
						showTasks(true);
						getTasks(getCurrentMaster().getId());
					} else {
						if (event.isAltDown()) {
							showMilestones(true);
							event.consume();
						} else {
							if (event.isControlDown()) {
								master m = getCurrentMaster();
								tbMasterTasks.getItems().remove(m);
								tbMasterTasks.getItems().add(0, m);
								tbMasterTasks.scrollTo(0);
								tbMasterTasks.getSelectionModel().clearAndSelect(0);
							}

						}
					}
					break;
				case G:
					if (event.isShortcutDown()) {
						openDashFile();
					}
					break;
				case T:
					if (event.isShortcutDown()) {
						bringPlanningAndActionBites();
						event.consume();
					}
					break;

				case A:
					
					biteDao.FilterModifier modifier;
					modifier = biteDao.FilterModifier.ALL;
					
					if (event.isShortcutDown()) {
						if (event.isShiftDown()) {
							modifier = biteDao.FilterModifier.CLOSED;
						}
						if (tbMiles.isFocused()) {
							biteFilterAllClosedOrOpen(modifier);
						} else {
							if (tbBites.isFocused()) {
								biteFilterAllClosedOrOpen(modifier);
							} else {
								if (tbTasks.isFocused()) {
									runPythonScriptFileFile(getCurrentFileName(2));
								}
							}
						}
					}
					break;

				case U:
					if (event.isShortcutDown()) {
						if (tbTasks.isFocused() || tbMasterTasks.isFocused()) {
							openURLfromNotes(getCurrentFileName(3));
						}
					}
					break;
				case D:
					//2020m01_18 - New Feature to filter deliverable panel
					String[] filter_options = {"","deliverable - ", "d - ", "r - ", "w - "};
					if (tbMiles.isFocused() && event.isShortcutDown()) {
						tvMilesData_tb_items.clear();
						ObservableList<TableColumn<bite, ?>> cols = tbMiles.getColumns();
						TableColumn col = cols.get(4);
						vCurrMilestoneFilterLevel = vCurrMilestoneFilterLevel + 1;
						if (vCurrMilestoneFilterLevel > filter_options.length-1) {
							vCurrMilestoneFilterLevel = 0;
						}
						 String search_term = filter_options[vCurrMilestoneFilterLevel];
						 System.out.println(search_term);

						 for(int i=0; i< tvMilestoneData.size(); i++) {
 							String cellValue = col.getCellData(tvMilestoneData.get(i)).toString().toLowerCase();
 							if (cellValue.startsWith(search_term)) {
 								tvMilesData_tb_items.add(tvMilestoneData.get(i));
 							}
 							tbMiles.setItems(tvMilesData_tb_items);
 						 }
 						focusAndSelect(tbMiles); 
 						event.consume();
					} else { 
						//This feature will toggle a task into a different type of deliverable 
						if (tbMiles.isFocused() && event.isShiftDown()) {
							todoitem item;
							item = getCurrentTodo((TableView) stage.getScene().focusOwnerProperty().get());
							String newName = item.getName();
							int type_detected = 0;
							for(int i = 1; i < filter_options.length; i++) {
								if (item.getName().toLowerCase().startsWith(filter_options[i]) ) {
									type_detected = i;
								}
							}

							
							int new_type = type_detected + 1; 
							if (new_type > filter_options.length-1) {
								new_type = 0;
							}
							
							
							if (type_detected != 0 ) {
							   newName = item.getName().substring(filter_options[type_detected].length());
							}
							
							newName = filter_options[new_type] + newName;
							
							
							item.setName(newName);
							System.out.println(item.getName());
							bDao.persist((bite) item);
							event.consume();
						}
					}
					break;
					
				case X:
					if (tbMiles.isFocused() && event.isShortcutDown() ) {

						
						
						tvMilesData_tb_items.clear();
						tvMilesData_tb_items.addAll(tvMilestoneData);
							tbMiles.setItems(tvMilesData_tb_items);
						
						vCurrMilestoneFilterLevel = 0;
						  
						tbMiles.setItems(tvMilesData_tb_items);
 						event.consume();
							
 						focusAndSelect(tbMiles); 
						}
					break;

				case W:
					if (event.isShortcutDown()) {
						biteFilterTheWeek();
					}
					break;

				case R:
					if (event.isShortcutDown()) {
						goToRunning();
					}
					break;

				case L:
					if (event.isShortcutDown()) {
						selectLastWorkedTask();
					}
					break;

				case F:
					if (event.isShortcutDown()) {

						if (event.isShiftDown()) {
							openGlobalFind();
						} else {
							openQuikFind("");
						}
					}
					break;

				case C:
					if (event.isShortcutDown()) {

						if (event.isShiftDown()) {
							getQuickCopy(2);
						} else {
							getQuickCopy(1);
						}
					}
					break;

				case S:
					if (event.isShortcutDown()) {
						openWorkReport(null);
					}
					break;

				case Y:
					if (event.isShortcutDown()) {
						goToActionLess();
					}
					break;

				default:
					if ((event.getCode().isLetterKey() && !event.isShortcutDown()) || scQuickFind.match(event)) {
						if (scQuickFind.match(event)) {
							openQuikFind("");
						}
					}
				}

				if (event.getCode().isLetterKey() && !event.isShortcutDown() && !event.isConsumed()) {
					openQuikFind(event.getCode().toString());
					event.consume();
				}

			} catch (IOException | SQLException | ParseException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		tbTasks.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			try {
				switch (event.getCode()) {
				case M:
					if (event.isShortcutDown()) {
						if (event.isShiftDown()) {

							addBiteFromClipboard();
//							addMilestone();

						} else {
							addBite();
						}
					} else
						event.consume();
					break;
				case DELETE:
					if (!this.deleting) {
						deleteTask();
					}
					this.deleting = !this.deleting;
					event.consume();
					break;
				case D:
				case ENTER:
					if (event.isShortcutDown()) {
						changeTaskStatus(scChangeToWait.match(event), event.isShiftDown());
					} else {
						if (event.isShiftDown()) {
							getTextFromRelatedFile();
						} else {
							changeTask();
						}
					}
					event.consume();
					break;
				case J:
					if (event.isShortcutDown()) {
						openDir(event.isShiftDown());
					}
					event.consume();
					break;
				}
			} catch (IOException | SQLException | ParseException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}

		});

		tbBites.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

			try {

				if (scRescheduled.match(event)) {
					rescheduleTaskorBite();
					event.consume();
				} else {

					if (event.getCode() == KeyCode.ENTER) {
						locateBiteParent((bite) tbBites.getSelectionModel().getSelectedItem(), true);
					} else {

						if (scDelete.match(event)) {
							deleteBite();
							event.consume();
						}
					}
					if (scBitesDayAfter.match(event)) {
//						biteFilterFwdOneDay();
						event.consume();
					} else {
						if (scBitesDayBefore.match(event)) {
//							biteFilterBackOneDay();
							event.consume();
						} else {
							if (scBitesWeekAfter.match(event)) {
//								biteFilterFwdOneWeek(event,tbBites);
								event.consume();
							} else {
								if (scBitesWeekBefore.match(event)) {
//									biteFilterBackOneWeek(event,tbBites);
									event.consume();
								} else {
									if (event.getCode() == KeyCode.SPACE && !event.isShiftDown()) {
//										changeBiteStatus();
									} else {
										if (scChangeTaskStatus.match(event)) {
											Util.logStatusChange("", "[ " + getCurrentBite().getName() + " ]", getCurrentFileName(2));
											if (getCurrentTask().getStatus() != task.stWORKING) {
												tbTasks.requestFocus();
												changeTaskStatus(false, event.isShiftDown()); // se
																								// shift
																								// down,
																								// eh
																								// paralel
											}
											tbTasks.requestFocus();
										} else {
											if (scGolden.match(event)) {
												changeBiteGoldenStatus();
												event.consume();

											} else {
												if (event.getCode() == KeyCode.RIGHT && event.isShiftDown()) {
													markasnext();
												}

											}
										}
									}
								}
							}
						}
					}
				}
			} catch (IOException | SQLException | ParseException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		tbMasterTasks.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			try {
				switch (event.getCode()) {
				case ENTER:
					editMaster(getCurrentMaster(), Util.opUPDATE);
					break;
				case DELETE:
					deleteMaster();
			
					break;
				case RIGHT:
					if (event.isShiftDown() && event.isAltDown()) // isShorcutDown
																	// --->
																	// Control
																	// on
																	// Windows
					{
//						biteFilter(false, BITE_FILTER_OPTION.MASTER_SPECIFIC_LATE,tbBites);
					} else {
						if (event.isShortcutDown()) // isShorcutDown --->
													// Control on Windows
						{
//							biteFilter(event.isShiftDown(), BITE_FILTER_OPTION.MASTER_SPECIFIC,tbBites);
						}
					}
					break;
				case J:
					if (event.isShortcutDown()) {
						openDir(event.isShiftDown());
					}
					event.consume();
					break;
				case M:
					if (event.isShortcutDown()) {
						if (event.isAltDown()) {
//							addMilestone();
						} else {
							addBite();
						}
					} else
						event.consume();
					break;
				}
			} catch (IOException | SQLException | ParseException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		tbWs.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			try {
				if (event.getCode() == KeyCode.ENTER) {
					fixDates();

				}
			} catch (IOException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});

		/*
		 * tbTasks.getSelectionModel().selectedItemProperty().addListener((
		 * observableValue, oldValue, newValue) -> { Check whether item is selected and
		 * set value of selected item to Label if
		 * (tbTasks.getSelectionModel().getSelectedItem() != null) {
		 * tfBiteFilter.setText(observableValue.toString()); } });
		 */

		tbMiles.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
			try {

				switch (event.getCode()) {
				case ENTER:
					findAndSelectProjectByID(getCurrentMilestone().getMasterid(), true);
					findAndSelectTaskByID(getCurrentMilestone().getTaskid(), true);
					tbBites.requestFocus();
					biteForTask(biteDao.FilterModifier.OPEN);
					tbMiles.requestFocus();

					event.consume();
					break;
				case SPACE:
					if (!event.isShiftDown()) {
//						changeMilestoneStatus();
						// getMilestones();
					} else {
//						changeMilestoneGolden();
					}
					event.consume();
					break;
				case DELETE:
					deletePlanningBite();
					event.consume();
					break;

				case M:
					if (event.isShortcutDown()) {
						if (event.isAltDown()) {
//							addMilestone();
						} else {
							addBite();
						}
					} else
						event.consume();
					break;

				}
			} catch (SQLException | IOException | ParseException ex) {
				Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
	}

	private Date getDeadline(todoitem item) {
		if (item instanceof task) {
			return ((task) item).getDeadline();

		} else if (item instanceof bite) {
			return ((bite) item).getDeadline();
		}
		return null;
	}

	private void setDeadline(todoitem item, LocalDate date) throws SQLException {
		if (item instanceof task) {
			((task) item).setDeadline(Date.valueOf(date));
			tDao.persist((task) item);

		} else if (item instanceof bite) {
			locateBiteParent((bite) item, true);
			((bite) item).incReschedule(((bite) item).getDeadline().toLocalDate(), date);
			((bite) item).setDeadline(Date.valueOf(date));

			task Task = getCurrentTask();
			Task.setDeadline(Date.valueOf(date));
			tDao.persist(Task);
			bDao.persist((bite) item);

		}
	}

	private void markasnext() throws SQLException {
		if (tbBites.isFocused() || tbMiles.isFocused()) {
			bite currBite = getSelectedBite();
			if (currBite.getStatus() != bite.stCLOSED) {
				if (currBite.getNext() == bite.stNOTNEXT) {
					currBite.setNext(bite.stNEXT);
				} else {
					currBite.setNext(bite.stNOTNEXT);
				}
			}
			bDao.persist(currBite);
		}
	}

	private void gotoQuickAccessTask() throws IOException {
		
		   TextFileHandler handler = new TextFileHandler();
    		String rowStr = handler.getRowFromFile(DashboardFile, 4);
    		findAndSelectProjectByID(Integer.valueOf(rowStr), true);
    		tbTasks.requestFocus();
	}

	private void gotoQuickAccessTask2() throws IOException {
		
		   TextFileHandler handler = new TextFileHandler();
 		String rowStr = handler.getRowFromFile(DashboardFile, 7);
 		findAndSelectProjectByID(Integer.valueOf(rowStr), true);
 		tbTasks.requestFocus();
	}

	
	private void kickItDownTheRoad(todoitem item) throws SQLException {
		LocalDate today = LocalDate.now();
		LocalDate monday = LocalDate.now();
		LocalDate kickDate = LocalDate.now();

		monday = today.with(DayOfWeek.MONDAY);

		int i = 0;

		int min = 2;
		int max = 7;

		int weeks = (int) (Math.random() * ((max - min) + 1)) + min;
		int weekday = (int) (Math.random() * ((max - min) + 1)) + min;

		kickDate = today.plusDays(weekday - 2).plusWeeks(weeks);

		DayOfWeek day = DayOfWeek.of(kickDate.get(ChronoField.DAY_OF_WEEK));

		switch (day) {
		case SATURDAY:
			System.out.println("Weekend - Saturday");
			kickDate = kickDate.with(DayOfWeek.MONDAY);
		case SUNDAY:
			kickDate = kickDate.with(DayOfWeek.MONDAY);
		}

		setDeadline(item, kickDate);

	}

	private void quickReschedule(KeyCode keycode, todoitem item, boolean fromCurrentDate) throws SQLException {

		LocalDate currentDate = getDeadline(item).toLocalDate();
		LocalDate today = LocalDate.now();
		LocalDate baseDate = today;
		LocalDate monday;
		LocalDate newDate = today;

		if (fromCurrentDate) {
			baseDate = currentDate;
		}

		monday = baseDate.with(DayOfWeek.MONDAY);

		switch (keycode) {
		case DIGIT0:
			newDate = LocalDate.now();
			break; // Today
		case DIGIT1:
			newDate = baseDate.plusWeeks(1);
			break; // Next Week
		case DIGIT2:
			newDate = monday.plusDays(0);
			break; // Mon
		case DIGIT3:
			newDate = monday.plusDays(1);
			break; // Tue
		case DIGIT4:
			newDate = monday.plusDays(2);
			break; // Wed
		case DIGIT5:
			newDate = monday.plusDays(3);
			break; // Thu
		case DIGIT6:
			newDate = monday.plusDays(4);
			break; // Fri
		case DIGIT7:
			newDate = baseDate.plusMonths(1);
			break; // next month
		case DIGIT8:
			newDate = baseDate.plusMonths(3);
			break;
		case EQUALS:
			newDate = baseDate.plusDays(1);
			break;
		case MINUS:
			newDate = baseDate.plusDays(-1);
			break;
		}

		if (newDate.isBefore(today)) {
			newDate = newDate.plusDays(7);
		}

		setDeadline(item, newDate);
		this.statusText.set(WordUtils.capitalize(newDate.getDayOfWeek().toString().toLowerCase()));

	}

	private void pTroubleshoot() {
		Util.AlertMessagebox(String.valueOf(getCurrentTask().getTooverdue()));

	}

	private void deleteTask() throws SQLException {
		if (Util.YesNoDialogbox("Deletar Task", "Deseja realmente deletar essa tarefa?").get() == ButtonType.OK) {
			task t = getCurrentTask();
			int selected = tbTasks.getSelectionModel().getFocusedIndex();
			tDao.delete(getCurrentMaster().getId(), t.getId());
			wDao.deleteWaitsforTask(getCurrentMaster().getId(), t.getId());
			bDao.deleteBitesforTask(getCurrentMaster().getId(), t.getId());
			tbTasks.getItems().remove(t);
			refreshMaster();
			tbTasks.getSelectionModel().select(selected);
			// tbTasks.getSelectionModel().select(selected);
		}
	}

	private void deleteMaster() throws SQLException {
		if (Util.YesNoDialogbox("Deletar Master", "Deseja realmente deletar esse projeto e todas suas atividades?")
				.get() == ButtonType.OK) {
			master m = getCurrentMaster();
			mDao.delete(m.getId());
			tDao.deleteTasks(m.getId());
			wDao.deleteWaitsforMaster(m.getId());
			bDao.deleteBitesforMaster(m.getId());
			tbMasterTasks.getItems().remove(m);
		}
	}

	private void deleteBite() throws SQLException {
		if (Util.YesNoDialogbox("Deletar Bite", "Deseja realmente deletar esse bite").get() == ButtonType.OK) {
			int a = tbBites.getSelectionModel().getFocusedIndex();
			bite b = getCurrentBite();
			bDao.delete(b.getId());
			tbBites.getItems().remove(b);
			tbBites.getSelectionModel().select(a);
		}
	}

	private void deletePlanningBite() throws SQLException {
		if (Util.YesNoDialogbox("Delete deliverable", "Do you really want to delete this deliverable?")
				.get() == ButtonType.OK) {
			int a = tbMiles.getSelectionModel().getFocusedIndex();
			bite b = getSelectedBite();
			bDao.delete(b.getId());
			tbMiles.getItems().remove(b);
			tbMiles.getSelectionModel().select(a);
		}
	}

	private void changeBiteGoldenStatus() throws SQLException {

		boolean escalate = System.nanoTime() - this.lastBitePrioritization < 250000000;
		this.lastBitePrioritization = System.nanoTime();

		if (escalate) {
			int newPriority = getSelectedBite().getGolden() + 1;
			if (newPriority > 3) {
				newPriority = 0;
			}
			getSelectedBite().setGolden(newPriority);
		} else {
			if (getSelectedBite().getGolden() > 0) {
				getSelectedBite().setGolden(0);
			} else {
				getSelectedBite().setGolden(1);
			}

		}
		bDao.persist(getSelectedBite());
	}

	private void changeBiteStatus() throws SQLException {

		bite currBite = getSelectedBite();

		if (currBite.getStatus() == task.stOPEN) {
			currBite.setStatus(task.stDONE);
// 		currBite.setNext(bite.stNOTNEXT);
			currBite.setFinished(Date.valueOf(LocalDate.now()));
		} else {
			currBite.setStatus(task.stOPEN);
			currBite.setFinished(null);
		}
		bDao.persist(currBite);
	}

	private void changeTaskMark() throws SQLException {

		if (getCurrentTask().getMarked() == 1) {
			getCurrentTask().setMarked(0);
		} else {
			getCurrentTask().setMarked(1);
		}
		updateTaskCountStats(tbTasks.getItems());
		tDao.persist(getCurrentTask());
	}

	private void changeTaskGolden() throws SQLException {
		boolean escalate = System.nanoTime() - this.lastBitePrioritization < 250000000;
		this.lastBitePrioritization = System.nanoTime();

		if (escalate) {
			int newPriority = getCurrentTask().getGolden() + 1;
			if (newPriority > 2) {
				newPriority = 0;
			}
			getCurrentTask().setGolden(newPriority);
		} else {
			if (getCurrentTask().getGolden() > 0) {
				getCurrentTask().setGolden(0);
			} else {
				getCurrentTask().setGolden(1);
			}

		}
		tDao.persist(getCurrentTask());
	}

	private void addTask() throws IOException, SQLException {
		task t = null;
		editTask(t, Util.opINSERT);
	}

	private String removeLimiteChars(String text) {
		// remove first and last charactes
		return text.substring(1, text.length() - 1);
	}

	private String getQuickCopy(int line) throws IOException {
		TextFileHandler handler = new TextFileHandler();

		String rowStr = handler.getRowFromFile(DashboardFile, line);

		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(rowStr);
		clipboard.setContent(content);

		return rowStr;

	}

	private void getTextFromRelatedFile() throws IOException {
		// openNota(getCurrentFileName(2)); //nota

		TextFileHandler handler = new TextFileHandler();

		try {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
			content.putString((handler.readFile(getCurrentFileName(2))));
			clipboard.setContent(content);
		} catch (IOException ex) {
			Logger.getLogger(EditNotesController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void changeTask() throws IOException, SQLException {
		task t = getCurrentTask();

		// 2014m12 - Se for uma edição em pseudo task, localizar a tarefa de verdade

		if (getCurrentMaster().getId() > 0) {
			editTask(t, Util.opUPDATE);
		} else {
			goToTask(getCurrentTask().getMasterid(), getCurrentTask().getId());
		}
	}

	private void editTask(task t, int operation) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditTask.fxml"));
		Parent root = (Parent) loader.load();
		EditTaskController controller = (EditTaskController) loader.getController();
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");
		stage.initModality(Modality.APPLICATION_MODAL);
		controller.setStageAndSetupListeners(this, t, getCurrentMaster());
		controller.setOperation(operation);
		stage.show();
		controller.edName.requestFocus();
	}

	private void rescheduleTaskorBite() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reeschedule.fxml"));
		Parent root = (Parent) loader.load();
		ReescheduleController controller = (ReescheduleController) loader.getController();
		Stage stage = new Stage(StageStyle.UTILITY);
		Scene scene = new Scene(root);
		stage.setScene(scene);

		task t;
		bite b;

		if (tbTasks.isFocused()) {
			t = getCurrentTask();
			b = null;
		} else {
			t = null;
			b = getSelectedBite();
		}

		scene.getStylesheets().add("test.css");
		stage.initModality(Modality.APPLICATION_MODAL);
		controller.setStageAndSetupListeners(t, b, tDao, bDao);
		stage.show();
	}

	private void refreshScreen() throws SQLException, IOException {
		getData();
		focusAndSelect(tbMasterTasks);
	}

	private void openGlobalFind() throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/GlobalSearch.fxml"));
		Parent root = (Parent) loader.load();
		GlobalSearchController controller = (GlobalSearchController) loader.getController();

		Stage stage = new Stage(StageStyle.UTILITY);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("test.css");
		stage.setScene(scene);

		controller.setStageAndSetupListeners(this, stage, gsDao);
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();

		controller.textFind.requestFocus();
		// controller.textFind.setText(st);
		controller.textFind.end();
		controller.textFind.deselect();

	}

	private void openQuikFind(String st) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuickFind.fxml"));
		Parent root = (Parent) loader.load();
		QuickFindController controller = (QuickFindController) loader.getController();

		Stage stage = new Stage(StageStyle.UTILITY);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("test.css");
		stage.setScene(scene);

		if (tbMasterTasks.isFocused()) {
			controller.setStageAndSetupListeners(tbMasterTasks, st);
		} else {
			if (tbTasks.isFocused()) {
				controller.setStageAndSetupListeners(tbTasks, st);
			} else {
				if (tbBites.isFocused()) {
					controller.setStageAndSetupListeners(tbBites, st);
				} else {
					if (tbMiles.isFocused()) {
						controller.setStageAndSetupListeners(tbMiles, st);
					}
				}
			}
		}

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();

		controller.textFind.requestFocus();
		controller.textFind.setText(st);
		controller.textFind.end();
		controller.textFind.deselect();

	}

	private void addMaster() throws IOException, SQLException, ParseException {
		master m = null;
		editMaster(m, Util.opINSERT);
	}

	private void addBiteFromClipboard() throws IOException, SQLException, ParseException {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		String name = clipboard.getString();

		String[] parts = name.split("\n");
		for (String x : parts) {
			bite b = new bite(bDao.getnextid(), getCurrentTask().getMasterid(), getCurrentTask().getId(),
					x.replace("\n", "").replace("\r", ""), Util.DateToSQLDate(getCurrentTask().getDeadline()),
					task.stOPEN, bite.stNOTNEXT, 2, Util.DateToSQLDate(new java.util.Date()),
					Util.DateToSQLDate(new java.util.Date()), null, getCurrentMaster().getName(),
					getCurrentTask().getName(), getCurrentTask().getGolden(), 0);

			if (b.getName() != "") {
				tbBites.getItems().add(0, b);
				tbBites.getSelectionModel().select(0);
				bDao.persist(b);

				// logStatusChange("BITE CREATION", b.getName());
			}

		}
	}

	private void addBite() throws IOException, SQLException, ParseException {

		bite b = new bite(bDao.getnextid(), getCurrentTask().getMasterid(), getCurrentTask().getId(),
				Util.InputText("New Bite", "What is next action for this task?", "Bite:", getCurrentTask().getName()),
				Util.DateToSQLDate(getCurrentTask().getDeadline()), task.stOPEN, bite.stNOTNEXT, 2,
				Util.DateToSQLDate(new java.util.Date()), Util.DateToSQLDate(new java.util.Date()), null,
				getCurrentMaster().getName(), getCurrentTask().getName(), getCurrentTask().getGolden(), 0);

		if (b.getName() != "") {

			// 2019m11_23 - if d - , delivery -, w - is found in the string, tbmiles should
			// receive it instead

			if (b.getName().indexOf("d -") == -1 & b.getName().indexOf("deliverable -") == -1
					& b.getName().indexOf("w -") == -1) {

				tbBites.getItems().add(0, b);
				tbBites.getSelectionModel().select(0);
			} else {
				tbMiles.getItems().add(0, b);
				tbMiles.getSelectionModel().select(0);
			}
			bDao.persist(b);

			// logStatusChange("BITE CREATION", b.getName());
		}
	}


	public void selectTaskById(int id) {
		task t = null;
		for (int x = 0; x <= tbTasks.getItems().size() - 1; x++) {
			t = (task) tbTasks.getItems().get(x);
			if (t.getId() == id) {
				tbTasks.getSelectionModel().clearAndSelect(x);
				// tbTasks.scrollTo(x);
			}
		}

	}

	private void editMaster(master m, int operation) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditMaster.fxml"));
		Parent root = (Parent) loader.load();
		EditMasterController controller = (EditMasterController) loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");
		stage.initModality(Modality.APPLICATION_MODAL);

		controller.setStageAndSetupListeners(stage, tbMasterTasks, mDao, cDao, roDao, m);
		controller.setOperation(operation);
		stage.show();
		controller.edName.requestFocus();
	}

	private void changeTaskStatus(boolean towait, boolean parallel) throws ParseException, SQLException {
		int selected;
		selected = tbTasks.getSelectionModel().getSelectedIndex();

		task t;
		master m;

		m = getCurrentMaster();
		t = getCurrentTask();

		t.goNextTaskStatus(towait);

		if (t.getStatus() == task.stDONE || t.getStatus() == task.stWAITING) {
			long sessionMinutes;
			sessionMinutes = Util.diffInMinutes(t.getRestart(), t.getChangedon());
			wDao.addWorkSession(t.getMasterid(), t.getId(), t.getRestart(), t.getChangedon(), sessionMinutes);
			long waitminutes = wDao.getSumWait(m.getId(), t.getId());
			t.setMinutes(waitminutes);
			if (t.getStatus() == task.stDONE) {
				// logStatusChange("DONE", "session total time: " +
				// sessionMinutes + " minutes");
			} else {
				// logStatusChange("WAITING", "session total time: " +
				// sessionMinutes + " minutes");
			}
		} else {
			if (t.getStatus() == task.stWORKING) {
				// logStatusChange("WORKING", "");

				// 2014m11 - Ao colocar uma tarefa em execução, eu posso parar
				// todas as outras que estão rodando:
				// Iterator<master> it = runningMaster.iterator();
				if (!parallel) {
					{
						ArrayList<master> runningMaster_clone = new ArrayList<>();
						ArrayList<task> runningTask_clone = new ArrayList<>();

						runningMaster_clone.addAll(runningMaster);

						for (master it : runningMaster_clone) {
//							System.out.println("Running task " + it.getName());

							// Encontrando o projeto
							findAndSelectProjectByID(it.getId(), true);
							runningTask_clone.addAll(runningTask);

							for (task itt : runningTask_clone) {
								findAndSelectTaskByID(itt.getId(), true);
								changeTaskStatus(true, false);
							}

//							System.out.println(getCurrentMaster().getName());
						}
						goToTask(t.getMasterid(), t.getId());
					}
				}
			}
		}

		tDao.persist(t);

		maintainRunningTaskList(t);

		updateOverallStats();

		getTasks(getCurrentMaster().getId());

		refreshMaster();
		tbTasks.getSelectionModel().select(selected);

		if (t.getStatus() != task.stDONE) {
			findAndSelectTaskByID(t.getId(), true);
		}
	}

	public void updateOverallStats() throws SQLException {
		ResultSet rs = rDao.getTotalSummary();
		rs.next();
		textStatusOpen.setText(rs.getString("OPEN"));
		textStatusWaiting.setText(rs.getString("WAITING"));
		textStatusWorking.setText(rs.getString("WORKING"));

		spPaneWorking.getStyleClass().remove("working");
		if (rs.getInt("WORKING") > 0) {
			spPaneWorking.getStyleClass().add("working");
		}

		rs.close();
	}

	public void refreshMaster() throws SQLException {

		master m = getCurrentMaster();
		master m_new = getDBMaster(m);

		// Copy
		// copyFields(getDBMaster(m),m);
		tbMasterTasks.getItems().add(tbMasterTasks.getSelectionModel().getSelectedIndex(), m_new);
		tbMasterTasks.getItems().remove(m);

		// como sou obrigado a remover e adicionar na tableviewer, eu preciso
		// substituir tb na running list
		if (runningMaster.contains(m)) {
			runningMaster.remove(m);
			runningMaster.remove(m_new);
		}

		maintainRunningMasterList(m_new);

//		System.out.println("Array is now: " + runningMaster.size());

	}

	private void openDir(boolean create) throws SQLException, IOException {
		openDir(create, false);
	}

	private String openDir(boolean create, boolean onlyPath) throws SQLException, IOException {
		String path;
		String hPath = "";
		String tPath = "";
		String mPath = "";
		String cPath = "";
		String dirSeparator;

		if (Util.onWindows()) {
			dirSeparator = "\\";
		} else {
			dirSeparator = "/";
		}

		String newMasterDir = "";
		String newTaskDir = "";

		// boolean create = true;
		TextFileHandler file = new TextFileHandler();
		hPath = Util.Read("Config.ini", "HOME_DIR");
		cPath = cDao.GetCatPath(getCurrentMaster().getCategory());
		mPath = file.getPathFromNotes(getCurrentFileName(1));
		tPath = file.getPathFromNotes(getCurrentFileName(2));

		if (create) {
			cPath = cPath.isEmpty() ? hPath + dirSeparator + Util.LimpaFileName(getCurrentMaster().getCategory())
					: cPath;
			mPath = mPath.isEmpty()
					? cPath + dirSeparator
							+ Util.LimpaFileName((getCurrentMaster() != null ? getCurrentMaster().getName() : ""))
					: mPath;
			tPath = tPath.isEmpty()
					? mPath + dirSeparator
							+ Util.LimpaFileName((getCurrentTask() != null ? getCurrentTask().getName() : ""))
					: tPath;
		}

		if (TextFileHandler.isFile(tPath)) {
			tPath = mPath.isEmpty() ? cPath.isEmpty() ? hPath + tPath : cPath + dirSeparator + tPath
					: mPath + dirSeparator + tPath;
		}

		if (tbTasks.isFocused()) {
			path = tPath.isEmpty() ? (mPath.isEmpty() ? (cPath.isEmpty() ? (hPath) : cPath) : mPath) : tPath;
		} else {
			path = mPath.isEmpty() ? (cPath.isEmpty() ? (hPath) : cPath) : mPath;
		}

		if (create) {
			if (!TextFileHandler.DirExists(path)) {
				path = Util.CreateDir(path);
				if (!path.isEmpty()) {
				   file.InsertLineAtTop("[" + path + "]", getCurrentFileName(3));
				}
			} else {
				Path dfile = new File(path).toPath();

				if (Files.isRegularFile(dfile)) {
					path = dfile.getParent().toString();
				} else {
					System.out.println("Its NOT a file");
				}
			}
		}

		if (!path.isEmpty()) {
			final Clipboard clipboard = Clipboard.getSystemClipboard();
			final ClipboardContent content = new ClipboardContent();
	
			path = path + dirSeparator;
	
			path = StringUtils.replace(path, "\\\\", "\\");
	
			content.putString(path);
	
			clipboard.setContent(content);
	
			if (!onlyPath) {
				Util.OpenDir(path);
			}
		}
		return path;

	}

	private void fixDates() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FixDate.fxml"));
		Parent root = (Parent) loader.load();
		FixDateController controller = (FixDateController) loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");

		stage.initModality(Modality.APPLICATION_MODAL);

		controller.setStageAndSetupListeners(this, getCurrentWS(), wDao);

		stage.show();

	}

	private void openNota(String pfileName) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditNotes.fxml"));
		Parent root = (Parent) loader.load();
		EditNotesController controller = (EditNotesController) loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");

		stage.initModality(Modality.APPLICATION_MODAL);
		controller.setpFilename(pfileName);
		controller.setStageAndSetupListeners(stage);

		stage.show();
	}

	private void runPythonScriptFileFile(String pfileName) throws IOException {
		TextFileHandler file = new TextFileHandler();

//		System.out.println(file.getPythonScript(pfileName));
//		System.out.println(file.getPathFromNotes(pfileName));

		String pythonScript = file.getPythonScript(pfileName);

		if (!pythonScript.isEmpty()) {
			run(pythonScript);

		}
	}

	private void openURLfromNotes(String pfileName) {
		TextFileHandler file = new TextFileHandler();

		String url;
		try {
			url = file.getURLFromNotes(pfileName);
			if (!url.isEmpty()) {
				Util.openURL(url);
			}
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(String command) throws IOException {

//		System.out.println("Now running --->" + command);

//		   Process p = Runtime.getRuntime().exec("runas /profile /user:Administrator cmd.exe /c"  + command); 

//		Process p = Runtime.getRuntime().exec("c:/elevate Rundll32.exe " + command);

		ProcessBuilder p = new ProcessBuilder(new String[] { "cmd.exe", "/C", command });
//		ProcessBuilder p = new ProcessBuilder(new String[] { command});
		Process newProcess = p.start();

//		   Process p = Runtime.getRuntime().exec(command );

//	    	BufferedReader stdInput = new BufferedReader(new 
//			     InputStreamReader(p.);

//			BufferedReader stdError = new BufferedReader(new 
//			     InputStreamReader(p.getErrorStream()));

		// read the output from the command
//		System.out.println("Here is the standard output of the command:\n");
		String s = null;
//			while ((s = stdInput.readLine()) != null) {
//		System.out.println(s);
//			}

		// read any errors from the attempted command
//			System.out.println("Here is the standard error of the command (if any):\n");
//			while ((s = stdError.readLine()) != null) {
//			    System.out.println(s);
//			}

//			statusBarAnnoucement ("Automation complete.");

//			Util.AlertMessagebox(stdInput.toString());

	}

	private void openDashFile() throws IOException {
		openNota(DashboardFile);
	}

	private void openWinning() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Winning.fxml"));
		Parent root = (Parent) loader.load();
		WinningController controller = (WinningController) loader.getController();

		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.getStylesheets().add("test.css");

		stage.initModality(Modality.APPLICATION_MODAL);
		// controller.setpFilename(pfileName);
		// controller.setStageAndSetupListeners(stage);

		stage.show();
	}

	public void focusAndSelect(TableView tb) {
		tb.requestFocus();
		if (tb.getSelectionModel().getSelectedIndex() < 0 && tb.getItems().size() > 0) {
			tb.getSelectionModel().select(0);
		}
	}



	public void logTimeStamp() {
		TextFileHandler file = new TextFileHandler();

	}

}
