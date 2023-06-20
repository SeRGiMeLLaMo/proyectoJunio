package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.utils.Validaciones;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;


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
    public void add() throws SQLException, IOException {
        String nombre = name.getText();
        Date fecha = java.sql.Date.valueOf(birthdate.getValue());
        String usuario = username.getText();
        String contrasena = password.getText();
        contrasena= Validaciones.encryptSHA256(contrasena);

        ClientClass c = new ClientClass(nombre, fecha, usuario, contrasena);
        ClientDAO cdao = new ClientDAO();
        cdao.save(c);


            //Mensajes emergentes


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
