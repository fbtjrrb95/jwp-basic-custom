package next.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Question {

    private Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private Long answerCount;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

}
