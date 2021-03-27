
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
        user1.setCountry("egyptology");
        user1.setName("johnson");
        user1.setEmail("johnson@gmail.com");

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

        assertEquals(5, userDAO.selectAllUsers().size());
    }


    @Test
    public void testSelectUser() throws SQLException {
        User user = userDAO.selectUser(0);
        assertEquals("john", user.getName());
        assertEquals("egypt", user.getCountry());
        assertEquals("john@gmail.com", user.getEmail());

    }

    @Test
    public void testEditUser() throws SQLException {
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

        userDAO.deleteUser(0);
        assertEquals(4, userDAO.selectAllUsers().size());
    }


}
