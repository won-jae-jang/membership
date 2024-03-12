package hello.membership.exception.exception;

public class LoginFailException extends IllegalStateException{

    public LoginFailException() {
        super();
    }

    public LoginFailException(String s) {
        super(s);
    }

    public LoginFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailException(Throwable cause) {
        super(cause);
    }
}
