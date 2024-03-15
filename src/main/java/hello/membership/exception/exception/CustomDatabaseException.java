package hello.membership.exception.exception;

import org.springframework.dao.DataAccessException;

public class CustomDatabaseException extends DataAccessException {
    public CustomDatabaseException(String msg) {
        super(msg);
    }

    public CustomDatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
