package my.mood.JobPortalAPI.Job_Portal_API.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class JobNotFoundException extends RuntimeException {

	public JobNotFoundException(String message) {
		super(message);
	}
}
