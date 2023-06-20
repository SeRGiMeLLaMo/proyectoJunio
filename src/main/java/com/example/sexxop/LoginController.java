package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.singleton.UserSession;
import com.example.sexxop.utils.Validaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Button btnLogin;

    private ClientDAO cdao= new ClientDAO();

    @FXML
    private void goMenu() {

    }
    @FXML
    public void signUp() throws IOException {
        HelloApplication.setRoot("Sign_up");
    }

    @FXML
    private void eventAtion(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if (evt.equals(btnLogin)) {
            if (!txtUser.getText().isEmpty() && !txtPassword.getText().isEmpty()) {
                if (txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")) {
                    System.out.println("entro");
                    HelloApplication.setRoot("adminProducts");
                } else {
                    String user = txtUser.getText();
                    String password = txtPassword.getText();
                    password = Validaciones.encryptSHA256(password);
                    boolean state = cdao.login(user, password);
                    if (state) {
                        ClientClass loggedUser = cdao.getUserByUser(user); // Obtener los datos completos del usuario
                        UserSession.login(loggedUser.getId(), loggedUser.getName(), loggedUser.getBirthday(), loggedUser.getUser_login());
                        HelloApplication.setRoot("clientProduct");
                    } else {
                        //mensaje de error scene builder
                    }
                }
            } else {
                //mensaje de error scene builder
            }
        }
    }
}
