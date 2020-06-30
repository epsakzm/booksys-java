/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain;

import java.sql.Date;
import java.sql.Time;

public abstract class BookingImp implements Booking {
    protected int covers;
    protected Date date;
    protected Time time;
    //add endTime
    protected Time endTime;
    protected Table table;

    public BookingImp(int c, Date d, Time t, Time et, Table tab) {
        covers = c;
        date = d;
        time = t;
        endTime = et;
        table = tab;
    }

    public Time getArrivalTime() {
        return null;
    }

    public int getCovers() {
        return covers;
    }

    public Date getDate() {
        return date;
    }

    // End time defaults to 2 hours after time of booking

    public Time getEndTime() {
//    Time endTime = (Time) time.clone() ;
//    endTime.setHours(endTime.getHours() + 2) ;
        return endTime;
    }

    public Time getTime() {
        return time;
    }

    public Table getTable() {
        return table;
    }

    public int getTableNumber() {
        return table.getNumber();
    }

    public void setArrivalTime(Time t) {
    }

    public void setCovers(int c) {
        covers = c;
    }

    public void setDate(Date d) {
        date = d;
    }

    public void setTime(Time t) {
        time = t;
    }

    public void setTable(Table t) {
        table = t;
    }

    public String getMoreDetails() {
        if (this != null) {
            StringBuffer buf = new StringBuffer(1000);
            buf.append("\n\tDate: ");
            buf.append(getDate().toString() + "\n");
            buf.append("\tTime: ");
            buf.append(getTime().toString() + "\n");
            buf.append("\tEndTime: ");
            buf.append(getEndTime().toString() + "\n");
            buf.append("\tTable Number: ");
            buf.append(getTable().getNumber() + "\n");
            buf.append("\tCovers: ");
            buf.append(getCovers() + "\n");
            return buf.toString();
        }
        return "getDetailsNull";
    }

}
