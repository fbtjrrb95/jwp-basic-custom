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
public class Answer {

    private Long id;
    private final String writer;
    private final String contents;
    private final Long questionId;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;
    
}
