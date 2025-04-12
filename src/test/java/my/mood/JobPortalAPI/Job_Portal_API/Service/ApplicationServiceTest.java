package my.mood.JobPortalAPI.Job_Portal_API.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.StatusDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Status;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.ApplicationRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

	@Mock
	ApplicationRepository applicationRepository;
	
	@Mock
	JobRepository jobRepository;
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	ApplicationService applicationService;
	
	List<Application_Entity> applications = null;
	
	@BeforeEach
	public void init() {
		
		System.out.println("Before Each!");

		applications = new ArrayList<Application_Entity>();
		
		applications.add(new Application_Entity(1, Application_Status.APPLIED));
		applications.add(new Application_Entity(2, Application_Status.APPLIED));
		applications.add(new Application_Entity(3, Application_Status.APPLIED));
		
	}
	
	@Test
	public void shouldReturnAllJobs_WhenRetrieveAllJobsIsCalled() {
		
		System.out.println("Retrieve all jobs back!");
		
		Pageable pageable = PageRequest.of(0, 3);
		Page<Application_Entity> page = new PageImpl<Application_Entity>(applications, pageable, applications.size());
		
		Mockito.when(applicationRepository.findAll(pageable)).thenReturn(page);
		
		Page<Application_Entity> result = applicationService.retrieveAllApplications(pageable);
		
		assertNotNull(result);
		assertEquals(3, result.getNumberOfElements());
		assertEquals(3, result.getContent().get(2).getId());
		
	}
	
	@Test
	public void shouldNotReturnAllJobs_WhenJobsDoesNotExist() {
		
		System.out.println("Negative: No jobs are in database!");

		Pageable pageable = PageRequest.of(0, 3);
		Page<Application_Entity> emptyPage = new PageImpl<Application_Entity>(new ArrayList<Application_Entity>(), pageable, 0);
		
		Mockito.when(applicationRepository.findAll(pageable)).thenReturn(emptyPage);
		
		Page<Application_Entity> result = applicationService.retrieveAllApplications(pageable);
		
		assertNotNull(result);
		assertEquals(0, result.getNumberOfElements());
		
	}
	
	@Test
	public void shouldReturnApplicationById_WhenRetrieveApplicationByIdIsCalled() {
		
		System.out.println("Retrieve an aplication from database!");
		
		Mockito.when(applicationRepository.findById(1)).thenReturn(Optional.of(applications.get(0)));
		
		Optional<Application_Entity> result = applicationService.retrieveApplicationById(1);
		
		assertNotNull(result);
		assertEquals(1, result.get().getId());
		
	}
	
	@Test
	public void shouldNotReturnApplicationById_WhenApplicationDoesNotExist() {
		
		System.out.println("Negative: Application not present in database!");
		
		Mockito.when(applicationRepository.findById(99)).thenReturn(Optional.empty());
		
		Optional<Application_Entity> result = applicationService.retrieveApplicationById(99);
		
		assertNotNull(result);
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldReturnApplicationByJobId_WhenRetrieveApplicationByJobIdIsCalled() {
		
		System.out.println("Retrieve an aplications for job from database!");
		
		Job_Entity job = new Job_Entity();
		job.setId(1);
		
		Mockito.when(applicationRepository.findByJobId(1)).thenReturn(applications);
		
		List<Application_Entity> result = applicationService.getApplicationsByJobId(1);
		
		assertNotNull(result);
		assertEquals(applications.size(), result.size());
		
	}
	
	@Test
	public void shouldNotReturnApplicationByJobId_WhenApplicationForJobIdDoesNotExist() {
		
		System.out.println("Negative: Aplications for job does not exist in database!");
		
		Mockito.when(applicationRepository.findByJobId(99)).thenReturn(new ArrayList<Application_Entity>());
		
		List<Application_Entity> result = applicationService.getApplicationsByJobId(99);
		
		assertNotNull(result);
		assertTrue(result.size() == 0);
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldReturnApplicationByUserId_WhenRetrieveApplicationByUserIdIsCalled() {
		
		System.out.println("Retrieve an aplications for user from database!");
		
		User_Entity user = new User_Entity();
		user.setId(1);
		
		Mockito.when(applicationRepository.findByUserId(1)).thenReturn(applications);
		
		List<Application_Entity> result = applicationService.getApplicationsByUserId(1);
		
		assertNotNull(result);
		assertEquals(applications.size(), result.size());
		
	}
	
	@Test
	public void shouldNotReturnApplicationByUserId_WhenApplicationForUserIdDoesNotExist() {
		
		System.out.println("Negative: Aplications for user does not exist in database!");
		
		Mockito.when(applicationRepository.findByUserId(99)).thenReturn(new ArrayList<Application_Entity>());
		
		List<Application_Entity> result = applicationService.getApplicationsByUserId(99);
		
		assertNotNull(result);
		assertTrue(result.size() == 0);
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldApplyForJob_WhenApplyForJobIsCalled() {
		
		System.out.println("Apply for job!");
		
		Job_Entity job = new Job_Entity();
		job.setId(1);
		
		Application_Entity application = applications.get(0);
		application.setJob(job);
		
		User_Entity jobSeeker = new User_Entity();
		jobSeeker.setEmail("user@gmail.com");
		jobSeeker.setRole(User_Role.JOB_SEEKER);
		
		Principal principal = Mockito.mock(Principal.class);
		
		Mockito.when(principal.getName()).thenReturn("user@gmail.com");
		
		Mockito.when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
		Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(jobSeeker));
		Mockito.when(applicationRepository.save(Mockito.any(Application_Entity.class))).thenReturn(application);
		
		ResponseEntity<String> result = applicationService.applyForJob(application, principal);
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Applied for job successfully with id = 1", result.getBody());
		
	}
	
	@Test
	public void shouldNotApplyForJob_WhenJobDoesNotExist() {
		
		System.out.println("Nagative: Job not exist in database for apply!");

		Job_Entity job = new Job_Entity();
		job.setId(99);
		
		Application_Entity application = applications.get(0);
		application.setJob(job);
		
		User_Entity jobSeeker = new User_Entity();
		jobSeeker.setEmail("user@gmail.com");
		jobSeeker.setRole(User_Role.JOB_SEEKER);
		
		Principal principal = Mockito.mock(Principal.class);
				
		Mockito.when(jobRepository.findById(job.getId())).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			applicationService.applyForJob(application, principal);
		});
		
		assertNotNull(exception);
		assertEquals("Job not found!", exception.getMessage());
		
	}
	
	@Test
	public void shouldNotApplyForJob_WhenUserDoesNotExist() {
		
		System.out.println("Nagative: user not exist in database for apply!");

		Job_Entity job = new Job_Entity();
		job.setId(99);
		
		Application_Entity application = applications.get(0);
		application.setJob(job);
		
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("user@gmail.com");

		Mockito.when(jobRepository.findById(job.getId())).thenReturn(Optional.of(job));
		Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			applicationService.applyForJob(application, principal);
		});
		
		assertNotNull(exception);
		assertEquals("User not found!", exception.getMessage());
		
	}
	
	@Test
	public void shouldUpdateApplicationStatus_WhenUpdateApplicationStatusIsCalled() {
		
		System.out.println("Update application status for job in database!");

		Application_Entity application = applications.get(0);
		
		StatusDTO status = new StatusDTO();
		status.setStatus(Application_Status.ACCEPTED);
		
		Mockito.when(applicationRepository.findById(application.getId())).thenReturn(Optional.of(application));
		Mockito.when(applicationRepository.save(Mockito.any(Application_Entity.class))).thenReturn(application);
		
		ResponseEntity<String> result = applicationService.updateApplicationStatus(1, status);
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Application status updated to " + status, result.getBody());
		
	}
	
	@Test
	public void shouldNotUpdateApplicationStatus_WhenApplicationNotFound() {
		
		System.out.println("Nagative: application not found to update status in database!");

		StatusDTO status = new StatusDTO();
		status.setStatus(Application_Status.ACCEPTED);
		
		Mockito.when(applicationRepository.findById(99)).thenReturn(Optional.empty());
		
		RuntimeException exception = assertThrows(RuntimeException.class, () -> {
			applicationService.updateApplicationStatus(99, status);
		});
		
		assertNotNull(exception);
		assertEquals("Application not found!", exception.getMessage());
		
	}
	
	@Test
	public void shouldDeleteAllApplications_WhenDeleteAllApplicationsIsCalled() {
		
		System.out.println("Delete all applications from database!");
		
		doNothing().when(applicationRepository).deleteAll();
		
		ResponseEntity<String> result = applicationService.deleteAllApplications();
		
		assertNotNull(result);
		assertEquals("All applications deleted successfully!", result.getBody());
		
	}
	
	@Test
	public void shouldNotDeleteAllApplications_WhenApplicationsDoesNotPresent() {
		
		System.out.println("Negative: applications does not exist in database!");

		doThrow(new RuntimeException("Jobs not present is database!")).when(applicationRepository).deleteAll();
		
		try {
			applicationService.deleteAllApplications();
		}
		catch (Exception e) {
			assertEquals("Jobs not present is database!", e.getMessage());

		}
		
		verify(applicationRepository, timeout(1)).deleteAll();
	}
	
	@Test
	public void shouldDeleteApplicationById_WhenDeleteApplicationByIdIsCalled() {
		
		System.out.println("Delete an application from database!");

		Application_Entity application = applications.get(0);
		
		doNothing().when(applicationRepository).deleteById(1);
		
		ResponseEntity<String> result = applicationService.deleteApplicationById(application.getId());
		
		assertNotNull(result);
		assertEquals("Application deleted successfully with id = 1", result.getBody());
		
	}
	
	@Test
	public void shouldNotDeleteApplicationById_WhenApplicationByIdDoesNotPresent() {
		
		System.out.println("Negative: application does not exist in database!");

		doThrow(new RuntimeException("Job not present with id in database!")).when(applicationRepository).deleteById(99);
		
		try {
			applicationService.deleteApplicationById(99);
		}
		catch (Exception e) {
			assertEquals("Job not present with id in database!", e.getMessage());

		}
		
		verify(applicationRepository, timeout(1)).deleteById(99);
	}
	
}
