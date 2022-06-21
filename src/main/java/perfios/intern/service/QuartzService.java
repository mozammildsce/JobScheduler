package perfios.intern.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import perfios.intern.model.AllJobs;
import perfios.intern.model.AuditTable;
import perfios.intern.model.QrtzTriggers;
import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.model.Tenant;

public interface QuartzService {
	List<SchedulerJobInfo>getAllJobs();
	List<AllJobs>getAllJob();
	List<QrtzTriggers>getAllTriggers();
	List<AuditTable>getAllExecutedJobNames();
	QrtzTriggers getTriggerById(String id);
	List<AllJobs> getJobByJobId(String jobId);
	List<AuditTable> findByexecutionDateBetween(LocalDate fromDate,LocalDate toDate);
	List<AuditTable> findByKeyword(String keyword);
	List<SchedulerJobInfo> findByJobNames(String jobName);
	List<Tenant> getAllTenants();
}
