package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.ApplicationResponseDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.ApplyJobDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.StatusDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Status;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.ApplicationRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Security.CustomUserDetails;

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
	
	public ApplicationResponseDTO ApplicationResponseDto(Application_Entity appEntity) {
		ApplicationResponseDTO dto = new ApplicationResponseDTO();
		
		dto.setJob(appEntity.getJob());
		dto.setStatus(appEntity.getStatus());
		dto.setUser(appEntity.getUser());
		
		return dto;
	}
	
	// get all applications
	public Page<ApplicationResponseDTO> retrieveAllApplications(Pageable pageable) {
		return repository.findAll(pageable)
				.map(this::ApplicationResponseDto);
	}
	
	// get an applications by provided id
	public ApplicationResponseDTO retrieveApplicationById(int id) {
		Application_Entity application = repository.findById(id)
				.orElseThrow(()-> new RuntimeException("Application not found!"));
		return ApplicationResponseDto(application);
	}
	
	// Get applications for a specific job
	public List<Application_Entity> getApplicationsByJobId(int jobId) {
		return repository.findByJobId(jobId);
	}
	
	// Get application list for a specific user
	public List<Application_Entity> getApplicationsByUserId(int userId) {
		return repository.findByUserId(userId);
	}
	
	public ResponseEntity<List<Application_Entity>> getCurrentUserApplications( CustomUserDetails userDetails) {
        int userId = userDetails.getId();
        List<Application_Entity> applications = getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }
	
	// Apply for job
	public ResponseEntity<String> applyForJob(ApplyJobDTO application, Principal principal) {
		
		Job_Entity job = jobRepository.findById(application.getJob().getId())
				.orElseThrow(() -> new RuntimeException("Job not found!"));
		
		User_Entity user  = userRepository.findByEmail(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found!"));
		
		Application_Entity appEntity = new Application_Entity();
		appEntity.setJob(job);
		appEntity.setUser(user);
		appEntity.setStatus(Application_Status.APPLIED);

		repository.save(appEntity);
		
		return ResponseEntity.ok("Applied for job successfully with id = " + appEntity.getId());
	}
	
	// Update application status
	public ResponseEntity<String> updateApplicationStatus(int id, StatusDTO status) {
		Application_Entity application = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Application not found!"));
		
		application.setStatus(status.getStatus());
		repository.save(application);
		
		return ResponseEntity.ok("Application status updated to " + status);
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
