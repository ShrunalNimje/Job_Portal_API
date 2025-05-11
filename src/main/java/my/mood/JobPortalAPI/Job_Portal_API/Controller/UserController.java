package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserLoginDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserRegisterDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserResponseDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserUpdateDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Service.UserService;

@RestController
@Tag(name = "User APIs", description = "Retrieve, Update, Delete, Register and View Profile of User")
public class UserController {
	
	@Autowired
	UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	// Retrieve All users
	@Operation(summary = "Retrieve all users", description = "Get all list of users with thier roles")
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users/")
	public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
		return service.retrieveAllUsers(pageable);
	}
	
	// Retrieve an user by id
	@Operation(summary = "Retrieve an user", description = "Get an user provided by unique id")
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@GetMapping("/user/{id}/")
	public UserResponseDTO getUserById(@Valid @PathVariable int id) {
		return service.retrieveUserById(id);
	}
	
	// delete all users
	@Operation(summary = "Delete all users", description = "Delete all list of users with thier roles")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/delete/")
	public ResponseEntity<String> deleteAllUsers() {
		return service.deleteAllUsers();
	}
	
	// delete an user by id
	@Operation(summary = "Delete an user", description = "Delete an user provided by unique id")
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@DeleteMapping("/user/delete/{id}/")
	public ResponseEntity<String> deleteUserById(@Valid @PathVariable int id) {
		return service.deleteUserById(id);
	}
	
	// update user by id
	@Operation(summary = "Update an user", description = "Update an user provided by unique id")
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@PutMapping("/user/update/{id}/")
	public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDTO user, @Valid @PathVariable int id) {
		return service.updateUserById(user, id);
	}
	
	// Register a new user
	@Operation(summary = "Register a new user", description = "Register a new user provided by unique id, name, email, password and role based access")
	@PostMapping("/register/user/")
	public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDTO user) {
		return service.registerUser(user);
	}
	
	// Get logged-in user profile
	@Operation(summary = "View user profile", description = "View user profile by authentication")
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/profile/")
    public UserResponseDTO getUserProfile() {
        return service.getLoggedInProfile();
    }

	// Update logged-in user profile
	@Operation(summary = "Update user profile", description = "Update user profile by authentication")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/profile/update/")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserUpdateDTO updatedUser) {
        return service.updateUser(updatedUser);
    }
	
	// login user
	@Operation(summary = "Login an user", description = "Login an user by giving right credentials")
	@PostMapping("/login/")
	public ResponseEntity<String> login(@RequestBody UserLoginDTO user) {
		String token = service.verify(user);
		return ResponseEntity.ok(token);
	}
	
}
