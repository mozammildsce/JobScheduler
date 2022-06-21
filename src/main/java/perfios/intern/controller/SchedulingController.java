package perfios.intern.controller;

import java.io.IOException;
import org.quartz.listeners.JobChainingJobListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;

import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.quartz.listeners.JobChainingJobListener;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import perfios.intern.config.QuartzConfig;
import perfios.intern.dto.AllJobDto;
import perfios.intern.dto.AuditTableDto;
import perfios.intern.dto.SchedulerJobInfoDto;
import perfios.intern.dto.TenantDto;
import perfios.intern.job.MathsJob;

import perfios.intern.listener.MyJobExecutionListener;
import perfios.intern.model.AllJobs;
import perfios.intern.model.AuditTable;
import perfios.intern.model.Message;
import perfios.intern.model.QrtzTriggers;
import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.model.Tenant;
import perfios.intern.repository.AllJobsRepository;
import perfios.intern.repository.AuditTableRepository;
import perfios.intern.repository.QrtzTriggersRepository;
import perfios.intern.repository.SchedulerRepository;
import perfios.intern.repository.TenantRepository;
import perfios.intern.service.QuartzService;

@RestController
public class SchedulingController {
	private static final Logger log = LoggerFactory.getLogger(SchedulingController.class);
	@Autowired
	private QuartzConfig quartzConfig;
	@Autowired
	private TenantRepository tenantRepository;
	@Autowired
	private SchedulerRepository schedulerRepository;
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private QrtzTriggersRepository qrtzTriggersRepository;
	@Autowired
	private AllJobsRepository allJobsRepository;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	@Autowired
	private QuartzService quartzService;
	@Autowired MyJobExecutionListener myJobExecutionListener;


