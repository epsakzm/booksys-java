package booksys.application.persistency;

import booksys.application.domain.Admin;
import booksys.storage.Database;
import booksys.storage.Recorder;
import booksys.presentation.CurrentState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import booksys.presentation.LogInUI;

public class LoginMapper {
    private static LoginMapper uniqueInstance;
    private static Admin admin;
    private static LogInUI loginUI;

    public static LoginMapper getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LoginMapper();
        }
        return uniqueInstance;
    }
    private LoginMapper(){

    }
    public void setIsloggedIn() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = Database.getInstance().getConnection().createStatement();
            rs = stmt.executeQuery("select userID, unhex(password) from admin");
            while(rs.next()){
                String id = rs.getString(1);
                String pass = rs.getString(2);
                if(id.equals(getId()) && pass.equals(getHexPass())){
                    admin = new Admin(id, pass);
                    CurrentState.getInstance().setStatus(admin);
                    break;
                }else{
                    Recorder.record().log(getId() + " tried to log in!");
                }
            }
            if(stmt!=null)
                stmt.close();
            if(rs!=null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLogInUI(LogInUI ui) {
        loginUI = ui;
    }

    private String getId(){
        String tmp = loginUI.getIDField().getText().toString();
        return tmp;
    }

    private String getHexPass(){
        StringBuffer sb = new StringBuffer(1000);
        sb.append(loginUI.getPasswordField().getPassword());
        return sb.toString();
    }
}
