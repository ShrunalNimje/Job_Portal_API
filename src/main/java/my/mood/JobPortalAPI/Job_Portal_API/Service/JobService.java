package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobResponseDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobPostDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobUpdateDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Exception.JobNotFoundException;
import my.mood.JobPortalAPI.Job_Portal_API.Exception.UserNotFoundException;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;

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
	
	public JobResponseDTO JobResponseDto(Job_Entity job) {
		
		JobResponseDTO dto = new JobResponseDTO();

		dto.setTitle(job.getTitle());
		dto.setDescription(job.getDescription());
		dto.setLocation(job.getLocation());
		dto.setCompany(job.getCompany());
		dto.setSalary(job.getSalary());
		dto.setPostedBy(job.getPostedBy());
	    
	    return dto;
	}
	
	// get all jobs
	public Page<JobResponseDTO> retrieveAllJobs(Pageable pageable) {
		return repository.findAll(pageable)
				.map(this::JobResponseDto);
	}
	
	// get a job by provided id
	public JobResponseDTO retrieveJobById(int id) {
		Job_Entity job = repository.findById(id)
				.orElseThrow(()-> new JobNotFoundException("Job not found with id = " + id));
		
		return JobResponseDto(job);
	}
	
	// post an job
	public ResponseEntity<String> postJob(JobPostDTO postJob, Principal principal) {
		
		String loggedInUser = principal.getName();
		User_Entity employer = userRepository.findByEmail(loggedInUser)
				.orElseThrow(() -> new UserNotFoundException("Employer not found!"));
		
		Optional<Job_Entity> existingJob = repository.findByTitleAndPostedBy(postJob.getTitle(), postJob.getPostedBy());
		
		if(existingJob.isPresent()) {
			return ResponseEntity.badRequest().body("Job is already posted!");
		}
				
		Job_Entity job = new Job_Entity();
	    job.setTitle(postJob.getTitle());
	    job.setDescription(postJob.getDescription());
	    job.setLocation(postJob.getLocation());
	    job.setCompany(postJob.getCompany());
	    job.setSalary(postJob.getSalary());
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
		
		repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id = " + id));
		
		repository.deleteById(id);
		
		return ResponseEntity.ok("Job deleted successfully with id = " + id);
	}
	
	// update a job by provided id
	public ResponseEntity<String> updateJob(JobUpdateDTO updatedJob, int id) {
		
		retrieveJobById(id);
			
		Job_Entity job = repository.findById(id).get();
		
		job.setId(id);
		
		if(updatedJob.getDescription() != null) {
			job.setDescription(updatedJob.getDescription());
		}
		
		if(updatedJob.getLocation() != null) {
			job.setLocation(updatedJob.getLocation());
		}
		
		if(updatedJob.getSalary() != null) {
			job.setSalary(updatedJob.getSalary());
		}
		
		if(updatedJob.getTitle() != null) {
			job.setTitle(updatedJob.getTitle());
		}
		 
		repository.save(job);
		
		return ResponseEntity.ok("Job updated successfully with id = " + id);

	}
	
}
