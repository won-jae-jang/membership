package hello.membership.exception;

import hello.membership.exception.exception.CustomDatabaseException;
import hello.membership.exception.exception.LoginFailException;
import hello.membership.exception.exception.NotAuthorizedUserException;
import hello.membership.exception.exception.UserException;
import hello.membership.exception.exhandler.ErrorResult;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalStateException.class)
    public ErrorResult illegalExHandle(IllegalStateException e) {
        log.error("[exceptionHandle] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ExceptionHandler(LoginFailException.class)
    public ErrorResult loginFailExHandle(LoginFailException e) {
        log.info("로그인 실패");
        return new ErrorResult("loginFail", e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedUserException.class)
    public ErrorResult notAuthorized(NotAuthorizedUserException e) {
        log.info("권한이 없는 사용자");
        return new ErrorResult("notAuthorized user", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CustomDatabaseException.class)
    public ErrorResult databaseExHandle(CustomDatabaseException e){
        log.info("서버측 데이터베이스 로직 문제");
        return new ErrorResult("server database error", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResult entityNotFoundExHandle(EntityNotFoundException e) {
        log.info("데이터 베이스에 없는 데이터를 조회하려고 시도");
        return new ErrorResult("not data found", e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.error("[exceptionHandle ex", e);
        return new ErrorResult("EX", "내부오류");
    }
}
