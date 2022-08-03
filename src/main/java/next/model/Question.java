package next.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Question {

    private Long id;
    private final String writer;
    private final String title;
    private final String contents;
    private final Timestamp createdAt;
    private Timestamp updatedAt;

}
