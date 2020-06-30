package booksys.application.domain;

public class Admin {
    private String id;
    private String hexPass;
    private boolean loggedIn = false;

    public Admin(String id1, String pass){
        id = id1;
        hexPass = pass;
    }
    public String getId(){
        return id;
    }
    private String getPassword(){
        return hexPass;
    }
}
