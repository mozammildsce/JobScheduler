package perfios.intern.service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import perfios.intern.model.AllJobs;
import perfios.intern.model.AuditTable;
import perfios.intern.model.QrtzTriggers;
import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.model.Tenant;
import perfios.intern.repository.AllJobsRepository;
import perfios.intern.repository.AuditTableRepository;
import perfios.intern.repository.QrtzTriggersRepository;
import perfios.intern.repository.SchedulerRepository;
import perfios.intern.repository.TenantRepository;

@Service
public class QuartzServiceImpl implements QuartzService{
	@Autowired 
	SchedulerRepository schedulerRepository;
	@Autowired
	TenantRepository tenantRepository;
	@Override
	public List<SchedulerJobInfo> getAllJobs() {
		return schedulerRepository.findAll();
	}
	@Autowired
	AllJobsRepository allJobsRepository;
	@Override
	public List<AllJobs> getAllJob() {
		// TODO Auto-generated method stub
		return allJobsRepository.findAll();
	}
	
	@Autowired
	QrtzTriggersRepository qrtzTriggersRepository;
	@Override
	public List<QrtzTriggers> getAllTriggers() {
		return (List<QrtzTriggers>) qrtzTriggersRepository.findAll();
	}
	@Override
	public QrtzTriggers getTriggerById(String id) {
		Optional<QrtzTriggers> optional=qrtzTriggersRepository.getTriggersByJobName(id);
		QrtzTriggers qrtzTriggers =null;
		if(optional.isPresent()) {
			qrtzTriggers=optional.get();
		}
		else {
			throw new RuntimeException("Employee not found for id:: "+id);
			}
		return qrtzTriggers;
	}
	@Autowired
	AuditTableRepository auditTableRepository;
	@Override
	public List<AuditTable> getAllExecutedJobNames() {
		// TODO Auto-generated method stub	
		return auditTableRepository.findAll();
	}
	public List<AuditTable> findByKeyword(String keyword){
		System.out.println("in service implementation of find by keyword");

		return auditTableRepository.findByKeyword(keyword);
	}
	@Override
	public List<AuditTable> findByexecutionDateBetween(LocalDate fromDate, LocalDate toDate) {
		// TODO Auto-generated method stub
		System.out.println(auditTableRepository.findByexecutionDateBetween(fromDate, toDate));
		return auditTableRepository.findByexecutionDateBetween(fromDate, toDate);
	}
	@Override
	public List<SchedulerJobInfo> findByJobNames(String jobName) {
		// TODO Auto-generated method stub
		return schedulerRepository.findByJobNames(jobName);
	}
	@Override
	public List<AllJobs> getJobByJobId(String jobId) {
		List<AllJobs> findJob=allJobsRepository.getJobByJObId(jobId);
		return findJob;
	}
	@Override
	public List<Tenant> getAllTenants() {
		// TODO Auto-generated method stub
		return tenantRepository.findAll();
	}
}
