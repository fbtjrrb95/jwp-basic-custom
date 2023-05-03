package next.service;

import next.dao.QuestionDao;

import java.sql.SQLException;

public class QuestionService {

    private final QuestionDao questionDao = new QuestionDao();

    public void increaseAnswerCount(long questionId) {
        try {
            questionDao.increaseAnswerCount(questionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void decreaseAnswerCount(long questionId) {
        try {
            questionDao.decreaseAnswerCount(questionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
