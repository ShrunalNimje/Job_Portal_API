package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Security.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	PasswordEncoder encoder;
	
	UserRepository repository;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}
	
	// get all users
	public List<User_Entity> retrieveAllUsers() {
		return repository.findAll();
	}
	
	// get an user by provided id
	public Optional<User_Entity> retrieveUserById(int id) {
		return repository.findById(id);
	}
	
	// delete all users
	public ResponseEntity<String> deleteAllUsers() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All users deleted successfully!");
	}
		
	// delete an user by provided id
	public ResponseEntity<String> deleteUserById(int id) {
		repository.deleteById(id);
		
		return ResponseEntity.ok("User deleted successfully with id = " + id);
	}
	
	// update an user by provided id
	public ResponseEntity<String> updateUser(UserDTO updatedUser, int id) {
		
		Optional<User_Entity> existingUser = retrieveUserById(id);
		
		if(existingUser.isPresent()) {
			User_Entity user = existingUser.get();
			
			user.setId(id);
			
			if(updatedUser.getName() != null) {
				user.setName(updatedUser.getName());
			}
			
			if(updatedUser.getPassword() != null) {
				user.setPassword(encoder.encode(updatedUser.getPassword()));
			}
			
			repository.save(user);
			
			return ResponseEntity.ok("User updated successfully with id = " + id);
		}
		
		else {
			throw new UserNotFoundException("User not found with id = " + id);
		}
	}
	
	// register a new user
	public ResponseEntity<String> registerUser(User_Entity user) {
		
		Optional<User_Entity> existingUser = repository.findByEmail(user.getEmail());
		
		if(existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("User already exists!");
		}
		
		User_Entity newUser = new User_Entity();
		newUser.setEmail(user.getEmail());
		newUser.setName(user.getName());
		newUser.setRole(user.getRole());
		newUser.setPassword(encoder.encode(user.getPassword()));
		repository.save(newUser);
		
		return ResponseEntity.ok("User registerd successfully with id = " + user.getId());
	}
	
}
