package test;

import com.example.sexxop.model.domain.ClientClass;
import org.junit.jupiter.api.Test;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class ClientClassTest {
    @Test
    public void testObjectCreation() {
        ClientClass client = new ClientClass();
        assertEquals(0, client.getId());
        assertNull(client.getName());
        assertNull(client.getBirthday());
        assertNull(client.getUser_login());
        assertNull(client.getPassword_login());
    }

    @Test
    public void testObjectCreationWithArguments() {
        int id = 1;
        String name = "John Doe";
        Date birthday = new Date(323);
        String user_login = "johndoe";
        String password_login = "password";

        ClientClass client = new ClientClass(id, name, birthday, user_login, password_login);
        assertEquals(id, client.getId());
        assertEquals(name, client.getName());
        assertEquals(birthday, client.getBirthday());
        assertEquals(user_login, client.getUser_login());
        assertEquals(password_login, client.getPassword_login());
    }

    @Test
    public void testAttributeSettersAndGetters() {
        ClientClass client = new ClientClass();

        int id = 1;
        String name = "John Doe";
        Date birthday = new Date(3232);
        String user_login = "johndoe";
        String password_login = "password";

        client.setId(id);
        client.setName(name);
        client.setBirthday(birthday);
        client.setUser_login(user_login);
        client.setPassword_login(password_login);

        assertEquals(id, client.getId());
        assertEquals(name, client.getName());
        assertEquals(birthday, client.getBirthday());
        assertEquals(user_login, client.getUser_login());
        assertEquals(password_login, client.getPassword_login());
    }

    @Test
    public void testEquality() {
        int id = 1;
        String name = "John Doe";
        Date birthday = new Date(3232);
        String user_login = "johndoe";
        String password_login = "password";

        ClientClass client1 = new ClientClass(id, name, birthday, user_login, password_login);
        ClientClass client2 = new ClientClass(id, name, birthday, user_login, password_login);
        ClientClass client3 = new ClientClass();

        assertEquals(client1, client2);
        assertNotEquals(client1, client3);
    }

    @Test
    public void testToString() {
        int id = 1;
        String name = "John Doe";
        Date birthday = new Date(32323);
        String user_login = "johndoe";
        String password_login = "password";

        ClientClass client = new ClientClass(id, name, birthday, user_login, password_login);
        String expectedString = "ClientClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", user_login='" + user_login + '\'' +
                ", password_login='" + password_login + '\'' +
                '}';

        assertEquals(expectedString, client.toString());
    }

    @Test
    public void testHashCode() {
        int id = 1;
        String user_login = "johndoe";
        String password_login = "password";

        ClientClass client1 = new ClientClass(id, "John Doe", new Date(3232), user_login, password_login);
        ClientClass client2 = new ClientClass(id, "Jane Smith", new Date(3232), user_login, password_login);

        assertEquals(client1.hashCode(), client2.hashCode());
    }


}