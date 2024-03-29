package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {

    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public void insert(User user) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public void insert(String userId, String password, String name, String email) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, userId, password, name, email);
    }

    public User findByUserId(String userId) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, userId);
        };
        RowMapper<User> rowMapper = resultSet -> new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        return jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }

    public List<User> findAll() throws SQLException {
        RowMapper<User> rowMapper = resultSet -> new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.query(sql, preparedStatement -> {}, rowMapper);
    }

    public void update(User user) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUserId());
        };
        String sql = "UPDATE USERS SET PASSWORD=?, NAME=?, EMAIL=? WHERE USERID=?";
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public void update(String userId, String email, String password, String name) throws SQLException {
        String sql = "UPDATE USERS SET PASSWORD=?, NAME=?, EMAIL=? WHERE USERID=?";
        jdbcTemplate.update(sql, password, name, email, userId);
    }

    public void truncate() throws SQLException {
        String sql = "TRUNCATE TABLE USERS";
        jdbcTemplate.update(sql);
    }

}
