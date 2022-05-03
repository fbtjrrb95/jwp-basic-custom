package next.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import next.model.User;

import java.util.List;

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

    @Test
    public void findAll() throws Exception {

        User user1 = new User("userId1", "password", "name", "javajigi@email.com");
        UserDao.insert(user1);

        User user2 = new User("userId2", "password", "name", "javajigi@email.com");
        UserDao.insert(user2);


        List<User> all = UserDao.findAll();
        assertEquals(all.size(), 2);

    }

}
