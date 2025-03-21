package my.mood.JobPortalAPI.Job_Portal_API.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Job_Entity {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String title;
	private String description;
	private String company;
	private int salary;
	private String location;
	
	@ManyToOne
	@JoinColumn(name = "posted_by", nullable = false)
	private User_Entity postedBy;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Application_Entity> aaplications = new ArrayList<>();
	
	public Job_Entity() {
		
	}

	public Job_Entity(int id, String title, String description, String company, int salary, String location,
			User_Entity postedBy) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.company = company;
		this.salary = salary;
		this.location = location;
		this.postedBy = postedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User_Entity getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User_Entity postedBy) {
		this.postedBy = postedBy;
	}
	
}
