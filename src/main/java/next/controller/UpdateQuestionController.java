package next.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.util.ObjectMapperFactory;
import next.dao.QuestionDao;
import next.model.Question;
import next.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UpdateQuestionController extends QuestionDetailController {
    private final ObjectMapper objectMapper = ObjectMapperFactory.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long questionId = QuestionIdParser.apply("/qna/questions/", request.getRequestURI());

        Map<String, String> map = objectMapper.readValue(
                request.getInputStream(),
                new TypeReference<>() {});

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);
        question.setContents(map.get("contents"));
        Question updatedQuestion = questionDao.update(question);

        return jsonView().addObject("contents", updatedQuestion.getContents());
    }
}
