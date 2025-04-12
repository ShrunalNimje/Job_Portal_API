package my.mood.JobPortalAPI.Job_Portal_API.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
public class JWTSeriveTest {

	@InjectMocks
	JWTService jwtService;
	
	private String secret_key = "dfbjnfjnioewnio23t04j";
	
	@BeforeEach
	public void init() {
		
		System.out.println("Before Each!");
		
		this.secret_key = Base64.getEncoder().encodeToString(secret_key.getBytes());
		
	}
	
	@Test
	public void shouldGenerateTokenSuccessfully_WhenGenerateTokenIsCalled() {
		
		System.out.println("Generate token successfully!");

		String email = "dummy@gmail.com";
		
		String result = jwtService.generateToken(email);
		
		assertNotNull(result);
		
	}
	
	@Test
	public void shouldExtractEmailSuccessfully_WhenExtractEmailIsCalled() {
		
		System.out.println("Extract email successfully!");

		String email = "dummy@gmail.com";
		
		String token = jwtService.generateToken(email);
		
		String result = jwtService.extractEmail(token);
		
		assertNotNull(result);
		assertEquals(email, result);
		
	}
	
	@Test
	public void shouldValidateTokenSuccessfully_WhenValidateTokenIsCalled() {
		
		System.out.println("Validate token successfully!");

		String email = "dummy@gmail.com";
		
		String token = jwtService.generateToken(email);
				
		UserDetails userDetails = new User(email, "password123", new ArrayList<>());
		
		boolean result = jwtService.validateToken(token, userDetails);
		
		assertNotNull(result);
		assertTrue(result);
		
	}
	
	@Test
	public void shouldNotValidateToken_WhenUserIsInvalid() {
		
		System.out.println("Negative: User is invalid!");

		String email = "dummy@gmail.com";
		
		String token = jwtService.generateToken(email);
				
		UserDetails userDetails = new User("user@gmail.com", "password123", new ArrayList<>());
		
		boolean result = jwtService.validateToken(token, userDetails);
		
		assertNotNull(result);
		assertFalse(result);
		
	}
	
}
