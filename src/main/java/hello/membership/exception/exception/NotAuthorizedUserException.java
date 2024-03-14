package hello.membership.exception.exception;

import java.nio.file.AccessDeniedException;

public class NotAuthorizedUserException extends AccessDeniedException {
    public NotAuthorizedUserException(String file) {
        super(file);
    }

    public NotAuthorizedUserException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
