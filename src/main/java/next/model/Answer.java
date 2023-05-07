package next.model;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Builder
@ToString
public class Answer {

    private Long id;
    private final String writer;
    private final String contents;
    private final Long questionId;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
    
}
