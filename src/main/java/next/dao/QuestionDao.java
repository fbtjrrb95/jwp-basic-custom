package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class QuestionDao {

    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public Question save(Question question) throws SQLException {
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

    public Question update(Question question) throws SQLException {
        Objects.requireNonNull(question.getWriter());
        Objects.requireNonNull(question.getTitle());
        Objects.requireNonNull(question.getContents());
        Objects.requireNonNull(question.getId());

        String sql = "UPDATE QUESTION SET writer=?, title=?, contents=?, updatedAt=? WHERE id=?";
        jdbcTemplate.update(
                sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                Timestamp.from(Instant.now()),
                question.getId());

        return question;
    }

    public Question findById(long id) throws SQLException {
        String sql = "SELECT id, writer, title, contents, answerCount, createdAt, updatedAt FROM Question WHERE id = ?";
        PreparedStatementSetter preparedStatementSetter = preparedStatement -> preparedStatement.setLong(1, id);

        RowMapper<Question> rowMapper = resultSet -> new Question(
                resultSet.getLong("id"),
                resultSet.getString("writer"),
                resultSet.getString("title"),
                resultSet.getString("contents"),
                resultSet.getLong("answerCount"),
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
                resultSet.getLong("answerCount"),
                resultSet.getTimestamp("createdAt"),
                resultSet.getTimestamp("updatedAt"));

        String sql = "SELECT id, writer, title, contents, answerCount, createdAt, updatedAt FROM QUESTION";
        return jdbcTemplate.query(sql, preparedStatement -> {}, rowMapper);
    }

    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM Question WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void increaseAnswerCount(long id) throws SQLException {
        String sql = "UPDATE QUESTION SET answerCount = answerCount + 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void decreaseAnswerCount(long id) throws SQLException {
        String sql = "UPDATE QUESTION SET answerCount = answerCount - 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
