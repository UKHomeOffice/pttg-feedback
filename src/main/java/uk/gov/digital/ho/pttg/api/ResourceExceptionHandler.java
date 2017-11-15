package uk.gov.digital.ho.pttg.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.gov.digital.ho.pttg.app.FeedbackException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ControllerAdvice
@Slf4j
public class ResourceExceptionHandler {

    @ExceptionHandler(FeedbackException.class)
    public ResponseEntity handle(FeedbackException e) {
        log.error("FeedbackException: {}", e);
        return errorResponse(e.getMessage(), INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> errorResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(message, httpHeaders(), httpStatus);
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        return headers;
    }
}