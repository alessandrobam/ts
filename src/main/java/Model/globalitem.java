/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author abarbosa
 */
public class globalitem {
        
        
        private int masterid;
        private int taskid;
        private int biteid;
        private String name;
        private String type;
        private String tag;
        private String master;

    public globalitem(int masterid, int taskid, int biteid, String name, String type, String tag, String master) {
        this.masterid = masterid;
        this.taskid = taskid;
        this.biteid = biteid;
        this.name = name;
        this.type = type;
        this.tag = tag;
        this.master = master;
    }

    public int getMasterid() {
        return masterid;
    }

    public void setMasterid(int masterid) {
        this.masterid = masterid;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getBiteid() {
        return biteid;
    }

    public void setBiteid(int biteid) {
        this.biteid = biteid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
   
    public String getTag() {
        return tag;
    }

    public void setTag(String type) {
        this.tag = tag;
    }      
    
    
    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.name = master;
    }

}
