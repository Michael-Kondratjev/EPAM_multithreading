package by.epam.riverport.exception;

public class RiverPortException extends Exception {

    public RiverPortException(String message) {
        super(message);
    }

    public RiverPortException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiverPortException(Throwable cause) {
        super(cause);
    }

}
