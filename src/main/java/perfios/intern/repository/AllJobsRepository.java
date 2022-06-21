package perfios.intern.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.model.AllJobs;
@Repository
public interface AllJobsRepository extends JpaRepository<AllJobs, String> {
	AllJobs findByJobName(String jobName);
	@Query(value="select * from all_jobs a where a.job_id:jobId", nativeQuery=true)
	List<AllJobs> getJobByJObId(String jobId);
}