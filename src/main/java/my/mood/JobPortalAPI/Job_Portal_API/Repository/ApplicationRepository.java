package my.mood.JobPortalAPI.Job_Portal_API.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.Application_Entity;

@Repository
public interface ApplicationRepository extends JpaRepository<Application_Entity, Integer>{

	public Page<Application_Entity> findAll(Pageable pageable);
	public List<Application_Entity> findByJobId(int jobId);
	public List<Application_Entity> findByUserId(int userId);
	
}
