package my.mood.JobPortalAPI.Job_Portal_API.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Security.UserNotFoundException;

@Service
public class JobService {

	JobRepository repository;
	
	public JobService(JobRepository repository) {
		this.repository = repository;
	}
	
	// get all jobs
	public List<Job_Entity> retrieveAllJobs() {
		return repository.findAll();
	}
	
	// get a job by provided id
	public Optional<Job_Entity> retrieveJobById(int id) {
		return repository.findById(id);
	}
	
	// create an user
	public ResponseEntity<String> createJob(Job_Entity job) {
		repository.save(job);
		
		return ResponseEntity.ok("job created successfully with id = " + job.getId());
	}
	
	// delete all jobs
	public ResponseEntity<String> deleteAlljobs() {
		repository.deleteAll();
		
		return ResponseEntity.ok("All jobs deleted successfully!");
	}
		
	// delete a job by provided id
	public ResponseEntity<String> deleteJobById(int id) {
		repository.deleteById(id);
		
		return ResponseEntity.ok("Job deleted successfully with id = " + id);
	}
	
	// update a job by provided id
	public ResponseEntity<String> updateJob(Job_Entity updatedJob, int id) {
		
		Optional<Job_Entity> existingJob = retrieveJobById(id);
		
		if(existingJob.isPresent()) {
			
			Job_Entity job = existingJob.get();
			
			job.setId(id);
			
			job.setCompany(updatedJob.getCompany());
			job.setDescription(updatedJob.getDescription());
			job.setLocation(updatedJob.getLocation());
			job.setSalary(updatedJob.getSalary());
			job.setTitle(updatedJob.getTitle());
			 
			repository.save(job);
			
			return ResponseEntity.ok("Job updated successfully with id = " + id);
		}
		
		else {
			throw new UserNotFoundException("Job not found with id = " + id);
		}
	}
	
}
