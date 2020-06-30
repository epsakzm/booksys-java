/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.domain ;

import booksys.presentation.CurrentState;
import booksys.storage.Recorder;

import java.sql.Date ;
import java.sql.Time ;
import java.util.* ;

public class BookingSystem
{
  // Attributes:

  Date currentDate ;
  Date today ;
  
  // Associations:

  Restaurant restaurant = null ;
  Vector currentBookings ;
  Booking selectedBooking ;

  // Singleton:
  
  private static BookingSystem uniqueInstance ;
  private static CurrentState state = CurrentState.getInstance();

  public static BookingSystem getInstance()
  {
    if (uniqueInstance == null) {
      uniqueInstance = new BookingSystem() ;
    }
    return uniqueInstance ;
  }

  private BookingSystem()
  {
    today = new Date(Calendar.getInstance().getTimeInMillis()) ;
    restaurant = new Restaurant() ;
  }

  // Observer: this is `Subject/ConcreteSubject'

  Vector observers = new Vector() ;

  public void addObserver(BookingObserver o)
  {
    observers.addElement(o) ;
  }
  
  public void notifyObservers()
  {
    Enumeration enums = observers.elements() ;
    while (enums.hasMoreElements()) {
      BookingObserver bo = (BookingObserver) enums.nextElement() ;
      bo.update() ;
    }
  }

  public boolean observerMessage(String message, boolean confirm)
  {
    BookingObserver bo = (BookingObserver) observers.elementAt(0) ;
    System.out.println(message);
    return bo.message(message, confirm) ;
  }
  
  // System messages:

  public void display(Date date)
  {
    currentDate = date ;
    currentBookings = restaurant.getBookings(currentDate) ;
    selectedBooking = null ;
    notifyObservers() ;
  }
  
  public String makeReservation(int covers, Date date, Time time, Time etime, int tno,
			      String name, String phone)
  {
    Booking b = null;
    if (!doubleBooked(time, etime, tno, null) && !overflow(tno, covers)) {
      b
	    = restaurant.makeReservation(covers, date, time, etime, tno, name, phone) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }
    return b.getMoreDetails();
  }
 
  public String makeWalkIn(int covers, Date date, Time time, Time etime, int tno)
  {
    Booking b = null;
    if (!doubleBooked(time, etime, tno, null) && !overflow(tno, covers)) {
      b = (WalkIn)restaurant.makeWalkIn(covers, date, time, etime, tno) ;
      currentBookings.addElement(b) ;
      notifyObservers() ;
    }

    return b.getMoreDetails();
  }

  public void logOut(){
    state.logOut();
  }

  public void selectBooking(int tno, Time time)
  {
    selectedBooking = null ;
    Enumeration enums = currentBookings.elements() ;
    while (enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      if (b.getTableNumber() == tno) {
	    if (b.getTime().before(time)
	        && b.getEndTime().after(time)) {
	        selectedBooking = b ;
	    }
      }
    }
    notifyObservers() ;
  }

  public void cancel()
  {

    if (selectedBooking != null) {
      if (observerMessage("Are you sure?", true)) {
        currentBookings.remove(selectedBooking);
        restaurant.removeBooking(selectedBooking);
        selectedBooking = null;
        notifyObservers();
      }
    }
  }
  
  public void recordArrival(Time time)
  {
    if (selectedBooking != null) {
      if (selectedBooking.getArrivalTime() != null) {
	observerMessage("Arrival already recorded", false) ;
      }
      else {
	selectedBooking.setArrivalTime(time) ;
	restaurant.updateBooking(selectedBooking) ;
	notifyObservers() ;
      }
    }
  }

  public void transfer(Time time, int tno) {
    String transLog = selectedBooking.getMoreDetails();
    if (selectedBooking != null) {
      if (selectedBooking.getTableNumber() != tno) {
        if (!doubleBooked(selectedBooking.getTime(), selectedBooking.getEndTime(), tno, selectedBooking)
                && !overflow(tno, selectedBooking.getCovers())) {
          selectedBooking.setTable(restaurant.getTable(tno));
          restaurant.updateBooking(selectedBooking);
          transLog += "\t\t|\n\t\tV"+ selectedBooking.getMoreDetails();
        }
      }
      notifyObservers();
    }
    Recorder.record().log("Reservation Transferred\n"+ transLog);
  }
  
  private boolean doubleBooked(Time startTime, Time endTime, int tno, Booking ignore)
  {
    boolean doubleBooked = false ;

//    Time endTime = (Time) startTime.clone() ;
//    endTime.setHours(endTime.getHours() + 2) ;
    
    Enumeration enums = currentBookings.elements() ;
    while (!doubleBooked && enums.hasMoreElements()) {
      Booking b = (Booking) enums.nextElement() ;
      if (b != ignore && b.getTableNumber() == tno
	  && startTime.before(b.getEndTime())
	  && endTime.after(b.getTime())) {
	doubleBooked = true ;
	observerMessage("Double booking!", false) ;
      }
    }
    return doubleBooked ;
  }
  
  private boolean overflow(int tno, int covers)
  {
    boolean overflow = false ;
    Table t = restaurant.getTable(tno) ;
      
    if (t.getPlaces() < covers) {
      overflow = !observerMessage("Ok to overfill table?", true) ;
    }
    return overflow ;
  }
  
  // Other Operations:

  public Date getCurrentDate()
  {
    return currentDate ;
  }

  public Enumeration getBookings()
  {
    return currentBookings.elements() ;
  }

  public Booking getSelectedBooking()
  {
    return selectedBooking ;
  }

  public static Vector getTableNumbers()
  {
    return Restaurant.getTableNumbers() ;
  }

}
