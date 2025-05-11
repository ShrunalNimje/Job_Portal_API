package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class JobUpdateDTO {
	
	@Size(min = 5, message = "Title cannot be less than 5 characters!")
	private String title;
	
	@Size(min = 20, message = "Description must contains atleast 20 characters!")
	private String description;
		
	@Min(value = 1, message = "Salary cannot less than 1 rupees")
	private Integer salary;
	
	@Size(min = 5, message = "Location cannot be less than 5 characters!")
	private String location;

	public JobUpdateDTO() {
		
	}

	public JobUpdateDTO(String title, String description, Integer salary, String location) {
		super();
		this.title = title;
		this.description = description;
		this.salary = salary;
		this.location = location;
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
	
}
