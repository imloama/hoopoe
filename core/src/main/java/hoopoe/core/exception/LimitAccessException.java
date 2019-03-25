package hoopoe.core.exception;

public class LimitAccessException extends Exception {

    public LimitAccessException(String message){
        super(message);
    }

    public LimitAccessException(String message, Throwable cause){
        super(message, cause);
    }

}
