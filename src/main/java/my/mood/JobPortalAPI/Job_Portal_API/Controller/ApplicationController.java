package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.security.Principal;
import java.util.List;
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
import my.mood.JobPortalAPI.Job_Portal_API.DTO.StatusDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Service.ApplicationService;

@RestController
public class ApplicationController {
	
	ApplicationService service;
	
	public ApplicationController(ApplicationService service) {
		this.service = service;
	}
	
	// Retrieve All applications
	@GetMapping("/applications/")
	public Page<Application_Entity> getAllApplications(Pageable pageable) {
		return service.retrieveAllApplications(pageable);
	}
	
	// Retrieve an application by id
	@GetMapping("/applications/{id}/")
	public Optional<Application_Entity> getApplicationById(@Valid @PathVariable int id) {
		return service.retrieveApplicationById(id);
	}
	
	// Get applications for a specific job
	@PreAuthorize("hasRole('EMPLOYER')")
	@GetMapping("/applications/job/{jobId}/")
	public List<Application_Entity> getApplicationByJobId(@Valid @PathVariable int jobId) {
		return service.getApplicationsByJobId(jobId);
	}
	
	// Update application status
	@PreAuthorize("hasRole('EMPLOYER')")
	@PutMapping("/application/status/{id}/")
	public void updateStatus(@Valid @PathVariable int id, @Valid @RequestBody StatusDTO status) {
		service.updateApplicationStatus(id, status);
	}
	
	// Apply for job
	@PreAuthorize("hasRole('JOB_SEEKER')")
	@PostMapping("/application/apply/")
	public void applyForApplication(@Valid @RequestBody Application_Entity application, Principal principal) {
		service.applyForJob(application, principal);
	}
	
	// delete all applications
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/applications/delete/")
	public void deleteAllApplications() {
		service.deleteAllApplications();
	}
	
	// delete an application by id
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/application/delete/{id}/")
	public void deleteApplicationById(@Valid @PathVariable int id) {
		service.deleteApplicationById(id);
	}
	
}
