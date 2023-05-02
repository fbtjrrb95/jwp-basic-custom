package next.service;

import next.dao.AnswerDao;
import next.model.Answer;

import java.sql.SQLException;

public class AnswerService {

    private final AnswerDao answerDao = new AnswerDao();
    private final QuestionService questionService = new QuestionService();

    public void delete(long answerId) {
        try {
            Answer answer = answerDao.findById(answerId);
            answerDao.delete(answerId);
            questionService.decreaseAnswerCount(answer.getQuestionId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
