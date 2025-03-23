package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
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
	public Page<Job_Entity> getAllJobs(Pageable pageable) {
		return service.retrieveAllJobs(pageable);
	}
	
	// Retrieve a job by id
	@GetMapping("/jobs/{id}/")
	public Optional<Job_Entity> getJobById(@Valid @PathVariable int id) {
		return service.retrieveJobById(id);
	}
	
	// Post a job
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@PostMapping("/job/")
	public void postJob(@Valid @RequestBody Job_Entity job, Principal principal) {
		service.postJob(job, principal);
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
	public void deleteJobById(@Valid @PathVariable int id) {
		service.deleteJobById(id);
	}
	
	// update job by id
	@PreAuthorize("hasRole('EMPLOYER') or hasRole('ADMIN')")
	@PutMapping("/job/update/{id}/")
	public void updateJob(@Valid @RequestBody JobDTO job, @Valid @PathVariable int id) {
		service.updateJob(job, id);
	}
	
}
