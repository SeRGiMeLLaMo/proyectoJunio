package com.example.sexxop.model.domain;

import java.util.Date;
import java.util.Objects;

public class ClientClass {
    private int id;
    private String name;
    private Date birthday;
    private String user_login;
    private String password_login;

    public ClientClass() {
    }

    public ClientClass(int id, String name, Date birthday, String user_login, String password_login) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.user_login = user_login;
        this.password_login = password_login;
    }

    public ClientClass(String nombre, Date fecha, String usuario, String contrasena) {
        this.name = nombre;
        this.birthday = fecha;
        this.user_login = usuario;
        this.password_login = contrasena;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getBirthday() {
        return (java.sql.Date) birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUser_login() {
        return user_login;
    }

    public void setUser_login(String user_login) {
        this.user_login = user_login;
    }

    public String getPassword_login() {
        return password_login;
    }

    public void setPassword_login(String password_login) {
        this.password_login = password_login;
    }

    @Override
    public String toString() {
        return "ClientClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", user_login='" + user_login + '\'' +
                ", password_login='" + password_login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientClass that = (ClientClass) o;
        return id == that.id && Objects.equals(user_login, that.user_login) && Objects.equals(password_login, that.password_login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_login, password_login);
    }
}

