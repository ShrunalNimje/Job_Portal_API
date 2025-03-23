package my.mood.JobPortalAPI.Job_Portal_API.Exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // Generic Exception Handler
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Specific Exception: User Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
    }

    // Validation Exception
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        String errorMessage = "Validation failed with " + ex.getErrorCount() + " error(s): " + String.join(", ", errors);

        return new ResponseEntity<>(new ErrorDetails(LocalDateTime.now(), errorMessage, request.getDescription(false)),
                HttpStatus.BAD_REQUEST);
    }

    // Helper Method for Consistency
    private ResponseEntity<ErrorDetails> buildErrorResponse(String message, String details, HttpStatus status) {
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), message, details);
        return new ResponseEntity<>(errorDetails, status);
    }
    
}
