package com.marcosvidolin.jokenpo.rest.api.error;

import com.marcosvidolin.jokenpo.domain.exception.BusinessException;
import com.marcosvidolin.jokenpo.domain.exception.ModelAlreadyExistsException;
import com.marcosvidolin.jokenpo.domain.exception.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

/**
 * General API exception handler.
 */
@RestControllerAdvice()
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<?> getStandardErrorHandler(final HttpStatus httpStatus
            , final String message, final HttpServletRequest request) {

        ErrorMessageResponseResource errorMessage = new ErrorMessageResponseResource(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                request.getRequestURI());

        return ResponseEntity.status(httpStatus.value()).body(errorMessage);
    }

    /**
     * Handle all not found exceptions.
     *
     * @param ex the exception
     * @param request the given request
     * @return returns a message with error
     */
    @ExceptionHandler(value = {ModelNotFoundException.class})
    public ResponseEntity<?> handleBusiness(ModelNotFoundException ex, HttpServletRequest request) {
        return this.getStandardErrorHandler(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    /**
     * Handle all {@link BusinessException} exception.
     *
     * @param ex the exception
     * @param request the given request
     * @return returns a message with error
     */
    @ExceptionHandler(value = {BusinessException.class, ModelAlreadyExistsException.class})
    public ResponseEntity<?> handleBusiness(Exception ex
            , HttpServletRequest request) {
        String message = ex.getCause().getLocalizedMessage();
        return this.getStandardErrorHandler(HttpStatus.BAD_REQUEST, message, request);
    }

    /**
     * Handle all {@link ConstraintViolationException} exception.
     *
     * @param ex the exception
     * @param request the given request
     * @return returns a message with error
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex
            , HttpServletRequest request) {
        return this.getStandardErrorHandler(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

}