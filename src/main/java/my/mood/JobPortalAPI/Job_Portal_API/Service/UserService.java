package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Security.UserNotFoundException;

@Service
public class UserService {

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
	
	// create an user
	public ResponseEntity<String> createUser(User_Entity user) {
		repository.save(user);
		
		return ResponseEntity.ok("User created successfully with id = " + user.getId());
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
	public ResponseEntity<String> updateUser(User_Entity updatedUser, int id) {
		
		Optional<User_Entity> existingUser = retrieveUserById(id);
		
		if(existingUser.isPresent()) {
			User_Entity user = existingUser.get();
			
			user.setId(id);
			
			if(updatedUser.getName() != null) {
				user.setName(updatedUser.getName());
			}
			
			if(updatedUser.getPassword() != null) {
				user.setPassword(updatedUser.getPassword());
			}
			
			repository.save(user);
			
			return ResponseEntity.ok("User updated successfully with id = " + id);
		}
		
		else {
			throw new UserNotFoundException("User not found with id = " + id);
		}
	}
}
