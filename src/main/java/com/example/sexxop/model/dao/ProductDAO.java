package com.example.sexxop.model.dao;

import com.example.sexxop.model.connection.ConnectionMySQL;
import com.example.sexxop.model.domain.ProductClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class ProductDAO {
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
}
