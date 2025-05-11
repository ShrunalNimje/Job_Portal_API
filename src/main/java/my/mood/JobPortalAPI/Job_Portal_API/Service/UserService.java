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

import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserLoginDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserRegisterDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserResponseDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserUpdateDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;
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
	
	public UserResponseDTO UserResponseDto(User_Entity user) {
		
		UserResponseDTO dto = new UserResponseDTO();
	    dto.setId(user.getId());
	    dto.setName(user.getName());
	    dto.setEmail(user.getEmail());
	    dto.setRole(user.getRole());

	    return dto;
	}

	// get all users
	public Page<UserResponseDTO> retrieveAllUsers(Pageable pageable) {
		return repository.findAll(pageable)
				.map(this::UserResponseDto);
	}
	
	// get an user by provided id
	public UserResponseDTO retrieveUserById(int id) {
		User_Entity user = repository.findById(id)
				.orElseThrow(()-> new UserNotFoundException("User not found!"));
		
		return UserResponseDto(user);
	}
	
	// delete all users
	public ResponseEntity<String> deleteAllUsers() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All users deleted successfully!");
	}
		
	// delete an user by provided id
	public ResponseEntity<String> deleteUserById(int id) {
		
		repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id = " + id));
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("User deleted successfully with id = " + id);
	}
	
	// update an user by provided id
	public ResponseEntity<String> updateUserById(UserUpdateDTO updatedUser, int id) {
		
		retrieveUserById(id);
		
		User_Entity user = repository.findById(id).get();
		
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
	
	// update logged in user profile
	public ResponseEntity<String> updateUser(UserUpdateDTO updatedUser) {
		
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
	public UserResponseDTO getLoggedInProfile() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User_Entity user = repository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("User not found!"));
		
		return UserResponseDto(user);
	}
	
	// register a new user
	public ResponseEntity<String> registerUser(UserRegisterDTO user) {
		
		Optional<User_Entity> existingUser = repository.findByEmail(user.getEmail());
		
		if(existingUser.isPresent()) {
			return ResponseEntity.badRequest().body("User already exists!");
		}
		
		User_Entity newUser = new User_Entity();
		newUser.setEmail(user.getEmail());
		newUser.setName(user.getName());
		newUser.setRole(User_Role.JOB_SEEKER);
		newUser.setPassword(encoder.encode(user.getPassword()));
		repository.save(newUser);
		
		return ResponseEntity.ok("User registerd successfully with id = " + newUser.getId());
	}
	
	// Login an user
	public String verify(UserLoginDTO user) {
		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())); 
		
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}
		
		throw new BadCredentialsException("Invalid email and password");
	}
	
}
