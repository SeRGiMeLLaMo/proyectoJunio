package com.example.sexxop;

import com.example.sexxop.model.dao.ClientDAO;
import com.example.sexxop.model.domain.ClientClass;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
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


public class AdminClientConstroller implements Initializable {
    ClientDAO cdao = new ClientDAO();
    private List<ClientClass> myclient = cdao.findAll();
    private final ObservableList<ClientClass> listaActualizable = FXCollections.observableArrayList(myclient);




    @FXML
    private TableView<ClientClass> myTable;
    @FXML private TableColumn<ClientClass, Integer> id;
    @FXML private TableColumn<ClientClass, String> name;
    @FXML private TableColumn<ClientClass, Date> birthdate;
    @FXML private TableColumn<ClientClass, String> user_login;

    @FXML private TextField buscar;


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getAll();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        //METODO BUSCAR
        FilteredList<ClientClass> filteredData = new FilteredList<>(listaActualizable, e -> true);
        buscar.setOnKeyReleased(e -> {
            buscar.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredData.setPredicate((Predicate<? super ClientClass>) client-> {
                    if(newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    if (client.getName().contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(client.getBirthday()).contains(newValue)) {
                        return true;
                    }
                    if (client.getUser_login().contains(newValue)) {
                        return true;
                    }
                    if (String.valueOf(client.getId()).contains(newValue)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<ClientClass> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(myTable.comparatorProperty());
            myTable.setItems(sortedData);

        });

    }


    /**
     * Controlador que muestra los clientes en la tableView
     * @throws SQLException
     */
    @FXML
    private void getAll() throws SQLException {
        ClientDAO cdao = new ClientDAO();

        List<ClientClass> clients = cdao.findAll();
        if(myTable.getItems().isEmpty()) {
            id.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getId()));
            name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            birthdate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper(cellData.getValue().getBirthday()));
            user_login.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser_login()));
            myTable.getItems().addAll(clients);

        }
    }

}
