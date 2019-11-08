package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class milestones extends todoitem {

	// next constants

	public final static int stNOTNEXT = 0; // Next
	public final static int stNEXT = 1; // Next

	// status constants
	public final static int stOPEN = 1;
	public final static int stPROGRESS = 2;
	public final static int stCLOSED = 3;

	// priority constands
	public final static int stFIRST = 0; // Tasks that needs completions
	public final static int stREGULAR = 1; // Plan
	public final static int stSTRECH = 2; // Plan

	private final StringProperty weekday = new SimpleStringProperty();
	private String taskname;
	private String mastername;

	/**
	 * Get the value of mastername
	 *
	 * @return the value of mastername
	 */
	public String getMastername() {
		return mastername;
	}

	/**
	 * Set the value of mastername
	 *
	 * @param mastername new value of mastername
	 */
	public void setMastername(String mastername) {
		this.mastername = mastername;
	}

	/**
	 * Get the value of taskname
	 *
	 * @return the value of taskname
	 */
	public String getTaskname() {
		return taskname;
	}

	/**
	 * Set the value of taskname
	 *
	 * @param taskname new value of taskname
	 */
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getWeekday() {
		// return weekday.get();
		return "Seguda";
	}

	public void setWeekday(String value) {
		weekday.set(value);
	}

	public StringProperty weekdayProperty() {
		return weekday;
	}

	private final IntegerProperty masterid = new SimpleIntegerProperty();

	public int getMasterid() {
		return masterid.get();
	}

	public void setMasterid(int value) {
		masterid.set(value);
	}

	public IntegerProperty masteridProperty() {
		return masterid;
	}

	private final IntegerProperty taskid = new SimpleIntegerProperty();

	public int getTaskid() {
		return taskid.get();
	}

	public void setTaskid(int value) {
		taskid.set(value);
	}

	public IntegerProperty taskidProperty() {
		return taskid;
	}

	private final ObjectProperty<Date> deadline = new SimpleObjectProperty<>();

	public Date getDeadline() {
		return deadline.get();
	}

	public void setDeadline(Date value) {
		deadline.set(value);
	}


	public ObjectProperty deadlineProperty() {
		return deadline;
	}

	
	private final ObjectProperty<Date> finished = new SimpleObjectProperty<>();

	public Date getFinished() {
		return finished.get();
	}

	public void setFinished(Date value) {
		finished.set(value);
	}


	private final IntegerProperty status = new SimpleIntegerProperty();

	public int getStatus() {
		// System.out.println("bite status" + status.get());
		return status.get();

	}

	public void setStatus(int value) {
		status.set(value);
	}

	public IntegerProperty statusProperty() {
		return status;
	}

	private final IntegerProperty next = new SimpleIntegerProperty();

	public int getNext() {
		return next.get();
	}

	public void setNext(int value) {
		next.set(value);
	}

	public IntegerProperty nextProperty() {
		return next;
	}

	private final IntegerProperty priority = new SimpleIntegerProperty();

	public int getPriority() {
		return priority.get();
	}

	public void setPriority(int value) {
		priority.set(value);
	}

	public IntegerProperty priorityProperty() {
		return priority;
	}

	private final ObjectProperty<Date> created = new SimpleObjectProperty<>();

	public Date getCreated() {
		return created.get();
	}

	public void setCreated(Date value) {
		created.set(value);
	}

	public ObjectProperty createdProperty() {
		return created;
	}

	private final ObjectProperty<Date> lastreschedule = new SimpleObjectProperty<>();

	public Date getLastreschedule() {
		return created.get();
	}

	public void setLastreschedule(Date value) {
		created.set(value);
	}

	public ObjectProperty lastrescheduleProperty() {
		return lastreschedule;
	}

	private final IntegerProperty golden = new SimpleIntegerProperty();

	public int getGolden() {
		return golden.get();
	}

	public void setGolden(int value) {
		golden.set(value);
	}

	public IntegerProperty goldenProperty() {
		return golden;
	}

	private final IntegerProperty countdown = new SimpleIntegerProperty();

	public int getCountdown() {
		return countdown.get();
	}

	public void setCountdown(int value) {
		countdown.set(value);
	}

	public IntegerProperty countdownProperty() {
		return countdown;
	}

	public milestones(int id, int masterid, int taskid, String name, Date deadline, int status, int next, int priority,
			Date created, Date lastreschedule, Date finished, String mastername, String taskname, int golden,
			int countdown) {
		super();
		this.setId(id);
		this.setMasterid(masterid);
		this.setTaskid(taskid);
		this.setName(name);
		this.setDeadline(deadline);
		this.setStatus(status);
		this.setNext(next);
		this.setPriority(priority);
		this.setCreated(created);
		this.setLastreschedule(lastreschedule);
		this.setMastername(mastername);
		this.setTaskname(taskname);
		this.setGolden(golden);
		this.setCountdown(countdown);
	}

	public void incReschedule(LocalDate prevDeadline, LocalDate newDeadline)
    {
      
//		getting numbers from existing bite text
		
		String biteName = this.getName();
    	int lo = this.getName().indexOf("[") ;
    	int hi = this.getName().indexOf("]") ;
    	int comma = this.getName().lastIndexOf(",");
    	boolean isFirstReschedule = false;
    	
    	int reschedules = 0;
    	int totalDays = 0;
    	
    	if (lo == - 1) { //true if task has never been reescheduled
    		isFirstReschedule = true;
    		lo = biteName.length(); 
    	} else
	    	{  if (comma == -1 || comma < lo) { //true if task using previous reescheduling format
	    		  reschedules = Integer.valueOf(this.getName().substring(lo+1, hi));
	    		} else //true if new format is used
	    			{
	    			   totalDays =  Integer.valueOf(this.getName().substring(comma+2,hi-1));
	    			   reschedules = Integer.valueOf(this.getName().substring(lo+1, comma));
	    			}
	    	}
    	
    	System.out.println("reeschedules: " + reschedules);
    	System.out.println("totalDays: " + totalDays);
    	
    	
    	
    	  Period duration = Period.between( prevDeadline, newDeadline);
    	  int daysAdded =  duration.getDays() + duration.getMonths() * 30 + duration.getYears() * 365;
    	  
    	  System.out.println("DaysAdded: " + totalDays);

    	  
    	  
    	  
    	  
    	if (this.getLastreschedule() != null &&  this.getLastreschedule().toLocalDate().compareTo(LocalDate.now()) != 0) { //True if reschedule happened
    		
    			reschedules = reschedules + 1;
    		}
    	
   		
    		if  (reschedules > 0) {
	    		totalDays = totalDays +  daysAdded;
	    		
	    		String rescheduleStamp = " [" + reschedules  + ", "+ totalDays  +"d]";  
	        	this.setName(this.getName().substring(0, lo-1) + rescheduleStamp )  ;
	        	this.setLastreschedule(Date.valueOf(LocalDate.now()));
    		}
    }
}
	
