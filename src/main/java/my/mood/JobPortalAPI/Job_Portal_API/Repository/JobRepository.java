package my.mood.JobPortalAPI.Job_Portal_API.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.Job_Entity;

@Repository
public interface JobRepository extends JpaRepository<Job_Entity, Integer>{
	
	public Page<Job_Entity> findAll(Pageable pageable);

	public Optional<Job_Entity> findByTitleAndPostedBy(String title, String string);
}
