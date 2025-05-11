package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;

public class JobResponseDTO {
	
	private String title;
	
	private String description;
	
	private String company;
	
	private int salary;
	
	private String location;
		
	private User_Entity postedBy;
	
	public JobResponseDTO() {
		
	}

	public JobResponseDTO(String title, String description, String company, 
			Integer salary, String location, User_Entity postedBy) {
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
	
	public User_Entity getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User_Entity postedBy) {
		this.postedBy = postedBy;
	}
	
}
