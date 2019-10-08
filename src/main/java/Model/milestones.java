/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author abarbosa
 */
public class milestones extends todoitem {
 private final IntegerProperty id = new SimpleIntegerProperty();

//    public int getId() {
//        return id.get();
//    }
//
//    public void setId(int value) {
//        id.set(value);
//    }
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
    
    public IntegerProperty idProperty() {
        return id;
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
   
        private Date created;

    /**
     * Get the value of created
     *
     * @return the value of created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * Set the value of created
     *
     * @param created new value of created
     */
    public void setCreated(Date created) {
        this.created = created;
    }
    
        private Date finished;

    /**
     * Get the value of finished
     *
     * @return the value of finished
     */
    public Date getFinished() {
        return finished;
    }

    /**
     * Set the value of finished
     *
     * @param finished new value of finished
     */
    public void setFinished(Date finished) {
        this.finished = finished;
    }
//
//
//    private String name;
//
//    /**
//     * Get the value of name
//     *
//     * @return the value of name
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Set the value of name
//     *
//     * @param name new value of name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }

    private final IntegerProperty status = new SimpleIntegerProperty();

    public int getStatus() {
        return status.get();
    }

    public void setStatus(int value) {
        status.set(value);
    }

    public IntegerProperty statusProperty() {
        return status;
    }
   
     public milestones(int Id, 
                   int masterid,
                   int taskid,
                   String name,
                   Date created,
                   Date finished,
                   int status,
                   int golden
                 )
                 {
                    this.setId(Id);
                    this.setMasterid(masterid);
                    this.setTaskid(taskid);
                    this.setCreated(created);
                    this.setName(name);
                    this.setStatus(status);
                    this.setGolden(golden);
                 }
    
}
