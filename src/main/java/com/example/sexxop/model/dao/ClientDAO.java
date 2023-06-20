package com.example.sexxop.model.dao;

import com.example.sexxop.model.connection.ConnectionMySQL;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.singleton.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private static final String INSERT = "INSERT INTO client (name, birthdate, user_login, password_login) VALUES (?,?,?,?)";
    private static final String FIND_ALL = "SELECT * FROM client";
    private static final String FIND_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String UPDATE = "UPDATE client SET name=?, birthdate=?, user_login=?, password_login=? WHERE id=?";
    private static final String LOGIN = "SELECT * FROM client WHERE user_login=? and password_login= ? ";
    private Connection conn;

    public ClientDAO(Connection conn) {
        this.conn = conn;
    }

    public ClientDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }

    /**
     * Funcion que comprueba si los datos introducidos en el login
     * son correctos o no
     * @param user Usuario introducido en la ventana de login
     * @param password Contraseña introducida en la ventana de Login
     * @return Devuelve true si el usuario se encuentra en la base de datos
     * o false si no.
     */
    public boolean login(String user, String password) {
        boolean logeado = false;
        if (this.conn != null) {
            try (PreparedStatement ps = this.conn.prepareStatement(LOGIN)){
                ps.setString(1, user);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    Date birthdate = rs.getDate("birthdate");
                    String user_login = rs.getString("user_login");
                    // Establecer los datos del usuario en UserSession
                    UserSession.login(id, name, birthdate, user_login);
                    logeado = true;
                } else {
                    logeado = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                //JOptionPane.showMessageDialog(null,"Hubo un error en la ejecucion:\n"+e.getMessage());
            }
        }
        return logeado;
    }

    /**
     * Dao que me devuelve los datos del usuario(cliente) logueado
     * @param username
     * @return
     */
    public ClientClass getUserByUser(String username) {
        String query = "SELECT * FROM client WHERE user_login = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Date birthdate = rs.getDate("birthdate");
                String user_login = rs.getString("user_login");
                String password_login = rs.getString("password_login");
                // Crea y devuelve un objeto Consumer con los datos obtenidos
                return new ClientClass(id, name, birthdate, user_login, password_login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encontró el usuario, devuelve null
    }

    public List<ClientClass> findAll() {
        List<ClientClass> result = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    ClientClass u = new ClientClass();
                    u.setId(res.getInt("id"));
                    u.setName(res.getString("name"));
                    u.setBirthday(res.getDate("birthdate"));
                    u.setUser_login(res.getString("user_login"));
                    u.setPassword_login(res.getString("password_login"));

                    result.add(u);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    result.setUser_login(res.getString("user_login"));
                    result.setPassword_login(res.getString("password_login"));
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