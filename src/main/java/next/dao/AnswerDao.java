package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;
import org.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AnswerDao {

    public Answer save(Answer answer) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO Answer (writer, contents, questionId, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?)";
        PreparedStatementCreator psc = con -> {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, answer.getWriter());
            pstmt.setString(2, answer.getContents());
            pstmt.setLong(3, answer.getQuestionId());
            pstmt.setTimestamp(4, answer.getCreatedAt());
            pstmt.setTimestamp(5, answer.getUpdatedAt());
            return pstmt;
        };

        Long generatedId = jdbcTemplate.update(psc);
        return findById(generatedId);
    }

    public Answer findById(long id) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        String sql = "SELECT id, writer, contents, questionId, createdAt, updatedAt FROM Answer WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setLong(1, id);
        };
        RowMapper<Answer> rowMapper = resultSet -> new Answer(
                resultSet.getLong("id"),
                resultSet.getString("writer"),
                resultSet.getString("contents"),
                resultSet.getLong("questionId"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt")
        );
        return jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }

    public List<Answer> findByQuestionId(long questionId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT id, writer, contents, questionId, createdAt, updatedAt FROM Answer WHERE questionId = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> {
            preparedStatement.setLong(1, questionId);
        };
        RowMapper<Answer> rowMapper = resultSet -> new Answer(
                resultSet.getLong("id"),
                resultSet.getString("writer"),
                resultSet.getString("contents"),
                resultSet.getLong("questionId"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt")
        );
        return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
    }
}
