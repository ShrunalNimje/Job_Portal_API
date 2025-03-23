package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.NotNull;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Status;

public class StatusDTO {
	
	@NotNull(message = "Status cannot be null!")
	Application_Status status;
	
	public StatusDTO() {
		
	}
	
	public StatusDTO(Application_Status status) {
		this.status = status;
	}

	public Application_Status getStatus() {
		return status;
	}

	public void setStatus(Application_Status status) {
		this.status = status;
	}
	
}
