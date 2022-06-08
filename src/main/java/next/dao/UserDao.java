package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {

            @Override
            void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getUserId());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getName());
                preparedStatement.setString(4, user.getEmail());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public User findByUserId(String userId) throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

            @Override
            void setValues(PreparedStatement pstmt) {

            }
        };
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
        return (User) selectJdbcTemplate.queryForObject(sql);
    }

    private String createQueryForFindById() {
        return "SELECT userId, password, name, email FROM USERS WHERE userId=?";
    }

    public List<User> findAll() throws SQLException {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate() {
            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"));
            }

            @Override
            void setValues(PreparedStatement pstmt) {

            }

        };
        String sql = "SELECT userId, password, name, email FROM USERS";
        return (List<User>)selectJdbcTemplate.query(sql);
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {

            @Override
            void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, user.getPassword());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getUserId());
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return null;
            }
        };

        jdbcTemplate.update("UPDATE USERS SET PASSWORD=?, NAME=?, EMAIL=? WHERE USERID=?");
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
