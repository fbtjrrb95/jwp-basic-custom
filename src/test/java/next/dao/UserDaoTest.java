package next.dao;

import next.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest {

    @Before
    public void init() throws Exception {
        UserDao.truncate();
    }

    @Test
    public void create() throws Exception {
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        UserDao userDao = new UserDao();
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

    }

    @Test
    public void update() throws Exception {
        UserDao userDao = new UserDao();
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        userDao.insert(expected);

        String newName = "newName";
        expected.setName(newName);

        UserDao.update(expected.getUserId(), expected);

        User updated = userDao.findByUserId(expected.getUserId());
        assertEquals(updated.getName(), newName);
    }

    @Test
    public void findAll() throws Exception {

        UserDao userDao = new UserDao();

        User user1 = new User("userId1", "password", "name", "javajigi@email.com");
        userDao.insert(user1);

        User user2 = new User("userId2", "password", "name", "javajigi@email.com");
        userDao.insert(user2);


        List<User> users = UserDao.findAll();
        assertEquals(users.size(), 2);
        assertEquals(users.get(0).getUserId(), "userId1");
        assertEquals(users.get(1).getUserId(), "userId2");

    }

}
