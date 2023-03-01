package gr.codehub.sacchon.app.exception;

public class RegisterValidationException extends Exception {

    public RegisterValidationException(String message) {
        super(message);
    }

    public RegisterValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
