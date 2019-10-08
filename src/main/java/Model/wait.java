/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.Timestamp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;

public class wait {
    private final IntegerProperty id = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
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
    private final ObjectProperty<Timestamp> started = new SimpleObjectProperty<Timestamp>();

    public Timestamp getStarted() {
        return started.get();
    }

    public void setStarted(Timestamp value) {
        started.set(value);
    }

    public ObjectProperty startedProperty() {
        return started;
    }
    private final ObjectProperty<Timestamp> finshed = new SimpleObjectProperty<Timestamp>();

    public Timestamp getFinshed() {
        return finshed.get();
    }

    public void setFinshed(Timestamp value) {
        finshed.set(value);
    }

    public ObjectProperty finshedProperty() {
        return finshed;
    }
    private final LongProperty minutes = new SimpleLongProperty();

    public long getMinutes() {
        return minutes.get();
    }

    public void setMinutes(long value) {
        minutes.set(value);
    }

    public LongProperty minutesProperty() {
        return minutes;
    }

}
