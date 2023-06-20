package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.dao.ProductDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;
import com.example.sexxop.utils.Validaciones;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class RegisterProductController {


    @FXML
    private TextField name;

    @FXML
    private TextField price;


    @FXML
    private TextArea more;

    @FXML
    public void add() throws SQLException, IOException {
        String nombre = name.getText();
        Double precio = Double.valueOf(price.getText());
        String descripcion = more.getText();

        ProductClass p = new ProductClass(nombre, precio, descripcion);
        ProductDAO pdao = new ProductDAO();
        pdao.save(p);
    }

    @FXML
    public void back1() throws IOException {
        HelloApplication.setRoot("adminProducts");
    }
    @FXML
    public void signup2() throws IOException {
        HelloApplication.setRoot("mensajesignup");
    }
}
