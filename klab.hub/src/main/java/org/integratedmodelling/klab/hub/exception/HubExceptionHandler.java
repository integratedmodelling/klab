package org.integratedmodelling.klab.hub.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for catching and formatting {@link HubException} responses.
 */
@ControllerAdvice
public class HubExceptionHandler {

    /**
     * Handles {@link HubException} and returns a structured JSON response.
     *
     * @param ex the thrown HubException
     * @return ResponseEntity with error details and proper HTTP status
     */
    @ExceptionHandler(HubHttpException.class)
    public ResponseEntity<Object> handleHubException(HubHttpException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("message", ex.getMessage());
        body.put("error", ex.getError());
        body.put("status", ex.getHttpStatus().value());

        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }
}
