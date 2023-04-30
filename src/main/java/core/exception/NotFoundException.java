package core.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RuntimeException {

    public int status = 404;
    public final String message;
}
