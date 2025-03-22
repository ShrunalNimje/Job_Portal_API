package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Security.UserNotFoundException;

@Service
public class JobService {

	@Autowired
	PasswordEncoder encoder;
	
	JobRepository repository;
	
	UserRepository userRepository;
	
	public JobService(JobRepository repository, PasswordEncoder encoder, UserRepository userRepository) {
		this.repository = repository;
		this.encoder = encoder;
		this.userRepository = userRepository;
	}
	
	// get all jobs
	public Page<Job_Entity> retrieveAllJobs(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	// get a job by provided id
	public Optional<Job_Entity> retrieveJobById(int id) {
		return repository.findById(id);
	}
	
	// post an job
	public ResponseEntity<String> postJob(Job_Entity job, Principal principal) {
		
		Optional<Job_Entity> existingJob = repository.findById(job.getId());
		
		if(existingJob.isPresent()) {
			return ResponseEntity.badRequest().body("Job is already posted with id = " + job.getId());
		}
		
		String loggedInUser = principal.getName();
		User_Entity employer = userRepository.findByEmail(loggedInUser)
				.orElseThrow(() -> new UserNotFoundException("Employer not found!"));
		
		job.setPostedBy(employer);
		repository.save(job);
		
		return ResponseEntity.ok("job posted successfully with id = " + job.getId());
	}
	
	// delete all jobs
	public ResponseEntity<String> deleteAlljobs() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All jobs deleted successfully!");
	}
		
	// delete a job by provided id
	public ResponseEntity<String> deleteJobById(int id) {
		
		Job_Entity job = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id = " + id));
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("Job deleted successfully with id = " + id);
	}
	
	// update a job by provided id
	public ResponseEntity<String> updateJob(JobDTO updatedJob, int id) {
		
		Optional<Job_Entity> existingJob = retrieveJobById(id);
		
		if(existingJob.isPresent()) {
			
			Job_Entity job = existingJob.get();
			
			job.setId(id);
			
			if(updatedJob.getCompany() != null) {
				job.setCompany(updatedJob.getCompany());
			}
			
			if(updatedJob.getDescription() != null) {
				job.setDescription(updatedJob.getDescription());
			}
			
			if(updatedJob.getLocation() != null) {
				job.setLocation(updatedJob.getLocation());
			}
			
			if(updatedJob.getSalary() != 0) {
				job.setSalary(updatedJob.getSalary());
			}
			
			if(updatedJob.getTitle() != null) {
				job.setTitle(updatedJob.getTitle());
			}
			 
			repository.save(job);
			
			return ResponseEntity.ok("Job updated successfully with id = " + id);
		}
		
		else {
			throw new UserNotFoundException("Job not found with id = " + id);
		}
	}
	
}
