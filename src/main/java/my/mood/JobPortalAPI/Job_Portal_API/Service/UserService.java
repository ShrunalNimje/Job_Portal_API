package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Exception.UserNotFoundException;

@Service
public class UserService {

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	JWTService jwtService;
	
	public UserService(UserRepository repository, PasswordEncoder encoder, 
			AuthenticationManager authManager, JWTService jwtService) {
		this.repository = repository;
		this.encoder = encoder;
		this.authManager = authManager;
		this.jwtService = jwtService;
	}
	
	// get all users
	public Page<User_Entity> retrieveAllUsers(Pageable pageable) {
		return repository.findAll(pageable);
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
		
		User_Entity user = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id = " + id));
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("User deleted successfully with id = " + id);
	}
	
	// update an user by provided id
	public ResponseEntity<String> updateUserById(UserDTO updatedUser, int id) {
		
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
	
	// update logged in user profile
	public ResponseEntity<String> updateUser(UserDTO updatedUser) {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User_Entity user = repository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found!"));
			
		if(updatedUser.getName() != null) {
			user.setName(updatedUser.getName());
		}
		
		if(updatedUser.getPassword() != null) {
			user.setPassword(encoder.encode(updatedUser.getPassword()));
		}
		
		repository.save(user);
		
		return ResponseEntity.ok("User updated successfully!");
	}
	
	// get Logged in user profile
	public User_Entity getLoggedInProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User_Entity user = repository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found!"));
		
		return user;
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
	
	// Login an user
	public String verify(UserDTO user) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())); 
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}
		
		throw new BadCredentialsException("Invalid email and password");
	}
	
}
