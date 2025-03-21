package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Status;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.ApplicationRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;


@Service
public class ApplicationService {
	
	ApplicationRepository repository;
	
	JobRepository jobRepository;
	
	UserRepository userRepository;
	
	public ApplicationService(ApplicationRepository repository, JobRepository jobRepository, UserRepository userRepository) {
		this.repository = repository;
		this.jobRepository = jobRepository;
		this.userRepository = userRepository;
	}
	
	// get all applications
	public List<Application_Entity> retrieveAllApplications() {
		return repository.findAll();
	}
	
	// get an applications by provided id
	public Optional<Application_Entity> retrieveApplicationById(int id) {
		return repository.findById(id);
	}
	
	// Apply for job
	public ResponseEntity<String> applyForJob(Application_Entity application) {
		
		Job_Entity job = jobRepository.findById(application.getJob().getId())
				.orElseThrow(() -> new RuntimeException("Job not found!"));
		
		User_Entity user = userRepository.findById(application.getUser().getId())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		application.setJob(job);
		application.setUser(user);
		application.setStatus(Application_Status.APPLIED);
		repository.save(application);
		
		return ResponseEntity.ok("Applied for job successfully with id = " + application.getId());
	}
	
	// delete all application
	public ResponseEntity<String> deleteAllApplications() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All applications deleted successfully!");
	}
		
	// delete an application by provided id
	public ResponseEntity<String> deleteApplicationById(int id) {
		repository.deleteById(id);
		
		return ResponseEntity.ok("Application deleted successfully with id = " + id);
	}
	
}
