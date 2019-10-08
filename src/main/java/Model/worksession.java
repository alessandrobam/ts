/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import Util.Util;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Alessandro
 */
    public class worksession{
        private String id;
        private int taskid;
        private int masterid;
        private String project;
        private String task;
        private String date;
        private String pweekday;
        private String start;
        private String finish;
        private String total;
        private String rolename;

    
    public void setMasterid(int masterid) {
        this.masterid = masterid;
    }

        public worksession(String id, int taskid, int masterid, String project, String task, String date, String pweekday, String start, String finish, String total, String rolename) {
            this.id = id;
            this.taskid = taskid;
            this.masterid = masterid;
            
            this.project = project;
            this.task = task;
            this.date = date;
            this.pweekday = pweekday;
            this.start = start;
            this.finish = finish;
            this.total = total;
            this.rolename = rolename;
        }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

        
     public String getTotaltext()
    {
        return (StringUtils.leftPad(Util.MinutesToHoursText(Long.valueOf(this.getTotal())), 5, "0"));
    }
        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public String getTask() {
            return task;
        }

        public void setTask(String task) {
            this.task = task;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
        
        public String getPweekday() {
            return pweekday;
        }

        public void setPweekday(String weekday) {
            this.pweekday = pweekday;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getFinish() {
            return finish;
        }

        public void setFinish(String finish) {
            this.finish = finish;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public int getMasterid() {
        return masterid;
    }
  
        
    }

