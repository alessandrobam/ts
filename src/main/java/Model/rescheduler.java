/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.task;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 *
 * @author Alessandro
 */
public class rescheduler {

    private LocalDate date;
    private LocalDate originalDate;
    public DayOfWeek Next;

    public rescheduler() {
        this.date = LocalDate.now().plusDays(1);
    }

    public LocalDate getNext(DayOfWeek day) {
        return LocalDate.now().with(TemporalAdjusters.next(day));
    }

    public LocalDate getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(LocalDate originalDate) {
        this.originalDate = originalDate;
    }

    public LocalDate getInterval(String interval) {
        LocalDate retorno = LocalDate.now();
        switch (interval) {
            case "Today":
                retorno = LocalDate.now();
                break;
            case "Tomorrow":
                retorno = LocalDate.now().plusDays(1);
                break;
            case "In a Week":
                retorno = LocalDate.now().plusWeeks(1);
                break;
            case "In two Weeks":
                retorno = LocalDate.now().plusWeeks(2);
                break;
                
            case "In a Month":
                retorno = LocalDate.now().plusMonths(1);
                break;
            case "In a Quater":
                retorno = LocalDate.now().plusMonths(3);
                break;
            case "End of the Month":
                retorno = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
                break;  
        }
        return retorno;
    }
}