package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.dao.ProductDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;
import com.example.sexxop.model.domain.Purchase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ClientProductController {



    //SIDEBAR//
    @FXML
    private BorderPane bp;
    @FXML private AnchorPane ap;
    @FXML
    Button btnLogin;

    public ClientProductController() throws SQLException {
    }


    @FXML
    public void products(MouseEvent event) {
        bp.setCenter(ap);

    }
    @FXML
    public void login(ActionEvent event) throws IOException {
        Object evt = event.getSource();
        if(evt.equals(btnLogin)) {
            HelloApplication.setRoot("login");
        }
    }

    @FXML
    public void  clientPurchases(MouseEvent event) {
        loadPage("clientPurchases");
    }


    @FXML
    public void back2() throws IOException {
        HelloApplication.setRoot("Login");
    }


    /**

     Controlador del SideBar
     @param page*/
    public void loadPage(String page) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(page + ".fxml"));

            bp.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private TableView<ProductClass> tableViewProducts;
    @FXML
    private TableColumn<ProductClass, Integer> id;
    @FXML
    private TableColumn<ProductClass, String> name;
    @FXML
    private TableColumn<ProductClass, Double> price;

    @FXML
    private TableColumn<ProductClass, String> more;
private ObservableList<ProductClass> listProducts;

ProductDAO productsDao = new ProductDAO();
@FXML
private ComboBox<ProductClass> chooser;

    public void initialize ()throws  SQLException{
        listProducts = FXCollections.observableArrayList();

        this.id.setCellValueFactory(new PropertyValueFactory("id"));
        this.name.setCellValueFactory(new PropertyValueFactory("name"));
        this.price.setCellValueFactory(new PropertyValueFactory("price"));
        this.more.setCellValueFactory(new PropertyValueFactory("more"));

        generateTable();
        ProductDAO productDAO = new ProductDAO();
        List<ProductClass> product = productDAO.findAll();
        chooser.setItems(FXCollections.observableArrayList(product));
    }

    private void generateTable() throws SQLException {
        List<ProductClass> aux = productsDao.findAll();
        listProducts.setAll(aux);
        this.tableViewProducts.setItems(listProducts);
    }

    /*--------------------------------
    * COMPRA DE PRODUCTOS
    *----------------------------------
    * Aqui mostraremos un boton comprar
    * y se guardara
    * en la tabla compras.
    */




}
