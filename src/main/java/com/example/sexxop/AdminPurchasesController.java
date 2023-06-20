package com.example.sexxop;

import com.example.sexxop.model.dao.PurchaseDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;
import com.example.sexxop.model.domain.Purchase;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminPurchasesController implements Initializable {
    /*
    Aqui realizaremos una tabla,
    como en la de admin client donde
    mostrara las compras de los clientes
    es igual que la clase AdminClient
    */

    PurchaseDAO pdao = new PurchaseDAO();
    private List<Purchase> mypurchases =(List<Purchase>) pdao.findAll();
    private final ObservableList<Purchase> listaActualizable = FXCollections.observableArrayList(mypurchases);


    @FXML
    private TableView<Purchase> myTable;
    @FXML private TableColumn<Purchase, ClientClass> client;
    @FXML private TableColumn<Purchase, ProductClass> product;
    @FXML private TableColumn<Purchase, Date> date;

    @FXML private TextField buscar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            allPurchase();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //METODO BUSCAR
        FilteredList<Purchase> filteredData = new FilteredList<>(listaActualizable, e -> true);
        buscar.setOnKeyReleased(e -> {
            buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super Purchase>) purchase-> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    if (purchase.getClient().getUser_login().contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(purchase.getProduct().getName()).contains(newValue)) {
                        return true;
                    }


                    return false;
                });
            });
            SortedList<Purchase> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(myTable.comparatorProperty());
            myTable.setItems(sortedData);

        });

    }

    /**
     * Metodo para mostrar en la tabla todos los campos de compra
     * @throws SQLException
     */
    public void allPurchase() throws SQLException {
        PurchaseDAO pdao = new PurchaseDAO();
        List<Purchase> tickets = pdao.findAll();
        if(myTable.getItems().isEmpty()) {
            client.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getClient().getUser_login()));
            product.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getProduct().getName()));
            date.setCellValueFactory(cellData -> {
                Date date = (Date) cellData.getValue().getDate();
                return new SimpleObjectProperty<Date>(date);
            });

            myTable.getItems().addAll(tickets);
        }
    }
}
