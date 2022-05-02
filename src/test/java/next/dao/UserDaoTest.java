package next.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import next.model.User;

public class UserDaoTest {

    @Before
    public void init() throws Exception {
        UserDao.truncate();
    }

    @Test
    public void create() throws Exception {
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        UserDao.insert(expected);

        User actual = UserDao.findByUserId(expected.getUserId());
        assertEquals(expected, actual);

    }

    @Test
    public void update() throws Exception {
        User expected = new User("userId1", "password", "name", "javajigi@email.com");
        UserDao.insert(expected);

        String newName = "newName";
        expected.setName(newName);

        UserDao.update(expected.getUserId(), expected);

        User updated = UserDao.findByUserId(expected.getUserId());
        assertEquals(updated.getName(), newName);
    }

}
