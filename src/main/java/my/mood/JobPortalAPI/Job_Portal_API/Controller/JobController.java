package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Service.JobService;

@RestController
public class JobController {
	
	JobService service;
	
	public JobController(JobService service) {
		this.service = service;
	}
	
	// Retrieve All jobs
	@GetMapping("/jobs/")
	public List<Job_Entity> getAllJobs() {
		return service.retrieveAllJobs();
	}
	
	// Retrieve a job by id
	@GetMapping("/jobs/{id}/")
	public Optional<Job_Entity> getJobById(@PathVariable int id) {
		return service.retrieveJobById(id);
	}
	
	// Post a job
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@PostMapping("/job/")
	public void postJob(@RequestBody Job_Entity job) {
		service.postJob(job);
	}
	
	// delete all jobs
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@DeleteMapping("/jobs/delete/")
	public void deleteAllJobs() {
		service.deleteAlljobs();
	}
	
	// delete a job by id
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@DeleteMapping("/job/delete/{id}/")
	public void deleteJobById(@PathVariable int id) {
		service.deleteJobById(id);
	}
	
	// update job by id
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@PutMapping("/job/update/{id}/")
	public void updateJob(@RequestBody JobDTO job, @PathVariable int id) {
		service.updateJob(job, id);
	}
	
}
