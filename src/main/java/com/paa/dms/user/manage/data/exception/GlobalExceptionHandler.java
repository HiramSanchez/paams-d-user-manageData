package com.paa.dms.user.manage.data.exception;

import com.paa.dms.user.manage.data.constants.APIConstants;
import com.paa.dms.user.manage.data.exception.custom.ForbiddenException;
import com.paa.dms.user.manage.data.exception.custom.NoDataFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Data
@ControllerAdvice
/**
 * Global exception handler to manage exceptions thrown in the application.
 * This class centralizes the handling of specific exceptions and provides
 * consistent error responses to the client.
 */
public class GlobalExceptionHandler {
    @Autowired
    private APIConstants apiConstants;

/*
    // Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                apiConstants.getEXCEPTION_MSG_UNEXPECTED(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
*/

    /**
     * Handles validation exceptions thrown when request parameters fail to meet
     * validation criteria. It aggregates all field errors into a single error message.
     *
     * @param ex      the MethodArgumentNotValidException
     * @param request the current request context
     * @return ResponseEntity containing the error details and HTTP status 400 (Bad Request)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + error.getDefaultMessage())
                .reduce((error1, error2) -> error1 + ", " + error2)
                .orElse(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions thrown when a requested resource is not found.
     *
     * @param request the current request context
     * @return ResponseEntity containing the error details and HTTP status 404 (Not Found)
     */
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                apiConstants.getEXCEPTION_MSG_NO_DATA_FOUND(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions thrown when a forbidden action is attempted.
     *
     * @param request the current request context
     * @return ResponseEntity containing the error details and HTTP status 403 (Forbidden)
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                apiConstants.getEXCEPTION_MSG_FORBIDDEN(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }
}
