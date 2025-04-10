package my.mood.JobPortalAPI.Job_Portal_API.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.UserDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;
import my.mood.JobPortalAPI.Job_Portal_API.Exception.UserNotFoundException;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;
		
	@Mock
	PasswordEncoder encoder;
	
	@Mock
	AuthenticationManager authManager;
	
	@Mock
	JWTService jwtService;
	
	@InjectMocks
	UserService userService;
	
	List<User_Entity> users = null;
	
	@BeforeEach
	public void init() {
		
		System.out.println("Before Each!");

		users = new ArrayList<User_Entity>();
		
		users.add(new User_Entity(1, "shrunal", "sn@gmail.com", "shrunal123", User_Role.JOB_SEEKER));
		users.add(new User_Entity(2, "ritik", "rn@gmail.com", "ritik123", User_Role.EMPLOYER));
		users.add(new User_Entity(3, "anushka", "an@gmail.com", "anushka123", User_Role.ADMIN));
	}
	
	@Test
	public void shouldReturnAllUsers_WhenRetrieveAllUsersIsCalled() {
		
		System.out.println("Get all users back!");
		
		Pageable pageable = PageRequest.of(0, 3);
		Page<User_Entity> page = new PageImpl<>(users, pageable, users.size());
		
		Mockito.when(userRepository.findAll(pageable)).thenReturn(page);
		
		Page<User_Entity> result = userService.retrieveAllUsers(pageable);
		
		assertNotNull(result);
		assertEquals(3, result.getNumberOfElements());
		assertEquals("shrunal", result.getContent().get(0).getName());
		
	}
	
	@Test
	public void retrieveAllUsers_shouldReturnEmptyPage_whenNoUsersExist() {

	    System.out.println("Negative: No users found in DB!");

	    Pageable pageable = PageRequest.of(0, 3);
	    Page<User_Entity> emptyPage = new PageImpl<>(new ArrayList<>(), pageable, 0);

	    Mockito.when(userRepository.findAll(pageable)).thenReturn(emptyPage);

	    Page<User_Entity> result = userService.retrieveAllUsers(pageable);

	    assertNotNull(result);
	    assertEquals(0, result.getNumberOfElements());
	    assertTrue(result.getContent().isEmpty());
	    
	}

	@Test
	public void shouldReturnUser_WhenValidUserIdIsProvided() {
		
		System.out.println("Get an user back!");
				
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(users.get(0)));
		
		Optional<User_Entity> result = userService.retrieveUserById(1);
		
		assertTrue(result.isPresent());
		assertEquals(1, result.get().getId());
		assertEquals("shrunal", result.get().getName());
		
	}
	
	@Test
	public void retrieveUserById_shouldReturnEmptyOptional_whenUserNotFound() {

	    System.out.println("Negative: User not found by ID!");

	    Mockito.when(userRepository.findById(99)).thenReturn(Optional.empty());

	    Optional<User_Entity> result = userService.retrieveUserById(99);

	    assertTrue(result.isEmpty());
	    
	}

	@Test
	public void shouldDeleteAllUsersSuccessfully() {
		
		System.out.println("Delete all users!");
				
		doNothing().when(userRepository).deleteAll();
		
		userService.deleteAllUsers();
		
		verify(userRepository, times(1)).deleteAll();
		
	}
	
	@Test
	public void deleteAllUsers_shouldThrowException_whenRepositoryFails() {

	    System.out.println("Negative: Exception while deleting all users!");

	    Mockito.doThrow(new RuntimeException("DB error")).when(userRepository).deleteAll();

	    try {
	        userService.deleteAllUsers();
	    } 
	    catch (RuntimeException ex) {
	        assertEquals("DB error", ex.getMessage());
	    }

	    verify(userRepository, times(1)).deleteAll();
	    
	}

	@Test
	public void shouldDeleteUser_WhenValidUserIdIsProvided() {
		
		System.out.println("Delete an user!");
		
		Mockito.when(userRepository.findById(2)).thenReturn(Optional.of(users.get(1)));
		
		doNothing().when(userRepository).deleteById(2);
		
		userService.deleteUserById(2);
		
		verify(userRepository, times(1)).deleteById(2);
		verify(userRepository, times(1)).findById(2);
		
	}
	
	@Test
	public void deleteUserById_shouldThrowException_whenUserNotFound() {
		
	    System.out.println("Negative: User not found by ID!");
		
	    Mockito.when(userRepository.findById(99)).thenReturn(Optional.empty());
	    
	    assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(99));
	    
	}
	
	@Test
	public void shouldUpdateUser_WhenValidDataIsProvided() {
		
	    System.out.println("Update user!");
	    
	    User_Entity existingUser = users.get(0); // ID: 1
	    
	    UserDTO updatedData = new UserDTO();
	    
	    updatedData.setName("SHRUNAL");
	    
	    Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
	    Mockito.when(userRepository.save(Mockito.any(User_Entity.class))).thenReturn(existingUser);
	    
	    ResponseEntity<String> result = userService.updateUserById(updatedData, 1);
	    
	    assertNotNull(result);
	    assertEquals(HttpStatus.OK, result.getStatusCode());
	    assertEquals("User updated successfully with id = 1", result.getBody());
	}

	@Test
	public void updateUserById_negativeTest_userNotFound() {
		
	    System.out.println("Negative: User not found by ID!");

	    UserDTO updatedUser = new UserDTO();
	    
	    updatedUser.setName("New Name");
	    updatedUser.setPassword("newpassword");

	    Mockito.when(userRepository.findById(99)).thenReturn(Optional.empty());

	    assertThrows(UserNotFoundException.class, () -> {
	        userService.updateUserById(updatedUser, 99);
	    });
	}

	@Test
	public void shouldRegisterUser_WhenUserDoesNotExist() {
		
	    System.out.println("Register new user!");
	    
	    User_Entity newUser = new User_Entity(4, "user", "user@gmail.com", "user123", User_Role.EMPLOYER);
	    
	    Mockito.when(userRepository.findByEmail(newUser.getEmail())).thenReturn(Optional.empty());
	    Mockito.when(userRepository.save(Mockito.any(User_Entity.class))).thenReturn(newUser);
	    
	    ResponseEntity<String> result = userService.registerUser(newUser);
	    
	    assertNotNull(result);
	    assertEquals("User registerd successfully with id = 4", result.getBody());
	    verify(userRepository, times(1)).save(Mockito.any(User_Entity.class));
	    
	}
	
	@Test
	public void registerUser_NegativeTest_UserAlreadyExists() {
	    
	    System.out.println("Negative: User already exist with this Email ID!");

	    User_Entity existingUser = users.get(1);
	    
	    Mockito.when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(Optional.of(existingUser));
	    
	    ResponseEntity<String> result = userService.registerUser(existingUser);
	    
	    assertNotNull(result);
	    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	    assertEquals("User already exists!", result.getBody());
	    verify(userRepository, times(0)).save(Mockito.any(User_Entity.class));
	    
	}
	
	@Test
	public void shouldLoginUserAndReturnToken_WhenCredentialsAreValid() {
		
	    System.out.println("Login user using right credentials!");

	    UserDTO user = new UserDTO();
	    
	    user.setName("user");
	    user.setPassword("password");

	    Authentication auth = Mockito.mock(Authentication.class);

	    Mockito.when(authManager.authenticate(Mockito.any())).thenReturn(auth);
	    Mockito.when(auth.isAuthenticated()).thenReturn(true);
	    Mockito.when(jwtService.generateToken(user.getEmail())).thenReturn("mocked-jwt-token");

	    String token = userService.verify(user);

	    assertEquals("mocked-jwt-token", token);
	    verify(authManager, times(1)).authenticate(Mockito.any());
	    verify(jwtService, times(1)).generateToken(user.getEmail());
	    
	}
	
	@Test
	public void shouldThrowException_WhenLoginCredentialsAreInvalid() {
		
	    System.out.println("Negative: User's credential is wrong!");

	    UserDTO user = new UserDTO();
	    
	    user.setName("wrongUser");
	    user.setPassword("wrongPassword");

	    Mockito.when(authManager.authenticate(Mockito.any()))
	           .thenThrow(new BadCredentialsException("Invalid email and password"));

	    assertThrows(BadCredentialsException.class, () -> {
	        userService.verify(user);
	    });

	    verify(authManager, times(1)).authenticate(Mockito.any());
	}

}
