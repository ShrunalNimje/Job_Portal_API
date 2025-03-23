package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.Size;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;

public class UserDTO {

	private int id;
	
	@Size(min = 5, message = "Name cannot less than 5 characters!")
	private String name;
	
	@Size(min = 8, message = "Password cannot less than 8 characters!")
	private String password;
	
	private String email;
	private User_Role role;
	
	public UserDTO() {
		
	}

	public UserDTO(int id, String name, String password, String email, User_Role role) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
