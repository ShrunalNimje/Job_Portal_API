package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDTO {
	
	@NotBlank(message = "Email cannot be empty!")
	private String email;
	
	@NotBlank(message = "Password cannot be empty!")
	private String password;
	
	public UserLoginDTO() {
		
	}

	public UserLoginDTO(String password, String email) {
		super();
		this.password = password;
		this.email = email;
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
