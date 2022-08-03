package next.dao;

import next.model.Question;

import java.sql.SQLException;

public class QuestionDao {

    public Question save(Question question) throws SQLException {
        String sql = "INSERT INTO QUESTION (writer, title, contents, createdAt, updatedAt) VALUES (?, ?, ?, ?)";
        JdbcTemplate jdbcTemplate = new JdbcTemplate() {};
        Long generatedId = jdbcTemplate.insert(
                sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCreatedAt(),
                question.getUpdatedAt());
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
}
