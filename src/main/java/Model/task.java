  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Util.Util;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alessandro
 */
public class task extends todoitem {

    public final static int stUNSET = -1;
    public final static int stWORKING = 0;
    public final static int stOPEN = 1;
    public final static int stWAITING = 2;
    public final static int stDONE = 3;

    
    private int masterid;
    
    private final StringProperty tag = new SimpleStringProperty();
    private final ObjectProperty<Date> deadline = new SimpleObjectProperty<Date>();
    private final IntegerProperty tooverdue = new SimpleIntegerProperty();
    private final IntegerProperty status = new SimpleIntegerProperty();
    private final ObjectProperty<Timestamp> start = new SimpleObjectProperty<>();
    private final ObjectProperty<Timestamp> restart = new SimpleObjectProperty<>();
    private final ObjectProperty<Timestamp> finish = new SimpleObjectProperty<>();
    private final ObjectProperty<Timestamp> wait = new SimpleObjectProperty<>();
    
    private final LongProperty minutes = new SimpleLongProperty();
    private final StringProperty reference = new SimpleStringProperty();
    private final IntegerProperty statusprev = new SimpleIntegerProperty();
    private final ObjectProperty<Timestamp> changedon = new SimpleObjectProperty<Timestamp>();
    private final ObjectProperty<Timestamp> created = new SimpleObjectProperty<Timestamp>();
    private final IntegerProperty marked = new SimpleIntegerProperty();
    private final IntegerProperty pinned = new SimpleIntegerProperty();
    private final IntegerProperty golden = new SimpleIntegerProperty();
    
    private final StringProperty rolename  = new SimpleStringProperty();
    private final IntegerProperty roleid = new SimpleIntegerProperty();    
    
    
    public String getRoleName() {
        return rolename.get();
    }

    public void setRoleName(String value) {
        rolename.set(value);
    }

    public StringProperty rolenameProperty() {
        return rolename;
    }
   
    
	
	public int getRoleid () {
        return roleid.get();
    }

    public void setRoleid(int value) {
        roleid.set(value);
    }

    public IntegerProperty roleidProperty() {
        return roleid;
    }

    public int getGolden() {
        return golden.get();
    }

    public void setGolden(int value) {
        golden.set(value);
    }

    public IntegerProperty goldenProperty() {
        return golden;
    }
    
    public int getMarked() {
        return marked.get();
    }

    public void setMarked(int value) {
        marked.set(value);
    }

    public IntegerProperty markedProperty() {
        return marked;
    }

    public int getPinned() {
        return pinned.get();
    }

    public void setPinned(int value) {
        pinned.set(value);
    }

    public IntegerProperty pinnedProperty() {
        return pinned;
    }
    
    public int getMasterid() {
        return masterid;
    }

    public void setMasterid(int masterid) {
        this.masterid = masterid;
    }

    

    public String getTag() {
        return tag.get();
    }

    public void setTag(String value) {
        tag.set(value);
    }

    public StringProperty tagProperty() {
        return tag;
    }
    

    public Date getDeadline() {
        return deadline.get();
    }

    public void setDeadline(Date value) {
        deadline.set(value);
        
        LocalDate today = LocalDate.now();  

            Period betweenDates = Period.between(today, deadline.getValue().toLocalDate());
        tooverdue.set(betweenDates.getDays());
    }

    public ObjectProperty deadlineProperty() {
        return deadline;
    }
    

    public int getTooverdue() {
        return tooverdue.get();
    }

    public void setTooverdue(int value) {
        tooverdue.set(value);
    }

