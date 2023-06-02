package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.domain.ClientClass;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Sign_upController {

    @FXML
    private TextField name;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @FXML
    public void add() throws SQLException {
        String nombre = name.getText();
        Date fecha = java.sql.Date.valueOf(birthdate.getValue());
        String usuario = username.getText();
        String contrasena = password.getText();

        ClientClass c = new ClientClass(nombre, fecha, usuario, contrasena);
        ClientDAO cdao = new ClientDAO();
        cdao.save(c);


            //Mensajes emergentes

        if (cdao.save(c) != null){

            @FXML
            HelloApplication.setRoot("Login");
        }else {

            @FXML
            HelloApplication.setRoot("Sign_up");
        }
    }

    @FXML
    public void back1() throws IOException {
        HelloApplication.setRoot("Login");
    }
    @FXML
    public void signup2() throws IOException {
        HelloApplication.setRoot("mensajesignup");
    }
}
