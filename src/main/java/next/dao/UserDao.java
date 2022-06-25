package next.dao;

import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public User findByUserId(String userId) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, userId);
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

        };
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        return (User) jdbcTemplate.queryForObject(sql, preparedStatementSetter);
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

        };
        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>)jdbcTemplate.query(sql, preparedStatement -> {});
    }

    public void update(User user) throws SQLException {
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUserId());
        };
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };
        String sql = "UPDATE USERS SET PASSWORD=?, NAME=?, EMAIL=? WHERE USERID=?";
        jdbcTemplate.update(sql, preparedStatementSetter);
    }

    public static void truncate() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "TRUNCATE TABLE USERS";
            pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }
}
