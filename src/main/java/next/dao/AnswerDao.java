package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AnswerDao {
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public Answer save(Answer answer) throws SQLException {
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
        String sql = "SELECT id, writer, contents, questionId, createdAt, updatedAt FROM Answer WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setLong(1, id);
        RowMapper<Answer> rowMapper = resultSet -> Answer.builder()
                .id(resultSet.getLong("id"))
                .writer(resultSet.getString("writer"))
                .contents(resultSet.getString("contents"))
                .questionId(resultSet.getLong("questionId"))
                .createdAt(resultSet.getTimestamp("createdAt"))
                .updatedAt(resultSet.getTimestamp("updatedAt"))
                .build();
        return jdbcTemplate.queryForObject(sql, preparedStatementSetter, rowMapper);
    }

    public List<Answer> findByQuestionId(long questionId) throws SQLException {
        String sql = "SELECT id, writer, contents, questionId, createdAt, updatedAt FROM Answer WHERE questionId = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setLong(1, questionId);
        RowMapper<Answer> rowMapper = resultSet -> Answer.builder()
                .id(resultSet.getLong("id"))
                .writer(resultSet.getString("writer"))
                .contents(resultSet.getString("contents"))
                .questionId(resultSet.getLong("questionId"))
                .createdAt(resultSet.getTimestamp("createdAt"))
                .updatedAt(resultSet.getTimestamp("updatedAt"))
                .build();
        return jdbcTemplate.query(sql, preparedStatementSetter, rowMapper);
    }

    public void delete(long answerId) throws SQLException {
        String sql = "DELETE FROM Answer WHERE id = ?";
        jdbcTemplate.update(sql, answerId);
    }

    public void deleteByQuestionId(long questionId) throws SQLException {
        String sql = "DELETE FROM Answer WHERE questionId = ?";
        jdbcTemplate.update(sql, questionId);
    }
}
