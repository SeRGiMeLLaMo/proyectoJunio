package com.example.sexxop.model.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class ConnectionMySQL {

    private String file = "connection.xml";
    private static ConnectionMySQL _newInstace;
    private static Connection con;

    private ConnectionMySQL() {
        ConnectionData cd = loadXML();
        try {

            con = DriverManager.getConnection(cd.getServer()+"/"+cd.getDatabase(),cd.getUsername(),cd.getPassword());
        } catch (SQLException e) {
            con = null;
            e.printStackTrace();
        }
    }

    public static Connection getConnect() {
        if(_newInstace == null) {
            _newInstace = new ConnectionMySQL();

        }
        return con;
    }

    public ConnectionData loadXML() {
        ConnectionData con = new ConnectionData();
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ConnectionData.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            con = ((ConnectionData)jaxbUnmarshaller.unmarshal(new File(file)));
        } catch (JAXBException e) {

            e.printStackTrace();
        }

        return con;
    }
}
