package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alessandro
 */


import Util.Util;
import java.sql.Timestamp;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;
public class master extends todoitem {
    
    private final StringProperty reference = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final StringProperty rolename  = new SimpleStringProperty();
    private final IntegerProperty roleid = new SimpleIntegerProperty();    
    private final IntegerProperty countdown = new SimpleIntegerProperty();    
    
    
    private final IntegerProperty inactivity = new SimpleIntegerProperty();    
    private final FloatProperty perccomplete = new SimpleFloatProperty();
    private final IntegerProperty workcount = new SimpleIntegerProperty();
    private final IntegerProperty waitcount = new SimpleIntegerProperty();
    private final IntegerProperty donecount = new SimpleIntegerProperty();
    private final IntegerProperty opencount = new SimpleIntegerProperty(); //se opencount = -1 entao é novo
    private final IntegerProperty taskfortoday = new SimpleIntegerProperty();
    private final IntegerProperty taskoverdue = new SimpleIntegerProperty();
    private final LongProperty minutes = new SimpleLongProperty();
    private final ObjectProperty<Timestamp> lastupdate = new SimpleObjectProperty<Timestamp>();
    private final ObjectProperty<Timestamp> created = new SimpleObjectProperty<Timestamp>();
    
    

    
   
    public String getStatustext()
    {
        return (StringUtils.leftPad(Util.MinutesToHoursText((this.minutes.getValue())), 5, "0"));
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String value) {
        category.set(value);
    }

    public StringProperty categoryProperty() {
        return category;
    }
   
    
     public String getRoleName() {
        return rolename.get();
    }

    public void setRoleName(String value) {
        rolename.set(value);
    }

    public StringProperty rolenameProperty() {
        return rolename;
    }
   
    
    
    public int getCountdown() {
        return countdown.get();
    }

    public void setCountdown(int value) {
        countdown.set(value);
    }

    public IntegerProperty countdownProperty() {
        return countdown;
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

    
    
    public int getInactivity() {
        return inactivity.get();
    }

    public void setInactivity(int value) {
        inactivity.set(value);
    }

    public IntegerProperty inactivityProperty() {
        return inactivity;
    }
    
    
    public float getPerccomplete() {
        return perccomplete.get();
    }

    public void setPerccomplete(float value) {
        perccomplete.set(value);
    }

    public FloatProperty perccompleteProperty() {
        return perccomplete;
    }
    

    public int getWorkcount() {
        return workcount.get();
    }

    public void setWorkcount(int value) {
        workcount.set(value);
    }

    public IntegerProperty workcountProperty() {
        return workcount;
    }
    
    public int getWaitcount() {
        return waitcount.get();
    }

    public void setWaitcount(int value) {
        waitcount.set(value);
    }

    public IntegerProperty waitcountProperty() {
        return waitcount;
    }
    

    public int getDonecount() {
        return donecount.get();
    }

    public void setDonecount(int value) {
        donecount.set(value);
    }

    public IntegerProperty donecountProperty() {
        return donecount;
    }
    

    public int getOpencount() {
        return opencount.get();
    }

    public void setOpencount(int value) {
        opencount.set(value);
    }

    public IntegerProperty opencountProperty() {
        return opencount;
    }
    

    public int getTaskfortoday() {
        return taskfortoday.get();
    }

    public void setTaskfortoday(int value) {
        taskfortoday.set(value);
    }

    public IntegerProperty taskfortodayProperty() {
        return taskfortoday;
    }
    

    public int getTaskoverdue() {
        return taskoverdue.get();
    }

    public void setTaskoverdue(int value) {
        taskoverdue.set(value);
    }

    public IntegerProperty taskoverdueProperty() {
        return taskoverdue;
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

    
    public Timestamp getLastupdate() {
        return lastupdate.get();
    }

    public void setLastupdate(Timestamp value) {
        lastupdate.set(value);
    }

    public ObjectProperty lastupdateProperty() {
        return lastupdate;
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
    
    
    public master(int id, String name, Timestamp created) {
        this.setId(id);
        this.setName(name);
        this.created.setValue(created);
    }
    
    
    public master(int Id, 
                  String name, 
                  String category, 
                  int countdown,
                  int inactivity, 
                  float perccomplete,
                  long minutes, 
                  String reference,
                  int workcount,
                  int waitcount,
                  int donecount,
                  int opencount,
                  Timestamp lastupdate,
                  Timestamp created,
                  String rolename,
                  int roleid)
            {
                    this.setId(Id);
                    this.setName (name);
                    this.setCategory(category);
                    this.setLastupdate(lastupdate);
                    this.setCountdown(countdown);
                    this.setPerccomplete(perccomplete);
                    this.setReference(reference);
                    this.setWorkcount(workcount);
                    this.setWaitcount(waitcount);
                    this.setDonecount(donecount);
                    this.setOpencount(opencount);
                    this.setMinutes(minutes);
                    this.setCreated(created);
                    this.setRoleName(rolename);
                    this.setRoleid(roleid);
            }
}
