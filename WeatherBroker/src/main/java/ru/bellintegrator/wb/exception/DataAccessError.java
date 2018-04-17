package ru.bellintegrator.wb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Хмель А.В.
 * class DataAccessError
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataAccessError extends Exception {
    public DataAccessError() {
        super();
    }

    public DataAccessError(String message) {
        super(message);
    }

    public DataAccessError(String message, Throwable cause) {
        super(message, cause);
    }

    public DataAccessError(Throwable cause) {
        super(cause);
    }

    public DataAccessError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
