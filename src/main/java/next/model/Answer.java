package next.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {

    private final String writer;
    private final String contents;
    private final Long questionId;
    
}
