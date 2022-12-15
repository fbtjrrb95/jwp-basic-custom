package next.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@ToString
public class Question {

    private Long id;
    private final String writer;
    private final String title;
    @Setter
    private String contents;
    private Long answerCount;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
}
