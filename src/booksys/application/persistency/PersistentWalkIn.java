/*
 * Restaurant Booking System: example code to accompany
 *
 * "Practical Object-oriented Design with UML"
 * Mark Priestley
 * McGraw-Hill (2004)
 */

package booksys.application.persistency ;

import booksys.application.domain.* ;
import java.sql.*;
class PersistentWalkIn extends WalkIn implements PersistentBooking
{
  private int oid ;

  public PersistentWalkIn(int id, int c, Date d,
			  Time t, Time et, Table tab)
  {
    super(c, d, t, et, tab) ;
    oid = id ;
  }

  /* public because getId defined in an interface and hence public */

  public int getId() {
    return oid ;
  }
}