    public IntegerProperty tooverdueProperty() {
        return tooverdue;
    }
    

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int value) {
        status.set(value);
    }

    public IntegerProperty statusProperty() {
        return status;
    }

    

    public Timestamp getStart() {
        return start.get();
    }

    public void setStart(Timestamp value) {
        start.set(value);
    }

    public ObjectProperty startProperty() {
        return start;
    }
    
    public Timestamp getFinish() {
        return finish.get();
    }

    public void setFinish(Timestamp value) {
        finish.set(value);
    }

    public ObjectProperty finishProperty() {
        return finish;
    }
    
    public Timestamp getRestart() {
        return restart.get();
    }
    
    public void setRestart(Timestamp value) {
        restart.set(value);
    }

    public ObjectProperty restartProperty() {
        return restart;
    }
    
    public Timestamp getWait() {
        return wait.get();
    }

    public void setWait(Timestamp value) {
        wait.set(value);
    }

    public ObjectProperty waitProperty() {
        return wait;
    }
    

    public long getMinutes() {
        return minutes.get();
    }

    public void setMinutes(long value) {
        minutes.set(value);
    }

    public LongProperty minutesProperty() {
        return minutes;
    }
    

    public String getReference() {
        return reference.get();
    }

    public void setReference(String value) {
        reference.set(value);
    }

    public StringProperty referenceProperty() {
        return reference;
    }
    

    public int getStatusprev() {
        return statusprev.get();
    }

    public void setStatusprev(int value) {
        statusprev.set(value);
    }

    public IntegerProperty statusprevProperty() {
        return statusprev;
    }
    

    public Timestamp getChangedon() {
        return changedon.get();
    }

    public void setChangedon(Timestamp value) {
        changedon.set(value);
    }

    public ObjectProperty changedonProperty() {
        return changedon;
    }
    
    
    public Timestamp getCreated() {
        return created.get();
    }

    public void setCreated(Timestamp value) {
        created.set(value);
    }

    public ObjectProperty createdProperty() {
        return created;
    }

     public task(int masterid, int id, String name, Timestamp created) {
        this.masterid = masterid;
        this.setId(id);
        this.setName(name);
        this.setCreated(created);
     }
    public task(int masterid, int id, String name, String tag, Date deadline, int too, Timestamp start, Timestamp restart, Timestamp finish, Timestamp wait, int status, long minutes, String reference, Timestamp changedon, Timestamp created, int pinned, int marked, int golden, String mastername, String rolename, int roleid) {
        this.masterid = masterid;
        this.setId(id);
        this.setName(name);
        this.setTag(tag);
        this.setDeadline(deadline);
        this.setTooverdue(too);
        this.setStart(start);
        this.setRestart(restart);
        this.setFinish(finish);
        this.setWait(wait);
        this.status.set(status);
        this.setMinutes(minutes);
        this.setReference(reference);
        this.setStatusprev(-1);
        this.setChangedon(changedon);
        this.setCreated(created);
        this.setMarked(marked);
        this.setPinned(pinned);
        this.setGolden(golden);
        this.setMasterName(mastername);
        this.setRoleName(rolename);
        this.setRoleid(roleid);
    }

    public long calcMinutesWithoutWaits() throws ParseException
    {
        long retorno=0;
        switch (this.getStatus()) {
            case stDONE:
                retorno=  (this.getFinish().getTime() - this.getStart().getTime())/60000;
                break;      
            case stWAITING:
                retorno= (this.getWait().getTime() - this.getStart().getTime())/60000;
                break;
        }
        return retorno;
    }
    
    public void goNextTaskStatus(boolean toWait) throws ParseException {
        this.setStatusprev(this.getStatus());
        this.setChangedon(Util.getCurrentDateMinute());
        switch (this.GetTextStatus()) {
            case "OPEN":
                this.status.set(getStatusFromText("WORKING"));
                this.setStart(Util.getCurrentDateMinute());
                this.setRestart(Util.getCurrentDateMinute());
                break;
            case "WORKING":
                if (toWait) {
                    this.status.set(getStatusFromText("WAITING"));
                    this.setWait(Util.getCurrentDateMinute());
                    this.setMinutes(this.calcMinutesWithoutWaits());
                } else {
                    this.status.set(getStatusFromText("DONE"));
                    this.setFinish(Util.getCurrentDateMinute());
                    this.setMinutes(this.calcMinutesWithoutWaits());
                }
                break;
            case "DONE":
                this.status.set(getStatusFromText("WORKING"));
                this.setFinish(null);
                this.setRestart(Util.getCurrentDateMinute());
                break;
            case "WAITING":
                this.status.set(getStatusFromText("WORKING"));
                this.setWait(null);
                this.setRestart(Util.getCurrentDateMinute());
                break;
        }
        
    }

    public String GetTextStatus() {
        /*0 - WORKING --> CLOSE (3)
         1 - OPEN   ---> WORKING (0)
         2 - WAITING --> WORKING (0)
         3 - CLOSE   --> WORKING (0)
         */
        String retorno = "";
        switch (this.getStatus()) {
            case 0:
                retorno = "WORKING";
                break;
            case 1:
                retorno = "OPEN";
                break;
            case 2:
                retorno = "WAITING";
                break;
            case 3:
                retorno = "DONE";
                break;
        }
        return retorno;
    }

    public static int getStatusFromText(String statusText) {
       /*0 - WORKING --> CLOSE (3)
         1 - OPEN   ---> WORKING (0)
         2 - WAITING --> WORKING (0)
         3 - CLOSE   --> WORKING (0)
         */
        int retorno = 0;
        switch (statusText) {
            case "WORKING":
                retorno = 0;
                break;
            case "OPEN":
                retorno = 1;
                break;
            case "WAITING":
                retorno = 2;
                break;
            case "DONE":
                retorno = 3;
                break;
        }
        return retorno;
    }
    
   
    
    public void incReschedule()
    {
       //getting current reschedule count template [X]

    	int lo = this.getName().indexOf("[") ;
    	int hi = this.getName().indexOf("]") ;
    	
    	
    	if (this.getCreated() != null &&  this.getCreated().toLocalDateTime().toLocalDate().compareTo(LocalDate.now()) != 0) {
        	int reschedules = 0;
        	
        	if (lo == -1){
        		this.setName(this.getName() + " [1]");    		
        	}else
        	{
        		reschedules = Integer.valueOf(this.getName().substring(lo+1, hi))+1;
        		this.setName(this.getName().substring(0, lo ).trim() + " ["+ reschedules  +"]"); 
        	}

    	}
    }
    

}
