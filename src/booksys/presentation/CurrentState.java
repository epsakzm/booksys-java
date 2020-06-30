package booksys.presentation;

import booksys.application.domain.Admin;
import booksys.storage.Recorder;
import org.omg.CORBA.Current;

import java.awt.*;

public class CurrentState {
    private Admin admin;
    private static boolean isLoggedIn = false;
    private static CurrentState instance;
    private static LogInUI loginUI;
    private static BookingSystemApp app;

    public static CurrentState getInstance() {

        if (instance == null) {
            instance = new CurrentState();
        }
        return instance;
    }

    public void setScene(Frame ui, BookingSystemApp ap) {
        loginUI = (LogInUI) ui;
        app = ap;
    }

    public void resetStatus(){
        admin = null;
        isLoggedIn = false;
    }

    public void setStatus(Admin ad) {
        if (ad != null) {
            admin = ad;
            isLoggedIn = true;
        }
    }

    public Admin getAdmin() {
        return admin;
    }

    public boolean getLoggedIn() {
        return isLoggedIn;
    }

    public void setAppShow(boolean show) {
        app.setVisible(show);
    }

    public void setLoginShow(boolean show) {
        loginUI.setVisible(show);
    }

    public void setAppTitle() {
        app.setTitle("RESTAURANT BOOKING SYSTEM (" + admin.getId() + ")");
    }

    public void logOut(){
        resetStatus();
        setAppShow(false);
        setLoginShow(true);
    }
}
