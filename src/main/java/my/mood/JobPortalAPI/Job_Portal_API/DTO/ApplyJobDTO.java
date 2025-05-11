package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Status;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;

public class ApplyJobDTO {
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User_Entity user;
	
	@Enumerated(EnumType.STRING)
	private Application_Status status;
	
	@NotNull(message = "Job (Job Id) cannot be null!")
	@ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job_Entity job;
	
	public ApplyJobDTO() {
		
	}

	public User_Entity getUser() {
		return user;
	}

	public void setUser(User_Entity user) {
		this.user = user;
	}

	public Job_Entity getJob() {
		return job;
	}

	public void setJob(Job_Entity job) {
		this.job = job;
	}

	public void setStatus(Application_Status status) {
		this.status = status;
	}
	
	public Application_Status getStatus() {
		return status;
	}
	
}
