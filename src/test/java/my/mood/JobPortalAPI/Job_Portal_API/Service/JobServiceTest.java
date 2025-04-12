package my.mood.JobPortalAPI.Job_Portal_API.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import my.mood.JobPortalAPI.Job_Portal_API.DTO.JobDTO;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;
import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Role;
import my.mood.JobPortalAPI.Job_Portal_API.Exception.UserNotFoundException;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.JobRepository;
import my.mood.JobPortalAPI.Job_Portal_API.Repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

	@Mock
	JobRepository jobRepository;

	@Mock
	UserRepository userRepository;
	
	@Mock
	PasswordEncoder encoder;
	
	@Mock
	Principal principal;
	
	@InjectMocks
	JobService jobService;
	
	List<Job_Entity> jobs = null;
	
	@BeforeEach
	public void init() {
		
		System.out.println("Before All!");
		
		jobs = new ArrayList<Job_Entity>();
		
		jobs.add(new Job_Entity(1, "title_1", "description_1", "company_1", 1000, "location_1", new User_Entity()));
		jobs.add(new Job_Entity(2, "title_2", "description_2", "company_2", 1000, "location_2", new User_Entity()));
		jobs.add(new Job_Entity(3, "title_3", "description_3", "company_3", 1000, "location_3", new User_Entity()));
		
	}
	
	@Test
	public void shouldRetrieveAllJobs_whenRetrieveAllJobsIsCalled() {
		
		System.out.println("Retrieve All jobs back!");
		
		Pageable pageable = PageRequest.of(0, 3);
		Page<Job_Entity> page = new PageImpl<>(jobs, pageable, jobs.size());
		
		Mockito.when(jobRepository.findAll(pageable)).thenReturn(page);
		
		Page<Job_Entity> result = jobService.retrieveAllJobs(pageable);
		
		assertNotNull(result);
		assertEquals(3, result.getNumberOfElements());
		assertEquals("company_3", result.getContent().get(2).getCompany());
	}
	
	@Test
	public void retrieveAllJobs_ShouldreturnEmptyPage_WhenRetrieveAllJobsIsEmpty() {
		
		System.out.println("Nagative: No jobs found in database!");
		
		Pageable pageable = PageRequest.of(0, 3);
		Page<Job_Entity> emptyPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
		
		Mockito.when(jobRepository.findAll(pageable)).thenReturn(emptyPage);
		
		Page<Job_Entity> result = jobService.retrieveAllJobs(pageable);
		
		assertNotNull(result);
		assertEquals(0, result.getNumberOfElements());
		
	}
	
	@Test
	public void shouldRetrieveJobById_whenRetrieveJobByIdIsCalled() {
		
		System.out.println("Retrieve job by id back!");
		
		Mockito.when(jobRepository.findById(2)).thenReturn(Optional.of(jobs.get(1)));
		
		Optional<Job_Entity> result = jobService.retrieveJobById(2);
		
		assertNotNull(result);
		assertTrue(result.isPresent());
		assertEquals(2, result.get().getId());
		
	}
	
	@Test
	public void retrieveJobById_whenJobByIdDoesNotExist() {
		
		System.out.println("Negative: No job found in database!");
		
		Mockito.when(jobRepository.findById(99)).thenReturn(Optional.empty());
		
		Optional<Job_Entity> result = jobService.retrieveJobById(99);
		
		assertNotNull(result);
		assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void shouldPostJob_WhenPostJobIsCalled() {
		
		System.out.println("Job posted successfully!");
		
		Job_Entity job = new Job_Entity(4, "title_4", "description_4", "company_4", 1000, "location_4", new User_Entity());
		
		User_Entity employer = new User_Entity();
		employer.setEmail("user@gmail.com");
		employer.setRole(User_Role.EMPLOYER);
		
		Principal principal = Mockito.mock(Principal.class);
		Mockito.when(principal.getName()).thenReturn("user@gmail.com");
		
		Mockito.when(jobRepository.findById(job.getId())).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByEmail("user@gmail.com")).thenReturn(Optional.of(employer));
		Mockito.when(jobRepository.save(Mockito.any(Job_Entity.class))).thenReturn(job);
		
		ResponseEntity<String> result = jobService.postJob(job, principal);
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("job posted successfully with id = 4", result.getBody());
		
	}
	
	@Test
	public void doNotPostJob_WhenJobIsAlreadyExist() {
		
		System.out.println("negative: Job alerady exist in database!");
		
		Job_Entity existingJob = jobs.get(1);
		
		User_Entity employer = new User_Entity();
		employer.setEmail("user@gmail.com");
		employer.setRole(User_Role.EMPLOYER);
		
		Principal principal = Mockito.mock(Principal.class);
		
		Mockito.when(jobRepository.findById(existingJob.getId())).thenReturn(Optional.of(existingJob));
		
		ResponseEntity<String> result = jobService.postJob(existingJob, principal);
		
		assertNotNull(result);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		assertEquals("Job is already posted with id = 2", result.getBody());
		
	}
	
	@Test
	public void shouldDeleteAllJobs_WhenDeleteAllJobsIsCalled() {
		
		System.out.println("Delete all Jobs!");
		
		doNothing().when(jobRepository).deleteAll();
		
		ResponseEntity<String> result = jobService.deleteAlljobs();
		
		assertNotNull(result);
		assertEquals("All jobs deleted successfully!", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		verify(jobRepository, times(1)).deleteAll();
		
	}
	
	@Test
	public void shouldThrowExceptionForDeleteAllJobs_WhenJobsDoesNotExist() {
		
		System.out.println("Negative: No jobs in database");
		
		Mockito.doThrow(new RuntimeException("No jobs in database!")).when(jobRepository).deleteAll();
		
		try {
			jobService.deleteAlljobs();
		}
		catch(RuntimeException e) {
			assertEquals("No jobs in database!", e.getMessage());
		}
		
		verify(jobRepository, times(1)).deleteAll();
		
	}
	
	@Test
	public void shouldDeleteJobById_WhenDeleteJobByIdIsCalled() {
		
		System.out.println("Delete a Job by Id!");
		
		Mockito.when(jobRepository.findById(1)).thenReturn(Optional.of(jobs.get(0)));
		
		doNothing().when(jobRepository).deleteById(1);
		
		ResponseEntity<String> result = jobService.deleteJobById(1);
		
		assertNotNull(result);
		assertEquals("Job deleted successfully with id = 1", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		verify(jobRepository, times(1)).deleteById(1);
		
	}
	
	@Test
	public void shouldThrowExceptionForDeleteJobById_WhenJobNotExist() {
		
		System.out.println("Negative: No job in database");
		
		Mockito.when(jobRepository.findById(1)).thenReturn(Optional.of(jobs.get(0)));
		
		Mockito.doThrow(new RuntimeException("No job in database!")).when(jobRepository).deleteById(1);

		try {
			jobService.deleteJobById(1);
		}
		catch(RuntimeException e) {
			assertEquals("No job in database!", e.getMessage());
		}
		
		verify(jobRepository, times(1)).deleteById(1);
		
	}
	
	@Test
	public void shouldUpdateJobById_WhenUpdateJobByIdIsCalled() {
		
		Job_Entity existingJob = jobs.get(0);
		
		JobDTO updateJob = new JobDTO();
		updateJob.setSalary(12000);
		
		Mockito.when(jobRepository.findById(existingJob.getId())).thenReturn(Optional.of(existingJob));
		Mockito.when(jobRepository.save(Mockito.any(Job_Entity.class))).thenReturn(existingJob);
		
		ResponseEntity<String> result = jobService.updateJob(updateJob, existingJob.getId());
		
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals("Job updated successfully with id = 1", result.getBody());
		
	}
	
	@Test
	public void shouldNotUpdateJobById_WhenJobByIdDoesNotExist() {
				
		JobDTO updateJob = new JobDTO();
		updateJob.setSalary(12000);
		
		Mockito.when(jobRepository.findById(99)).thenReturn(Optional.empty());
				
		assertThrows(UserNotFoundException.class, () -> {
	        jobService.updateJob(updateJob, 99);
	    });
				
	}
	
}
