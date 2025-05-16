package by.antohakon.webservernotifications.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static class ErrorResponse {
        private String message;
        private Instant timestamp;
        private String errorType;

    private ErrorResponse(String message, Instant timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    private ErrorResponse(String errorType, String message, Instant timestamp) {
            this.errorType = errorType;
            this.message = message;
            this.timestamp = timestamp;
        }


    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getErrorType() {
        return errorType;
    }
    }

    // 404 - Не найдено (пользователь/сервис/подписка)
    @ExceptionHandler({
            UserNotFoundException.class,
            ServiceNotFoundException.class,
            SubscriptionNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        ex.getClass().getSimpleName(),
                        ex.getMessage(),
                        Instant.now()));
    }

    // 400 - Ошибка валидации (дубликат подписки)
    @ExceptionHandler({
            DuplicateSubscriptionException.class,
            DuplicateUserException.class
    })
    public ResponseEntity<ErrorResponse> handleDuplicateSubscription(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getClass().getSimpleName(),
                        ex.getMessage(),
                        Instant.now()));
    }

    // 500 - Все остальные непредвиденные ошибки
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {

        LOGGER.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ex.getClass().getSimpleName(),
                        ex.getMessage(),
                        Instant.now()
                ));
    }

}
