package my.mood.JobPortalAPI.Job_Portal_API.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.ApplicationResponseDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.ApplyJobDTO;
import my.mood.JobPortalAPI.Job_Portal_API.DTO.StatusDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Security.CustomUserDetails;
import my.mood.JobPortalAPI.Job_Portal_API.Service.ApplicationService;

@RestController
@Tag(name = "Application APIs", description = "Retrieve, Update, Delete, Apply for Applications")
public class ApplicationController {
	
	@Autowired
	ApplicationService service;
	
	public ApplicationController(ApplicationService service) {
		this.service = service;
	}
	
	// Retrieve All applications
	@Operation(summary = "Retrieve all applications", description = "Get all list of applications applied by job seeker")
	@GetMapping("/applications/")
	public Page<ApplicationResponseDTO> getAllApplications(Pageable pageable) {
		return service.retrieveAllApplications(pageable);
	}
	
	// Retrieve an application by id
	@Operation(summary = "Retrieve an application", description = "Get an application provided by application id")
	@GetMapping("/applications/{id}/")
	public ApplicationResponseDTO getApplicationById(@Valid @PathVariable int id) {
		return service.retrieveApplicationById(id);
	}
	
	// Get applications for a specific job
	@Operation(summary = "Retrieve all applications for specific job", description = "Get all list of applications for a specific job posted by employer")
	@PreAuthorize("hasRole('EMPLOYER')")
	@GetMapping("/applications/job/{jobId}/")
	public List<Application_Entity> getApplicationByJobId(@Valid @PathVariable int jobId) {
		return service.getApplicationsByJobId(jobId);
	}
	
	// Get application list for a specific user
	@Operation(summary = "Retrieve all applications for specific user", description = "Get all list of applications for specific user applied by job seeker")
	@PreAuthorize("hasRole('JOB_SEEKER')")
	@GetMapping("/applications/user/")
	public ResponseEntity<List<Application_Entity>> getApplicationByUserId(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		return service.getCurrentUserApplications(customUserDetails);
	}
	
	// Update application status
	@Operation(summary = "Update application status", description = "Update a status of perticular job provided by application id")
	@PreAuthorize("hasRole('EMPLOYER')")
	@PutMapping("/application/status/{id}/")
	public ResponseEntity<String> updateStatus(@Valid @PathVariable int id, @Valid @RequestBody StatusDTO status) {
		return service.updateApplicationStatus(id, status);
	}
	
	// Apply for job
	@Operation(summary = "Apply for job", description = "Post an application or apply for job provided by user id and job id")
	@PreAuthorize("hasRole('JOB_SEEKER')")
	@PostMapping("/application/apply/")
	public ResponseEntity<String> applyForApplication(@Valid @RequestBody ApplyJobDTO application, Principal principal) {
		return service.applyForJob(application, principal);
	}
	
	// delete all applications
	@Operation(summary = "Delete all applications", description = "Delete all list of applied applications posted by employer")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/applications/delete/")
	public ResponseEntity<String> deleteAllApplications() {
		return service.deleteAllApplications();
	}
	
	// delete an application by id
	@Operation(summary = "Delete an application", description = "Delete an applied application provided by application id")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/application/delete/{id}/")
	public ResponseEntity<String> deleteApplicationById(@Valid @PathVariable int id) {
		return service.deleteApplicationById(id);
	}
	
}
