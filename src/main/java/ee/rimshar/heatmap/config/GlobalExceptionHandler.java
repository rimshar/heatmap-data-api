package ee.rimshar.heatmap.config;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
    String name = ex.getParameterName();
    String type = ex.getParameterType();
    String message = String.format("Missing required parameter: '%s' of type %s", name, type);
    return ResponseEntity.badRequest().body(message);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
    // removes method name from error string
    String error = ex.getMessage().replaceAll("^[^.]*\\.", "");
    return ResponseEntity.badRequest().body(String.format("Validation error for parameter %s", error));
  }
}
