package perfios.intern.listener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import perfios.intern.config.QuartzConfig;
import perfios.intern.controller.SchedulingController;
import perfios.intern.model.AuditTable;
import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.repository.AuditTableRepository;
import perfios.intern.repository.QrtzTriggersRepository;
import perfios.intern.repository.SchedulerRepository;
import perfios.intern.service.QuartzService;
@Component		
public class MyJobExecutionListener implements JobListener{
	private static final Logger log = LoggerFactory.getLogger(SchedulingController.class);
	@Autowired
	AuditTableRepository auditTableRepository;
	@Autowired
	private SchedulerRepository schedulerRepository;
	public static final String LISTENER_NAME = "MyJobExecutionListener";
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		
		return LISTENER_NAME;
		
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		JobKey key = context.getJobDetail().getKey();
		
	}


	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		// TODO Auto-generated method stub
		String jobName = context.getJobDetail().getKey().getName();
		String nft;
		String pft;
		String cft;
		String sft;
		String runType="";
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss"); 
		AuditTable auditTable=new AuditTable();
		
		if(context.getPreviousFireTime()==null)
		{
			pft="First Run";
		}
		else {
			pft=formatter.format(context.getPreviousFireTime());
		}
		if(context.getNextFireTime()==null)
		{
			System.out.println("nextFireTimeIsNull");
			auditTable.setNextFireTime("none");
			runType="Manual";
			
		}
		else {
			System.out.println("nextFireTimeIsNotNull");
			nft=formatter.format(context.getNextFireTime());
			auditTable.setNextFireTime(nft);
			runType="Scheduled";
		}
		cft=formatter.format(context.getFireTime());
		sft=formatter.format(context.getScheduledFireTime());
		auditTable.setId(jobName);
		auditTable.setJobName(jobName);
		auditTable.setRunType(runType);
		auditTable.setPrevFireTime(pft);
		auditTable.setScheduledFireTime(sft);
		auditTable.setFireTime(cft);
		LocalDate lcd=LocalDate.now();
		auditTable.setExecutionDate(lcd);
		auditTable.setJobRunTime(context.getJobRunTime());
		auditTable=auditTableRepository.save(auditTable);
		System.out.println("fire time="+context.getFireTime());
		System.out.println("Next Fire time="+context.getNextFireTime());
		System.out.println("Prev Fire time="+context.getPreviousFireTime());
		System.out.println(context.getScheduledFireTime());
		System.out.println(context.getJobRunTime());
	}	
}