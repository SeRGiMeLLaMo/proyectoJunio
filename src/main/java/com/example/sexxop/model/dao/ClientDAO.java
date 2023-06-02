package com.example.sexxop.model.dao;

import com.example.sexxop.model.connection.ConnectionMySQL;
import com.example.sexxop.model.domain.ClientClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private static final String INSERT = "INSERT INTO client (name, birthdate, user_login, password_login) VALUES (?,?,?,?)";
    private static final String FIND_ALL = "SELECT * FROM client";
    private static final String FIND_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String UPDATE = "UPDATE client SET name=?, birthdate=?, user_login=?, password_login=? WHERE id=?";

    private Connection conn;

    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    public ClientDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    public boolean login(String user, String contrasena) {
        boolean logeado = false;

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM cliente WHERE usuario=? AND contrasena=?")) {
            ps.setString(1, user);
            ps.setString(2, contrasena);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    logeado = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción
        }

        return logeado;
    }

    public List<ClientClass> findAll() throws SQLException {
        List<ClientClass> result = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    ClientClass u = new ClientClass();
                    u.setName(res.getString("name"));
                    u.setBirthday(res.getDate("birthdate"));
                    u.setUser_login(res.getString("username"));
                    u.setPassword_login(res.getString("password"));

                    result.add(u);
                }
            }
        }

        return result;
    }

    public ClientClass findById(Integer id) throws SQLException {
        ClientClass result = null;

        try (PreparedStatement pst = conn.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new ClientClass();
                    result.setName(res.getString("name"));
                    result.setBirthday(res.getDate("birthdate"));
                    result.setUser_login(res.getString("username"));
                    result.setPassword_login(res.getString("password"));
                }
            }
        }

        return result;
    }
    public ClientClass save(ClientClass entity) throws SQLException {
        ClientClass result = new ClientClass();
        if (entity != null) {
            ClientClass c = findById(entity.getId());

            if (c == null) {
                // INSERT
                try (PreparedStatement pst = this.conn.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName());
                    pst.setDate(2, entity.getBirthday());
                    pst.setString(3, entity.getUser_login());
                    pst.setString(4, entity.getPassword_login());
                    pst.executeUpdate();
                }

            } else {
                // UPDATE
                try (PreparedStatement pst = this.conn.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setDate(2, entity.getBirthday());
                    pst.setString(3, entity.getUser_login());
                    pst.setString(4, entity.getPassword_login());

                    // Solo actualizar la contraseña si se proporcionó una nueva contraseña
                    if (entity.getPassword_login() != null && !entity.getPassword_login().isEmpty()) {
                        pst.setString(4, entity.getPassword_login());
                    } else {
                        pst.setString(4, c.getPassword_login());
                    }

                    pst.setInt(5, entity.getId());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

}