	/******************* Resuests ************/
	/******************* Run Once ***********/
	
	
	/*******************ChainJob**********/
	@RequestMapping(value = "/chainJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String chainJob(SchedulerJobInfo job) {

		String jobName = job.getJobName();
		System.out.println(jobName);
		SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(jobName);
		String desc = getJobInfo.getDescription();
		Message message = Message.failure();
		try {
			chainJob(job, desc);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	/******************* Pause ***********/
	

	/******************* Resume ***********/
	

	/*******************Statuss*************/
	
	/******************* Delete ***********/
	
	

	/******************* Update ***********/
	
	
	
	

	/************* functions ****************/
	
	/************statuss*******************/

	/************ Run Job Once **************/
	
	
	/************Chain Job********************/
	public void chainJob(SchedulerJobInfo schedulerJobInfo, String desc) throws ParseException, SchedulerException {
		SchedulerJobInfo getJobInfo = schedulerRepository.findByJobName(schedulerJobInfo.getJobName());
		TriggerKey triggerKey = TriggerKey.triggerKey(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup());
		Trigger trigger=scheduler.getTrigger(triggerKey);
		JobKey jobKey1 = JobKey.jobKey(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup());
		JobKey jobKey2 = JobKey.jobKey("MathsJo","CronJo");
		JobDetail jobDetail1 = scheduler.getJobDetail(new JobKey(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup()));
		System.out.println("error afterwards");
		JobDetail jobDetail2 = scheduler.getJobDetail(new JobKey("MathsJo","CronJo"));
		System.out.println("error afterwards2");
		JobChainingJobListener jobListener = new JobChainingJobListener("ChainListener");
		System.out.println("error afterwards3");
		jobListener.addJobChainLink(jobKey1, jobKey2);
		scheduler.addJob(jobDetail2, true);
		scheduler.getListenerManager().addJobListener(jobListener);
        scheduler.start();
	}

	/************ Resume Job ****************/
	@RequestMapping(value = "/resumeJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String resumeJob(SchedulerJobInfo job) {

		String jobName = job.getJobName();
		System.out.println(jobName);
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(job.getBuilderId());
		String desc = getJobInfo.getDescription();
		Message message = Message.failure();
		try {
			resumejob(job, desc);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	public void resumejob(SchedulerJobInfo schedulerJobInfo, String desc) throws ParseException, SchedulerException {
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(schedulerJobInfo.getBuilderId());
		String jobGroup = getJobInfo.getJobGroup();
		getJobInfo.setJobStatus("RESUMED");
		schedulerRepository.save(getJobInfo);
		try {
			schedulerFactoryBean.getScheduler().resumeJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/runJobOnce", method = { RequestMethod.GET, RequestMethod.POST })
	public String runJobOnce(SchedulerJobInfo job) {

		String jobName = job.getJobName();
		String builderId=job.getBuilderId();
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(builderId);
		System.out.println(getJobInfo);
		System.out.println(builderId);
		Message message = Message.failure();
		try {
			runJobonce(job);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	public void runJobonce(SchedulerJobInfo schedulerJobInfo) throws ParseException, SchedulerException {
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(schedulerJobInfo.getBuilderId());
		TriggerKey triggerKey = TriggerKey.triggerKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId());
		System.out.println(triggerKey);
		schedulerRepository.save(getJobInfo);
		try {
			schedulerFactoryBean.getScheduler().triggerJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getJobInfo.setJobStatus("SCHEDULED & STARTED");
		schedulerRepository.save(getJobInfo);
	}
	
	@RequestMapping(value = "/pauseJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String pauseJob(SchedulerJobInfo job) {

		String jobName = job.getJobName();
		String builderId=job.getBuilderId();
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(builderId);
		System.out.println(getJobInfo);
		System.out.println(builderId);
		String desc = getJobInfo.getDescription();
		Message message = Message.failure();
		try {
			pausejob(job, desc);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	/************* Pause Job ************/
	public void pausejob(SchedulerJobInfo schedulerJobInfo, String desc) throws ParseException, SchedulerException {
		System.out.println("inside the pausejob function="+schedulerJobInfo.getBuilderId());
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(schedulerJobInfo.getBuilderId());
		getJobInfo.setJobStatus("PAUSED");
		schedulerRepository.save(getJobInfo);
		try {
			schedulerFactoryBean.getScheduler().pauseJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*************** Delete Job **********/
	@RequestMapping(value = "/deleteJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteJob(SchedulerJobInfo job) {

		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(job.getBuilderId());
		String desc = getJobInfo.getDescription();
		Message message = Message.failure();
		try {
			deletejob(job, desc);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	public void deletejob(SchedulerJobInfo schedulerJobInfo, String desc) throws ParseException, SchedulerException {
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(schedulerJobInfo.getBuilderId());
		schedulerRepository.delete(getJobInfo);
		try {
			schedulerFactoryBean.getScheduler().deleteJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		@RequestMapping(value = "/removeJob", method = { RequestMethod.GET, RequestMethod.POST })
		public String removeJob(AllJobs job) {

			AllJobs getJobInfo = allJobsRepository.findByJobName(job.getJobName());
			System.out.println(getJobInfo.getJobName());
			String desc=getJobInfo.getDescription();
			Message message = Message.failure();
			try {
				removeJob(job, desc);
				message = Message.success();
			} catch (Exception e) {
				log.error("updateCron ex:", e);
			}
			return "true";
		}
	public void removeJob(AllJobs job, String desc)throws ParseException, SchedulerException {
			// TODO Auto-generated method stub
		AllJobs getJobInfo = allJobsRepository.findByJobName(job.getJobName());
		String jobName=getJobInfo.getJobName();
		System.out.println(jobName+"in remove job function");
		allJobsRepository.delete(getJobInfo);
		List<SchedulerJobInfo> scheduledJobs=schedulerRepository.findByJobNames(jobName);
		for (int i = 0; i < scheduledJobs.size(); i++) {
			
            SchedulerJobInfo sj=scheduledJobs.get(i);
            SchedulerJobInfo jobToBeDeleted = schedulerRepository.findBybuilderId(sj.getBuilderId());
            schedulerRepository.delete(jobToBeDeleted);
			try {
				schedulerFactoryBean.getScheduler().deleteJob(new JobKey(sj.getBuilderId()));
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//		allJobsRepository.delete(getJobInfo);
		}

	@RequestMapping(value = "/updateJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveOrUpdate(SchedulerJobInfo job) {
		SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(job.getBuilderId());
		getJobInfo.setCronExpression(job.getCronExpression());
		getJobInfo.setJobStatus("updated and schedule");
		System.out.println(getJobInfo.getAllJobs());
		schedulerRepository.save(getJobInfo);
		String desc = getJobInfo.getDescription();
		
		Message message = Message.failure();
		try {
			updatejob(job, desc);
				message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}

	/*************** Update Job ***********/
	private void updatejob(SchedulerJobInfo schedulerJobInfo, String desc) throws ParseException, SchedulerException {
		System.out.println(schedulerJobInfo.getSchedulerId());
		TriggerKey triggerKey = TriggerKey.triggerKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId());
		System.out.println(triggerKey);
		try {
			// trigger cron
			SchedulerJobInfo getJobInfo = schedulerRepository.findBybuilderId(schedulerJobInfo.getBuilderId());
			String triggerJobAt = schedulerJobInfo.getCronExpression();
			Trigger oldTrigger = scheduler.getTrigger(triggerKey);
			System.out.println("******************");
			System.out.println(oldTrigger);
			if (oldTrigger != null && oldTrigger instanceof CronTriggerImpl) {
				
				Trigger newTrigger = TriggerBuilder.newTrigger()
						.withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getBuilderId())
						.withSchedule(CronScheduleBuilder.cronSchedule(triggerJobAt)).build();
				schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey, newTrigger);
				System.out.println("inside update job");	
				schedulerFactoryBean.getScheduler().pauseJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
				schedulerFactoryBean.getScheduler().resumeJob(new JobKey(schedulerJobInfo.getJobName(),schedulerJobInfo.getBuilderId()));
					}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

	
	/****************** Run Job ***********************/
	

	/************************* Createjob *************************************/
	
//	@PostMapping(path = "/scheduler")
//	public @ResponseBody SchedulerJobInfoDto scheduleJob(SchedulerJobInfoDto sd) {
//		try {
//			SchedulerJobInfo schedulerJobInfo = new SchedulerJobInfo();
//			if (sd.getCronExpression().length() > 0) {
//				schedulerJobInfo.setCronJob(true);
//			}
//			schedulerJobInfo.setJobId(sd.getJobId());
//			System.out.println(sd.getJobName());
//			schedulerJobInfo.setJobName(sd.getJobName());
//			schedulerJobInfo.setJobGroup(sd.getJobGroup());
//			schedulerJobInfo.setJobStatus(sd.getJobStatus());
//			schedulerJobInfo.setJobClass(sd.getJobClass());
//			schedulerJobInfo.setCronExpression(sd.getCronExpression());
//			schedulerJobInfo.setDescription(sd.getDescription());
//			schedulerJobInfo.setInterfaceName(sd.getInterfaceName());
//			schedulerJobInfo.setRepeatTime(sd.getRepeatTime());
//			schedulerJobInfo.setCronJob(sd.getCronJob());
//			schedulerJobInfo = schedulerRepository.save(schedulerJobInfo);
//			scheduleNewJob(schedulerJobInfo);
//		} catch (Exception e) {
//			// scheduling failed
//			e.printStackTrace();
//		}
//		return sd;
//
//	}
	
	
	
	


	private void scheduleNewJob(SchedulerJobInfo schedulerJobInfo) {
		try {
			
			String jobclass=schedulerJobInfo.getJobClass();
			String jobName=schedulerJobInfo.getJobName();
			String jobGroup=schedulerJobInfo.getJobGroup();
			System.out.println(jobclass+" "+jobName+"    "+jobGroup);
			JobDetail jobDetail = JobBuilder.newJob(MathsJob.class)
					.withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup()).storeDurably().build();
			String triggerJobAt = schedulerJobInfo.getCronExpression();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup())
					.withSchedule(CronScheduleBuilder.cronSchedule(triggerJobAt)).build();
			System.out.println(trigger);
			Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();
			
		
			//scheduler.getListenerManager().addJobListener( myJobExecutionListener, KeyMatcher.keyEquals(jobKey));
			//scheduler.ListenerManager.AddTriggerListener(new MyJobExecutionListener(), GroupMatcher<TriggerKey>.AnyGroup());
			//scheduler.getListenerManager().addJobListener(MyJobExecutionListener, allJobs());
			scheduler.scheduleJob(jobDetail, trigger);

			scheduler.start();
		} catch (Exception e) {
			// scheduling failed
			e.printStackTrace();
		}
	}
	@PostMapping(path="/addTenant") 
	public @ResponseBody TenantDto addTenant(TenantDto tenantDto)
	{
		try {
			Tenant tenant=new Tenant();
			tenant.setDisplayName(tenantDto.getDisplayName());
			tenant.setName(tenantDto.getName());
			tenant=tenantRepository.save(tenant);
		}
		catch(Exception e) {
			// scheduling failed
			e.printStackTrace();
		}
		return tenantDto;
	}
	@PostMapping(path = "/addJob")
	public @ResponseBody AllJobDto addJob(AllJobDto allJobDto) {
		try {
			AllJobs allJobs = new AllJobs();
			System.out.println(allJobDto.getParameter());
			System.out.println(allJobDto.getDescription());
			allJobs.setParameter(allJobDto.getParameter());
			allJobs.setJobName(allJobDto.getJobName());
			allJobs.setJobGroup(allJobDto.getJobGroup());
			allJobs.setJobClass(allJobDto.getJobClass());
			allJobs.setDescription(allJobDto.getDescription());
			allJobs.setJobStatus(allJobDto.getJobStatus());
			System.out.println(allJobs.getParameter());
			System.out.println(allJobs.getDescription());
			allJobs = allJobsRepository.save(allJobs);
		} catch (Exception e) {
			// scheduling failed
			e.printStackTrace();
		}
		return allJobDto;

	}
	
	
	
	


//	private void scheduleNewJob(SchedulerJobInfo schedulerJobInfo) {
//		try {
//			
//			String jobclass=schedulerJobInfo.getJobClass();
//			String jobName=schedulerJobInfo.getJobName();
//			String jobGroup=schedulerJobInfo.getJobGroup();
//			System.out.println(jobclass+" "+jobName+"    "+jobGroup);
//			JobDetail jobDetail = JobBuilder.newJob(MathsJob.class)
//					.withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup()).storeDurably().build();
//			String triggerJobAt = schedulerJobInfo.getCronExpression();
//			Trigger trigger = TriggerBuilder.newTrigger()
//					.withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup())
//					.withSchedule(CronScheduleBuilder.cronSchedule(triggerJobAt)).build();
//			System.out.println(trigger);
//			Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();
//			
//		
//			//scheduler.getListenerManager().addJobListener( myJobExecutionListener, KeyMatcher.keyEquals(jobKey));
//			//scheduler.ListenerManager.AddTriggerListener(new MyJobExecutionListener(), GroupMatcher<TriggerKey>.AnyGroup());
//			//scheduler.getListenerManager().addJobListener(MyJobExecutionListener, allJobs());
//			scheduler.scheduleJob(jobDetail, trigger);
//
//			scheduler.start();
//		} catch (Exception e) {
//			// scheduling failed
//			e.printStackTrace();
//		}
//	}
	
	@RequestMapping(value = "/scheduleInstanceOfJob", method = { RequestMethod.GET, RequestMethod.POST })
	public String scheduleInstanceOfJob(AllJobs jobs,SchedulerJobInfo job) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+ "0123456789"+ "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(5);
		for (int i = 0; i < 5; i++) {
			int index= (int)(AlphaNumericString.length()* Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		String builderId=sb.toString();
		Message message = Message.failure();
		try {
			scheduleInstance(jobs,job,builderId);
			message = Message.success();
		} catch (Exception e) {
			log.error("updateCron ex:", e);
		}
		return "true";
	}
	public void scheduleInstance(AllJobs allJobs,SchedulerJobInfo job, String builderId) {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("inside the function");
			System.out.println(allJobs.getJobName());
			System.out.println(allJobs.getJobId());
			String jobId=allJobs.getJobId();
			System.out.println(allJobsRepository.findById(jobId));
			Optional<AllJobs> findJob=allJobsRepository.findById(jobId);
			AllJobs jobFound=null;
			if(findJob.isPresent())
			{
				jobFound=findJob.get();
			}
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends QuartzJobBean>) Class.forName("perfios.intern.job."+job.getJobClass()))
					.withIdentity(allJobs.getJobName(),builderId).storeDurably().build();
			String triggerJobAt = job.getCronExpression();
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(allJobs.getJobName(),builderId)
					.withSchedule(CronScheduleBuilder.cronSchedule(triggerJobAt)).build();
			Scheduler scheduler = quartzConfig.schedulerFactoryBean().getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			SchedulerJobInfo schedulerJobInfo=new SchedulerJobInfo();
			String jobName = job.getJobName();
			schedulerJobInfo.setAllJobs(jobFound);
			schedulerJobInfo.setBuilderId(builderId);
			System.out.println(job.getJobName());
			schedulerJobInfo.setJobName(job.getJobName());
			schedulerJobInfo.setJobGroup(job.getJobGroup());
			schedulerJobInfo.setJobStatus(job.getJobStatus());
			schedulerJobInfo.setJobClass(job.getJobClass());
			schedulerJobInfo.setCronExpression(job.getCronExpression());
			schedulerJobInfo.setDescription(job.getDescription());
			schedulerJobInfo.setInterfaceName(job.getInterfaceName());
			schedulerJobInfo.setRepeatTime(job.getRepeatTime());
			schedulerJobInfo.setCronJob(job.getCronJob());
			schedulerJobInfo = schedulerRepository.save(schedulerJobInfo);
			System.out.println(schedulerJobInfo.getBuilderId());
			System.out.println("inside schedule instance of job");
			System.out.println(jobName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	
	
//	@Autowired
//	AuditTableRepository auditTableRepository;
//	@PostMapping(value = "/filter")
//	public  String auditTable(AuditTableDto auditTableDto,Model model) {  
//		System.out.println(auditTableDto);
//			Date fromDate=auditTableDto.getFromDate();
//			System.out.println(fromDate);
//			Date toDate=auditTableDto.getToDate();
//			LocalDate fd=fromDate.toInstant()
//				      .atZone(ZoneId.systemDefault())
//				      .toLocalDate();
//			LocalDate td=toDate.toInstant()
//				      .atZone(ZoneId.systemDefault())
//				      .toLocalDate();
//			System.out.println(fd);
//			System.out.println(td);
//			//System.out.println(a);
//			List<AuditTable> auditTable=auditTableRepository.findByexecutionDateBetween(fd,td);
//			System.out.println(auditTable);
//			for(int i=0;i<auditTable.size();i++)
//			{
//				System.out.println(auditTable.get(i).getJobName());
//			}	
//			Message message = Message.failure();
//			try {
//				message = Message.success();
//			} catch (Exception e) {
//				log.error("updateCron ex:", e);
//			}
//			return "redirect:/auditTable";
//		}
//	
	/************************* Updating Cron Tirgger *****************************/
	/*
	 * @PostMapping(path = "/updateJob") public @ResponseBody SchedulerJobInfoDto
	 * update(SchedulerJobInfoDto sd) { try { SchedulerJobInfo schedulerJobInfo= new
	 * SchedulerJobInfo(); if(sd.getCronExpression().length()>0) {
	 * schedulerJobInfo.setCronJob(true); }
	 * schedulerJobInfo.setJobId(sd.getJobId());
	 * schedulerJobInfo.setJobName(sd.getJobName());
	 * schedulerJobInfo.setJobGroup(sd.getJobGroup());
	 * schedulerJobInfo.setJobStatus(sd.getJobStatus());
	 * schedulerJobInfo.setJobClass(sd.getJobClass());
	 * schedulerJobInfo.setCronExpression(sd.getCronExpression());
	 * schedulerJobInfo.setDescription(sd.getDescription());
	 * schedulerJobInfo.setInterfaceName(sd.getInterfaceName());
	 * schedulerJobInfo.setRepeatTime(sd.getRepeatTime());
	 * schedulerJobInfo.setCronJob(sd.getCronJob());
	 * schedulerJobInfo=schedulerRepository.save(schedulerJobInfo);
	 * updateScheduleJob(schedulerJobInfo); updateJob(schedulerJobInfo); } catch
	 * (Exception e) { // scheduling failed e.printStackTrace(); }return sd;
	 * 
	 * }
	 */
//	private void updateScheduleJob(SchedulerJobInfo schedulerJobInfo) {
//		String triggerJobAt=schedulerJobInfo.getCronExpression();
//		System.out.println(schedulerJobInfo.getJobName());
//		Trigger newTrigger = TriggerBuilder.newTrigger() .withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup()) .withSchedule(CronScheduleBuilder
//				.cronSchedule(triggerJobAt)).build();
//		try {
//			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(schedulerJobInfo.getJobName()), newTrigger);
//			schedulerJobInfo.setJobStatus("EDITED & SCHEDULED");
//			schedulerRepository.save(schedulerJobInfo);
//		} catch (SchedulerException e) {
//			log.error(e.getMessage(), e);
//		}
//		  Scheduler scheduler = schedulerFactoryBean.getScheduler();
//		    TriggerKey triggerKey = new TriggerKey("timeSyncTrigger");
//		    CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
//		    trigger.setCronExpression(triggerJobAt );
//		    scheduler.rescheduleJob(triggerKey, trigger);
//		}

	/*
	 * private void updateJob(SchedulerJobInfo schedulerJobInfo) throws
	 * ParseException, SchedulerException { String
	 * triggerJobAt=schedulerJobInfo.getCronExpression(); Scheduler scheduler =
	 * schedulerFactoryBean.getScheduler(); TriggerKey triggerKey = new
	 * TriggerKey(schedulerJobInfo.getJobName()); System.out.println(triggerKey);
	 * CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
	 * //CronTriggerImpl trigger = (CronTriggerImpl)
	 * scheduler.getTrigger(triggerKey);
	 * trigger.setCronExpression("0/10 * * * * ?");
	 * scheduler.rescheduleJob(triggerKey,trigger); }
	 */
	/*
	 * private void updateJob(SchedulerJobInfo schedulerJobInfo) throws
	 * ParseException, SchedulerException{ TriggerKey
	 * triggerKey=TriggerKey.triggerKey(schedulerJobInfo.getJobName(),
	 * schedulerJobInfo.getJobGroup()); System.out.println(triggerKey); try { //
	 * trigger cron String triggerJobAt=schedulerJobInfo.getCronExpression();
	 * Trigger oldTrigger = scheduler.getTrigger(triggerKey); if (oldTrigger!=null
	 * && oldTrigger instanceof CronTriggerImpl) { String cronExpression =
	 * ((CronTriggerImpl) oldTrigger).getCronExpression(); Trigger newTrigger =
	 * TriggerBuilder.newTrigger() .withIdentity(schedulerJobInfo.getJobName(),
	 * schedulerJobInfo.getJobGroup()) .withSchedule(CronScheduleBuilder
	 * .cronSchedule(triggerJobAt)).build();
	 * schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey, newTrigger);
	 * schedulerJobInfo.setJobStatus("EDITED & SCHEDULED");
	 * schedulerRepository.save(schedulerJobInfo); } } catch (SchedulerException e)
	 * { log.error(e.getMessage(), e); } }
	 */
}

/************************** Ignore ***********************************/
/*
 * private void updateScheduleJob(SchedulerJobInfo schedulerJobInfo) { try {
 * Trigger newTrigger = TriggerBuilder.newTrigger()
 * .withIdentity(schedulerJobInfo.getJobName(), schedulerJobInfo.getJobGroup())
 * .withSchedule(CronScheduleBuilder
 * .cronSchedule(schedulerJobInfo.getCronExpression())).build();
 * 		
 * schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(
 * schedulerJobInfo.getJobName()), newTrigger);
 * schedulerJobInfo.setJobStatus("EDITED & SCHEDULED");
 * schedulerRepository.save(schedulerJobInfo); log.info(">>>>> jobName = [" +
 * schedulerJobInfo.getJobName() + "]" + " updated and scheduled."); } catch
 * (SchedulerException e) { log.error(e.getMessage(), e); } }
 * 
 * 
 * @DeleteMapping(path = "/{messageId}/unschedule-visibility")
 * public @ResponseBody MessageDto
 * unscheduleMessageVisibility(@PathVariable(name = "messageId") Integer
 * messageId) {
 * 
 * MessageDto messageDto = new MessageDto();
 * 
 * Optional<Message> messageOpt = messageRepository.findById(messageId); if
 * (!messageOpt.isPresent()) { messageDto.setStatus("Message Not Found"); return
 * messageDto; }
 * 
 * Message message = messageOpt.get(); message.setVisible(false);
 * messageRepository.save(message);
 * 
 * String id = String.valueOf(message.getId());
 * 
 * try { Scheduler scheduler =
 * quartzConfig.schedulerFactoryBean().getScheduler();
 * 
 * scheduler.deleteJob(new JobKey(id)); TriggerKey triggerKey = new
 * TriggerKey(id); scheduler.unscheduleJob(triggerKey);
 * messageDto.setStatus("SUCCESS");
 * 
 * } catch (IOException | SchedulerException e) {
 * messageDto.setStatus("FAILED"); e.printStackTrace(); } return messageDto; }
 * 
 * @PostMapping(path = "/schedule-visibility") public @ResponseBody MessageDto
 * scheduleMessageVisibility(MessageDto messageDto) { try {
 * 
 * // saving messages in table
 * 
 * Message message = new Message(); message.setContent(messageDto.getContent());
 * message.setVisible(false);
 * message.setMakeVisibleAt(messageDto.getMakeVisibleAt());
 * 
 * message = messageRepository.save(message);
 * 
 * // Creating JobDetail instance String id = String.valueOf(message.getId());
 * JobDetail jobDetail = JobBuilder.newJob(MessageJob.class)
 * .withIdentity("MessageJob34", "MessageJob34").storeDurably().build();
 * 
 * // Adding JobDataMap to jobDetail jobDetail.getJobDataMap().put("messageId",
 * id);
 * 
 * // Scheduling time to run job // String triggerJobAt =
 * message.getMakeVisibleAt(); Trigger trigger = TriggerBuilder.newTrigger()
 * .withIdentity("MessageTrigger3", "MessageTrigger2")
 * .withSchedule(CronScheduleBuilder .cronSchedule(triggerJobAt)).build();
 * 
 * 
 * // Getting scheduler instance Scheduler scheduler =
 * quartzConfig.schedulerFactoryBean().getScheduler();
 * scheduler.scheduleJob(jobDetail, trigger); scheduler.start();
 * 
 * messageDto.setStatus("SUCCESS");
 * 
 * } catch (IOException | SchedulerException e) { // scheduling failed
 * messageDto.setStatus("FAILED"); e.printStackTrace(); } return messageDto; }
 * 
 */