package com.example.sexxop.model.dao;

import com.example.sexxop.model.connection.ConnectionMySQL;
import com.example.sexxop.model.domain.ClientClass;
import com.example.sexxop.model.domain.ProductClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductDAO {

    private static final String INSERT = "INSERT INTO product (name, price, more) VALUES (?,?,?)";
    private static final String FIND_ALL = "SELECT * FROM product";
    private static final String FIND_BY_ID = "SELECT * FROM product WHERE id = ?";
    private static final String UPDATE = "UPDATE product SET name=?, price=?, more=? WHERE id=?";
    private static final String DELETE = "DELETE FROM product WHERE id=?";

    private static ProductDAO _instance;
    public static ProductDAO getInstance() {
        if (_instance == null){
            _instance = new ProductDAO();
        }
        return _instance;
    }
    private Connection conexion;

    public ProductDAO() {
        conexion = ConnectionMySQL.getConnect();
    }

    public Collection<ProductClass> getAll(){
        Collection<ProductClass> ProductList = new ArrayList<ProductClass>();
        String sql = "select id, name, price, more from product";
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()){
                ProductClass p = new ProductClass();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setMore(rs.getString(4));
                ProductList.add(p);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return ProductList;
    }



    public List<ProductClass> findAll() throws SQLException {
        List<ProductClass> result = new ArrayList<>();

        try (PreparedStatement pst = conexion.prepareStatement(FIND_ALL)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    ProductClass p = new ProductClass();
                    p.setId(res.getInt("id"));
                    p.setName(res.getString("name"));
                    p.setPrice(Double.parseDouble(String.valueOf(res.getDouble("price"))));
                    p.setMore(res.getString("more"));

                    result.add(p);
                }
            }
        }

        return result;
    }

    public ProductClass findById(Integer id) throws SQLException {
        ProductClass result = null;

        try (PreparedStatement pst = conexion.prepareStatement(FIND_BY_ID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    result = new ProductClass();
                    result.setName(res.getString("name"));
                    result.setPrice(res.getDouble("price"));
                    result.setMore(res.getString("more"));
                }
            }
        }

        return result;
    }

    public ProductClass save(ProductClass entity) throws SQLException {
        ProductClass result = new ProductClass();
        if (entity != null) {
            ProductClass p = findById(entity.getId());

            if (p == null) {
                // INSERT
                try (PreparedStatement pst = this.conexion.prepareStatement(INSERT)) {
                    pst.setString(1, entity.getName());
                    pst.setDouble(2, (Double) entity.getPrice());
                    pst.setString(3, entity.getMore());

                    pst.executeUpdate();
                }

            } else {
                // UPDATE
                try (PreparedStatement pst = this.conexion.prepareStatement(UPDATE)) {
                    pst.setString(1, entity.getName());
                    pst.setDouble(2, (Double) entity.getPrice());
                    pst.setString(3, entity.getMore());

                    pst.setInt(4, entity.getId());
                    pst.executeUpdate();
                }
            }
            result = entity;
        }
        return result;
    }

    /**
     * Dao que elimina el producto
     */


    public void delete(ProductClass entity) throws SQLException {
        try(PreparedStatement pst=this.conexion.prepareStatement(DELETE)){
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
        }

    }
}
