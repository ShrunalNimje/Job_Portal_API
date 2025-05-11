package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;

public class JobPostDTO {
		
	@NotBlank(message = "Title cannot be empty!")
	@Size(min = 5, message = "Title cannot be less than 5 characters!")
	private String title;
	
	@NotBlank(message = "Description cannot be empty!")
	@Size(min = 20, message = "Description must contains atleast 20 characters!")
	private String description;
	
	@NotBlank(message = "Company cannot be empty!")
	private String company;
	
	@Min(value = 1, message = "Salary must be greater than zero!")
	private int salary;
	
	@NotBlank(message = "Location cannot be empty!")
	@Size(min = 5, message = "Location cannot be less than 5 characters!")
	private String location;
		
	private String postedBy;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Application_Entity> aaplications = new ArrayList<>();
	
	public JobPostDTO() {
		
	}

	public JobPostDTO(String title, String description, String company, 
			Integer salary, String location, String postedBy) {
		super();
		this.title = title;
		this.description = description;
		this.company = company;
		this.salary = salary;
		this.location = location;
		this.postedBy = postedBy;
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

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	
}
