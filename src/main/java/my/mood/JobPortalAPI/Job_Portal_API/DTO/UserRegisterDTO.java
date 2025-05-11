package my.mood.JobPortalAPI.Job_Portal_API.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {
	
	@NotBlank(message = "Name cannot be empty!")
	@Size(min = 5, message = "Name cannot be less than 5 characters!")
	private String name;
	
	@NotBlank(message = "Email cannot be empty!")
	@Email(message = "Email should be valid!")
	private String email;
	
	@NotBlank(message = "Password cannot be empty!")
	@Size(min = 8, message = "Password must contains atleast 8 characters!")
	private String password;
	
	public UserRegisterDTO() {
		
	}

	public UserRegisterDTO(String name, String password, String email) {
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
