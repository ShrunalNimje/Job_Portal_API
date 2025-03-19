package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public List<User_Entity> getAllUsers() {
		return service.retrieveAllUsers();
	}
	
	// Retrieve an user by id
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@GetMapping("/user/{id}/")
	public Optional<User_Entity> getUserById(@PathVariable int id) {
		return service.retrieveUserById(id);
	}
	
	// Create an user
	@PostMapping("/user/")
	public void createUser(@RequestBody User_Entity user) {
		service.createUser(user);
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
	public void deleteUserById(@PathVariable int id) {
		service.deleteUserById(id);
	}
	
	// update user by id
	@PreAuthorize("hasRole('ADMIN') or authentication.principal.id == #id")
	@PutMapping("/user/update/{id}/")
	public void updateUser(@RequestBody User_Entity user, @PathVariable int id) {
		service.updateUser(user, id);
	}
	
}
