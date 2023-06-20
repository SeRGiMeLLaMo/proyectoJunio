package com.example.sexxop.singleton;

import com.example.sexxop.model.domain.ClientClass;

import java.sql.Date;

public class UserSession {
    private static ClientClass user1 = null;

    public static void login(int id, String name, Date birthday, String user_login) {
        user1 = new ClientClass(id, name, birthday, user_login);
    }

    public static void logout() {
        user1 = null;
    }

    public static boolean isLogged() {
        return user1==null?false:true;
    }

    public static ClientClass getUser() {
        return user1;
    }
}
