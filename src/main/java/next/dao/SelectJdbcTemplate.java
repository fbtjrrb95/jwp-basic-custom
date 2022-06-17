package next.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SelectJdbcTemplate {

    abstract Object mapRow(ResultSet rs) throws SQLException;
    abstract void setValues(PreparedStatement pstmt);
}
