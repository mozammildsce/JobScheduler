package perfios.intern.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import perfios.intern.model.SchedulerJobInfo;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, Long> {

	SchedulerJobInfo findByJobName(String jobName);
	SchedulerJobInfo findBybuilderId(String builderId);
	@Query(value="select *from scheduler_job_info s where s.job_name like %:jobName%",nativeQuery=true)
    List<SchedulerJobInfo> findByJobNames(String jobName);

}