package next.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import next.model.User;

public class UserDaoTest {

    private UserDao userDao = new UserDao();

    @Before
    public void init() throws Exception {
        UserDao.truncate();
    }

    @Test
    public void create() throws Exception {
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        userDao.insert(expected);

        User actual = userDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

    }

    @Test
    public void update() throws Exception {
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        userDao.insert(expected);

        String newName = "newName";
        expected.setName(newName);

        userDao.update(expected.getUserId(), expected);

        User updated = userDao.findByUserId(expected.getUserId());
        assertEquals(updated.getName(), newName);
    }

}
