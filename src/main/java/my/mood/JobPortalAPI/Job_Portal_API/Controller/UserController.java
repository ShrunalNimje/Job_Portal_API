package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.util.Optional;

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

import jakarta.validation.Valid;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Service.UserService;

@RestController
public class UserController {
	
	UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
	
	// Retrieve All users
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users/")
	public Page<User_Entity> getAllUsers(Pageable pageable) {
		return service.retrieveAllUsers(pageable);
	}
	
	// Retrieve an user by id
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@GetMapping("/user/{id}/")
	public Optional<User_Entity> getUserById(@Valid @PathVariable int id) {
		return service.retrieveUserById(id);
	}
	
	// delete all users
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/delete/")
	public void deleteAllUsers() {
		service.deleteAllUsers();
	}
	
	// delete an user by id
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@DeleteMapping("/user/delete/{id}/")
	public void deleteUserById(@Valid @PathVariable int id) {
		service.deleteUserById(id);
	}
	
	// update user by id
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@PutMapping("/user/update/{id}/")
	public void updateUser(@Valid @RequestBody UserDTO user, @Valid @PathVariable int id) {
		service.updateUserById(user, id);
	}
	
	// Register a new user
	@PostMapping("/register/user/")
	public void registerUser(@Valid @RequestBody User_Entity user) {
		service.registerUser(user);
	}
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/user/profile/")
    public User_Entity getUserProfile() {
        return service.getLoggedInProfile();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/profile/update/")
    public ResponseEntity<String> updateUserProfile(@Valid @RequestBody UserDTO updatedUser) {
        return service.updateUser(updatedUser);
    }
}
