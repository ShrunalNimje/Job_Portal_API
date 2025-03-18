package my.mood.JobPortalAPI.Job_Portal_API.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.mood.JobPortalAPI.Job_Portal_API.Entity.User_Entity;

@Repository
public interface UserRepository extends JpaRepository<User_Entity, Integer>{
	
}
