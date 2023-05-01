package core.exception;

public class NotFoundException extends RuntimeException {

    public int status = 404;

    public String message = "Not Found";

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
