package my.mood.JobPortalAPI.Job_Portal_API.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User_Entity {

	@Id
	@GeneratedValue
	private int id;
	
	@NotBlank(message = "Name cannot be empty!")
	@Size(min = 5, message = "Name cannot be less than 5 characters!")
	private String name;
	
	@NotBlank(message = "Email cannot be empty!")
	@Email(message = "Email should be valid!")
	private String email;
	
	@NotBlank(message = "Password cannot be empty!")
	@Size(min = 8, message = "Password must contains atleast 8 characters!")
	private String password;
	
	@NotNull(message = "Role cannot be null!")
	@Enumerated(EnumType.STRING)
	private User_Role role;
	
	@OneToMany(mappedBy = "postedBy", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Job_Entity> jobs = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Application_Entity> applications = new ArrayList<>();
	
	public User_Entity() {
		
	}
	
	public User_Entity(int id, String name, String email, String password, User_Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User_Role getRole() {
		return role;
	}

	public void setRole(User_Role role) {
		this.role = role;
	}
	
}
