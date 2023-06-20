package com.example.sexxop;

import com.example.sexxop.model.dao.ProductDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminProductsController implements Initializable {


    //SIDEBAR
    @FXML
    private BorderPane bp;
    @FXML private AnchorPane ap;
    @FXML
    Button btnLogin;

    public AdminProductsController() throws SQLException {
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
    public void  adminClients(MouseEvent event) {
        loadPage("adminClients");
    }

    @FXML
    public void adminPurchase (MouseEvent event){
        loadPage("adminPurchases");
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
    //PRODUCTOS

    ProductDAO pDao = new ProductDAO();
    private List<ProductClass> myproducts =(List<ProductClass>) pDao.findAll();
    private final ObservableList<ProductClass> listaActualizable = FXCollections.observableArrayList(myproducts);

    @FXML private TableView<ProductClass> mytable;
    @FXML private TableColumn<ProductClass, Integer> id;
    @FXML private TableColumn<ProductClass, String> name;
    @FXML private TableColumn<ProductClass, String> more;
    @FXML private TableColumn<ProductClass, Double> price;

    @FXML private TextField buscar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.allProduct();
        mytable.setItems(listaActualizable);
        //METODO BUSCAR
        FilteredList<ProductClass> filteredData = new FilteredList<>(listaActualizable, e -> true);
        buscar.setOnKeyReleased(e -> {
            buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super ProductClass>) products-> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (products.getName().contains(newValue)) {
                        return true;
                    }
                    if (products.getMore().contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(products.getPrice()).contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(products.getId()).contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ProductClass> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(mytable.comparatorProperty());
            mytable.setItems(sortedData);

        });
    }




    /**
     * Controlador que muestra la tabla en el tableView
     * Ademas edita el producto
     */

    private void allProduct() {
        //id
        id.setCellValueFactory(products ->{
            ObservableValue<Integer> ov = new SimpleIntegerProperty().asObject();
            ((ObjectProperty<Integer>) ov).setValue(products.getValue().getId());
            return ov;
        });
        //name
        name.setCellValueFactory(products ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(products.getValue().getName());
            return ssp;
        });
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductClass, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductClass, String> t) {
                ProductClass selected = (ProductClass) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setName(t.getNewValue());
                try {
                    pDao.save(selected);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        //More
        more.setCellValueFactory(products ->{
            SimpleStringProperty ssp = new SimpleStringProperty();
            ssp.setValue(products.getValue().getMore());
            return ssp;
        });
        more.setCellFactory(TextFieldTableCell.forTableColumn());
        more.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductClass, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductClass, String> t) {
                ProductClass selected = (ProductClass) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setMore(t.getNewValue());
                try {
                    pDao.save(selected);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        //Price
        price.setCellValueFactory(products ->{
            SimpleDoubleProperty ssp = new SimpleDoubleProperty();
            ssp.setValue(products.getValue().getPrice());
            return ssp.asObject();
        });
        //price.setCellFactory(TextFieldTableCell.forTableColumn());
       /*price.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductClass, Double>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ProductClass, Double> t) {
                ProductClass selected = (ProductClass) t.getTableView().getItems().get(t.getTablePosition().getRow());
                selected.setPrice(t.getNewValue());
                try {
                    pDao.save(selected);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });*/
        mytable.setEditable(true);
        mytable.getSelectionModel().cellSelectionEnabledProperty().set(true);
    }
    @FXML
    public void insert() throws IOException {
        HelloApplication.setRoot("registrarProductos");
    }

    /**

     Metodo que elimina de la tabla */

    @FXML
    private void deleteProduct() {
        if(mytable.getSelectionModel().isEmpty()) {// utils.alerta("Error", "", "Debes seleccionar un cliente");

            //Validaciones.alertError("ERROR", "ERROR IN PRODUCTS", "You need to select a product.");

            }else {
            try {
                pDao.delete(mytable.getSelectionModel().getSelectedItem());} catch (SQLException e) {
                e.printStackTrace();}
            listaActualizable.remove(mytable.getSelectionModel().getSelectedItem());}}
}
