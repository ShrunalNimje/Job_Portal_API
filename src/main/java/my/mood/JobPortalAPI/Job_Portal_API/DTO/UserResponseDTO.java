package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;

public class UserResponseDTO {
	
	private int id;
	
	private String name;
	
	private String email;

	private User_Role role;
	
	public UserResponseDTO() {
		
	}

	public UserResponseDTO(int id, String name, String email, User_Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}
	
}
