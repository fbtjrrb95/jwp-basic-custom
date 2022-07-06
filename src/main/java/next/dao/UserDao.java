package next.dao;

import next.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
    public void insert(User user) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, preparedStatementSetter);
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        return (User) jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }

    public List<User> findAll() throws SQLException {
        RowMapper<User> rowMapper = resultSet -> new User(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email"));

        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>) jdbcTemplate.query(sql, preparedStatement -> {}, rowMapper);
    }

    public void update(User user) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUserId());
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "UPDATE USERS SET PASSWORD=?, NAME=?, EMAIL=? WHERE USERID=?";
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public void truncate() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        jdbcTemplate.truncate();
    }

}
