/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alessandro
 */
public class todoitem {
   private final StringProperty name = new SimpleStringProperty();
   private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty masterName = new SimpleStringProperty();

    public String getMasterName() {
        return masterName.get();
    }

    public void setMasterName(String value) {
        masterName.set(value);
    }

    public StringProperty masterNameProperty() {
        return masterName;
    }

  
  
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }
     public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }      
}
