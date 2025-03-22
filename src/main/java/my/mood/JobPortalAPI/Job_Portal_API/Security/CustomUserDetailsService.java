package my.mood.JobPortalAPI.Job_Portal_API.Security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User_Entity user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found!"));
		
		return new CustomUserDetails(user);
	}

}
