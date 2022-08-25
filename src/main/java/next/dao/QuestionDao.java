package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class QuestionDao {

    public Question save(Question question) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTION (writer, title, contents, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, question.getWriter());
            pstmt.setString(2, question.getTitle());
            pstmt.setString(3, question.getContents());
            pstmt.setTimestamp(4, question.getCreatedAt());
            pstmt.setTimestamp(5, question.getUpdatedAt());
            return pstmt;
        };

        Long generatedId = jdbcTemplate.update(psc);
        return findById(generatedId);
    }

    public Question findById(long id) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT id, writer, title, contents, createdAt, updatedAt FROM Question WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setLong(1, id);
        };
        RowMapper<Question> rowMapper = resultSet -> new Question(
                resultSet.getLong("id"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt")
        );
        return jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }

    public List<Question> findAll() throws SQLException {
        RowMapper<Question> rowMapper = resultSet -> new Question(
                resultSet.getLong("id"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt"))
                ;

        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT id, writer, title, contents, createdAt, updatedAt FROM QUESTION";
        return jdbcTemplate.query(sql, preparedStatement -> {}, rowMapper);
    }
}
