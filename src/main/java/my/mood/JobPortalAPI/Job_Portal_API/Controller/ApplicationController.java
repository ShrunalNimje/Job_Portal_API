package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Application_Entity> getAllApplications() {
		return service.retrieveAllApplications();
	}
	
	// Retrieve an application by id
	@GetMapping("/application/{id}/")
	public Optional<Application_Entity> getApplicationById(@PathVariable int id) {
		return service.retrieveApplicationById(id);
	}
	
	// Apply for job
	@PreAuthorize("hasRole('JOB_SEEKER')")
	@PostMapping("/application/")
	public void applyForApplication(@RequestBody Application_Entity application) {
		service.applyForJob(application);
	}
	
	// delete all applications
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/applications/delete/")
	public void deleteAllApplications() {
		service.deleteAllApplications();
	}
	
	// delete an application by id
	@PreAuthorize("hasRole('ADMIIN')")
	@DeleteMapping("/application/delete/{id}/")
	public void deleteApplicationById(@PathVariable int id) {
		service.deleteApplicationById(id);
	}
	
}
