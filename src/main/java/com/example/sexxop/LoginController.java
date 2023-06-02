package com.example.sexxop;


import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class  LoginController {

    @FXML
    private Button btnlogin;


    @FXML
    private void goMenu() {

    }
    @FXML
    public void signUp() throws IOException {
        HelloApplication.setRoot("Sign_up");
    }



}
