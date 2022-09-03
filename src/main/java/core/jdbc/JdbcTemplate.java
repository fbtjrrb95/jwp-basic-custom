package core.jdbc;

import next.dao.PreparedStatementCreator;
import next.dao.PreparedStatementSetter;
import next.dao.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {
    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try (
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            preparedStatementSetter.setValues(pstmt);
            pstmt.executeUpdate();
        }
    }

    public void update(String sql, Object... parameters) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            pstmt.executeUpdate();
        }
    }

    public Long insert(String sql, Object... parameters) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setObject(i + 1, parameters[i]);
            }
            pstmt.executeUpdate();
            return pstmt.getGeneratedKeys().getLong(1);
        }
    }

    public Long update(PreparedStatementCreator psc) throws SQLException {
        ResultSet rs = null;
        try (Connection conn = ConnectionManager.getConnection()) {
            PreparedStatement ps = psc.createPreparedStatement(conn);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new SQLException("no data available");
        } finally {
            assert rs != null;
            rs.close();
        }
    }

    public <T> List<T> query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws SQLException {
        ResultSet rs = null;
        try (
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            preparedStatementSetter.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<T> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            return result;
        }
    }

    public <T> T queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper<T> rowMapper) throws SQLException {
        List<T> result = query(sql, preparedStatementSetter, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public void truncate() throws SQLException {
        String sql = "truncate table users";
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            pstmt.executeUpdate();
        }
    }

}
