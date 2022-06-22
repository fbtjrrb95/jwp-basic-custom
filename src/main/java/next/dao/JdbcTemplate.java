package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {

    public void update(String sql) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {

            setValues(pstmt);
            pstmt.executeUpdate();
        }
    }

    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            preparedStatementSetter.setValues(pstmt);
            pstmt.executeUpdate();
        }
    }

    @SuppressWarnings("rawtypes")
    public List query(String sql) throws SQLException {

        ResultSet rs = null;
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        }
    }

    @SuppressWarnings("rawTypes")
    public Object queryForObject(String sql) throws SQLException {
        List result = query(sql);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
    abstract Object mapRow(ResultSet rs) throws SQLException;
}
