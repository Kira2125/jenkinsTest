
import com.example.demo.dao.UserDAO;
import com.example.demo.model.User;
import org.junit.*;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.sql.*;

public class CRUDTest {
    private static User user1;
    private static User user2;
    private static UserDAO userDAO;

    @Before
    public void before() {
        user1 = new User();
        user1.setCountry("egypt");
        user1.setName("john");
        user1.setEmail("john@gmail.com");

        user2 = new User();
        user2.setCountry("germany");
        user2.setName("peter");
        user2.setEmail("peter@gmail.com");

        userDAO = new UserDAO("test");
    }

    @Test
    public void testInsert() throws SQLException {

        assertTrue(userDAO.insertUser(user1));

    }

    @Test
    public void testSelectAllUsers() throws SQLException {
        userDAO.insertUser(user1);
        userDAO.insertUser(user2);

        assertEquals(2, userDAO.selectAllUsers().size());
    }


    @Test
    public void testSelectUser() throws SQLException {
        userDAO.insertUser(user1);
        User user = userDAO.selectUser(0);
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getCountry(), user.getCountry());
        assertEquals(user1.getEmail(), user.getEmail());
        assertEquals(1, userDAO.selectAllUsers().size());

    }

    @Test
    public void testEditUser() throws SQLException {
        userDAO.insertUser(user1);
        user1.setName("johny");
        user1.setEmail("johny@gmail.com");
        user1.setCountry("rome");
        user1.setId(0);
        assertTrue(userDAO.updateUser(user1));
        User user = userDAO.selectUser(0);
        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getCountry(), user.getCountry());
        assertEquals(user1.getEmail(), user.getEmail());
    }



    @Test
    public void testDeleteUser() throws SQLException {

        userDAO.insertUser(user1);

        userDAO.deleteUser(0);
        assertEquals(0, userDAO.selectAllUsers().size());
    }


}
