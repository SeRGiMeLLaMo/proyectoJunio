package com.example.sexxop;

import com.example.sexxop.model.dao.PurchaseDAO;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.Purchase;
import com.example.sexxop.singleton.UserSession;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ClientPurchasesController implements Initializable {
    /*
    Aqui realizaremos una tabla,
    como en la de admin purchases donde
    mostrara tus compras es igual que
    la clase AdminPurchases pero
    debes añadir un boton eliminar
    para eliminar las compras.
    */

    @FXML
    private TableView<Purchase> tableViewPurchases;
    @FXML
    private TableColumn<Purchase, String> name;
    @FXML
    private TableColumn<Purchase, Double> price;
    @FXML
    private TableColumn<Purchase, String> more;
    @FXML
    private TableColumn<Purchase, Date> date;
    @FXML
    private TableColumn<Purchase, String> user_login;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
              more.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getMore()));
        price.setCellValueFactory(cellData -> {
            Double price = (Double) cellData.getValue().getProduct().getPrice();
            return new SimpleObjectProperty<Double>(price);
        });
        date.setCellValueFactory(cellData -> {
            Date date = (Date) cellData.getValue().getDate();
            return new SimpleObjectProperty<Date>(date);
        });
        user_login.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClient().getUser_login()));

        try {
            PurchaseDAO purchaseDAO = new PurchaseDAO();
            List<Purchase> tickets = purchaseDAO.purchaseByClient(UserSession.getUser());
            tableViewPurchases.getItems().addAll(tickets);
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar el error de la base de datos
            // ...
        }
    }
    /**
     * Metodo que elimina los tickets comprados por un usuario
     */
    @FXML
    private void deletePurchase() {
        if (tableViewPurchases.getSelectionModel().isEmpty()) {
            //Validates.alertError("ERROR", "ERROR EN MIS VIAJES", "Debes seleccionar un ticket.");
            // utils.alerta("Error", "", "Debes seleccionar un cliente");
        } else {
            Purchase selectedPurchase = tableViewPurchases.getSelectionModel().getSelectedItem();
            ClientClass selectedClientClass = selectedPurchase.getClient();

            PurchaseDAO ticketDAO = new PurchaseDAO();
            try {
                ticketDAO.deletePurchaseByClient(selectedPurchase);
            } catch (SQLException e) {
                // Manejar el error de SQLException según tus necesidades
            }

            // Eliminar el ticket de la tabla y actualizar la vista
            tableViewPurchases.getItems().remove(selectedPurchase);
        }
    }
}
