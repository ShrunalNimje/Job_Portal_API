package my.mood.JobPortalAPI.Job_Portal_API.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Application_Entity {

	@Id
	@GeneratedValue
	private int id;
	
	@Enumerated(EnumType.STRING)
	private Application_Status status;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User_Entity user;
	
	@ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job_Entity job;
	
	public Application_Entity() {
		
	}

	public Application_Entity(int id, Application_Status status) {
		super();
		this.id = id;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Application_Status getStatus() {
		return status;
	}

	public void setStatus(Application_Status status) {
		this.status = status;
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
	
}
