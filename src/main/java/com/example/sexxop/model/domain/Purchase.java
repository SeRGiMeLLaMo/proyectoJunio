package com.example.sexxop.model.domain;

import java.sql.Date;
import java.util.Objects;

public class Purchase {
    private ClientClass client;
    private ProductClass product;
    private Date date;

    public Purchase() {
    }

    public Purchase(ClientClass client, ProductClass product, Date date) {
        this.client = client;
        this.product = product;
        this.date = date;
    }

    public ClientClass getClient() {
        return client;
    }

    public void setClient(ClientClass client) {
        this.client = client;
    }

    public ProductClass getProduct() {
        return product;
    }

    public void setProduct(ProductClass product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(client, purchase.client) && Objects.equals(product, purchase.product) && Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, product, date);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "client=" + client +
                ", product=" + product +
                ", date=" + date +
                '}';
    }
}
