package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
	@Size(min = 5, message = "Name cannot less than 5 characters!")
	private String name;
	
	@Size(min = 8, message = "Password cannot less than 8 characters!")
	private String password;
	
	@Email(message = "Email should be valid!")
	private String email;
	
	public UserDTO() {
		
	}

	public UserDTO(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
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
	
}
