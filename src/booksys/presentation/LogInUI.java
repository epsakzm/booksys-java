package booksys.presentation;

import booksys.application.persistency.LoginMapper;
import booksys.storage.Recorder;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LogInUI extends JFrame {
    private Label mainLabel;
    private Label label1;
    private Label idLabel;
    private Label passLabel;
    private TextField idField;
    private JPasswordField passwordField;
    private Button logInButton;
    private Button exitButton;

    public LogInUI() {
        setTitle("Log In");

        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        LoginMapper.getInstance().setLogInUI(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Recorder.record().log("Program closed");
                System.exit(0);
            }
        });

        setSize(new Dimension(500, 300));
        getContentPane().setLayout(null);
        mainLabel = new Label("RESTAURANT BOOKING SYSTEM");
        mainLabel.setBounds(112, 35, 240, 25);
        getContentPane().add(mainLabel);

        label1 = new Label("need Login");
        label1.setBounds(169, 72, 432, 25);
        getContentPane().add(label1);

        idLabel = new Label("ID");
        idLabel.setBounds(87, 114, 73, 18);
        getContentPane().add(idLabel);

        idField = new TextField();
        idField.setBounds(183, 111, 116, 24);
        getContentPane().add(idField);
        idField.setColumns(10);

        passLabel = new Label("Password");
        passLabel.setBounds(87, 150, 73, 18);
        getContentPane().add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(183, 147, 116, 24);
        getContentPane().add(passwordField);

        exitButton = new Button("CLOSE");
        exitButton.setBounds(313, 199, 105, 27);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Recorder.record().log("Program Closed Without Log-In\n");
                System.exit(0);
            }
        });
        getContentPane().add(exitButton);

        logInButton = new Button("OK");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginMapper.getInstance().setIsloggedIn();
                if (CurrentState.getInstance().getLoggedIn()) {
                    CurrentState.getInstance().setLoginShow(false);
                    CurrentState.getInstance().setAppShow(true);
                    CurrentState.getInstance().setAppTitle();
                    Recorder.record().log(CurrentState.getInstance().getAdmin().getId() + " Logged In Successfully");
                } else {
                    label1.setText("Log In Failed");
                }
            }
        });
        logInButton.setBounds(194, 199, 105, 27);
        getContentPane().add(logInButton);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Recorder.record().log("Exit Without Log In");
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public TextField getIDField() {
        return idField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }
}
