package next.dao;

import core.jdbc.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class JdbcTemplate {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);
    public void update(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)
        ) {
            logger.info("update statement: " + pstmt);
            preparedStatementSetter.setValues(pstmt);
            pstmt.executeUpdate();
        }
    }

    public List query(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        ResultSet rs = null;
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            preparedStatementSetter.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        }
    }

    public List query(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper rowMapper) throws SQLException {
        ResultSet rs = null;
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
        ){
            preparedStatementSetter.setValues(pstmt);

            rs = pstmt.executeQuery();

            List<Object> result = new ArrayList<>();
            while (rs.next()) {
                result.add(rowMapper.mapRow(rs));
            }
            return result;
        }
    }


    @SuppressWarnings("rawTypes")
    public Object queryForObject(String sql, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        List result = query(sql, preparedStatementSetter);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @SuppressWarnings("rawTypes")
    public Object queryForObject(String sql, PreparedStatementSetter preparedStatementSetter, RowMapper rowMapper) throws SQLException {
        List result = query(sql, preparedStatementSetter, rowMapper);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    abstract Object mapRow(ResultSet rs) throws SQLException;
}
