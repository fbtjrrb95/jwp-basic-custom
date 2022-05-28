package next.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {


    public void updateWithQuery(String sql) throws SQLException {
        try (
                Connection con = ConnectionManager.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {

            setValues(pstmt);
            pstmt.executeUpdate();
        }
    }


    abstract void setValues(PreparedStatement preparedStatement) throws SQLException;
}
