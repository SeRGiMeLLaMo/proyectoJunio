package com.example.sexxop.model.dao;

import com.example.sexxop.model.connection.ConnectionMySQL;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;
import com.example.sexxop.model.domain.Purchase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    private final static String FINDALL = "SELECT * from purchase";
    private final static String PURCHASESBYCLIENT = "SELECT product.name, " +
            "product.price, product.more, purchase.date, client.user_login FROM purchase JOIN product ON " +
            "purchase.id_product = product.id JOIN client ON purchase.id_client = client.id WHERE client.id = ?";

    private final static String DELETEBYCLIENT = "DELETE FROM purchase WHERE id_client IN (SELECT id FROM client WHERE id = ?) LIMIT 1";
    private final static String INSERTPURCHASEBYCLIENT = "INSERT INTO purchase (id_client, id_product, date) VALUES (?, (SELECT id FROM product WHERE name = ? AND price = ? AND more=?), ?)";


    private Connection conn;

    public PurchaseDAO(Connection conn) {
        this.conn = conn;
    }

    public PurchaseDAO() {
        this.conn = ConnectionMySQL.getConnect();
    }


    public List<Purchase> findAll() {
        List<Purchase> result = new ArrayList<>();

        try (PreparedStatement pst = conn.prepareStatement(FINDALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    Purchase p = new Purchase();
                    p.setDate(res.getDate("date"));
                    ClientDAO cdao = new ClientDAO(this.conn);
                    ClientClass c = cdao.findById(res.getInt("id_client"));
                    p.setClient(c);

                    ProductDAO pdao = new ProductDAO();
                    ProductClass p1 = pdao.findById(res.getInt("id_product"));
                    p.setProduct(p1);


                    result.add(p);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * dao que muestra las compras de un cliente
     *
     * @param entity : el cliente
     * @return la lista de compras del propio cliente
     * @throws SQLException
     */
    public List<Purchase> purchaseByClient(ClientClass entity) throws SQLException {
        List<Purchase> result = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(PURCHASESBYCLIENT)) {
            pstmt.setInt(1, entity.getId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductClass product = new ProductClass();
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setMore(rs.getString("more"));
                    ClientClass client = new ClientClass();
                    client.setUser_login(rs.getString("user_login"));
                    Purchase purchase = new Purchase();
                    purchase.setDate(rs.getDate("date"));
                    purchase.setProduct(product);
                    purchase.setClient(client);
                    result.add(purchase);
                }
            }
        }
        return result;
    }

    /**
     * Dao que elimina lss compras de un cliente
     *
     * @param entity las compras
     * @throws SQLException
     */
    public void deletePurchaseByClient(Purchase entity) throws SQLException {
        try (PreparedStatement pst = this.conn.prepareStatement(DELETEBYCLIENT)) {
            pst.setInt(1, entity.getClient().getId());
            pst.executeUpdate();
        }
    }

    /**
     * dao que inserta un producto para un cliente
     *
     * @param entity
     * @return
     * @throws SQLException
     */
    public Purchase insertByClient(Purchase entity) throws SQLException {
        Purchase result = new Purchase();
        if (entity != null) {
            try (PreparedStatement pstmt = conn.prepareStatement(INSERTPURCHASEBYCLIENT)) {
                pstmt.setInt(1, entity.getClient().getId());
                pstmt.setString(2, entity.getProduct().getName());
                pstmt.setDouble(3, entity.getProduct().getPrice());
                pstmt.setString(4, entity.getProduct().getMore());
                pstmt.setDate(5, (Date) entity.getDate());
                pstmt.executeUpdate();
            }
            result = entity;
        }
        return result;

    }
}
