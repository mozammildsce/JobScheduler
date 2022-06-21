package perfios.intern.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import perfios.intern.config.QuartzConfig;
import perfios.intern.dto.AuditTableDto;
import perfios.intern.model.AllJobs;
import perfios.intern.model.AuditTable;
import perfios.intern.model.QrtzTriggers;
import perfios.intern.model.SchedulerJobInfo;
import perfios.intern.model.Tenant;
import perfios.intern.repository.AuditTableRepository;
import perfios.intern.repository.SchedulerRepository;
import perfios.intern.service.QuartzService;
import perfios.intern.service.QuartzServiceImpl;

@Controller
public class ViewController{
	@Autowired 
	private SchedulerRepository schedulerRepository;
	@Autowired
	private AuditTableRepository auditTableRepository;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;
	/*****************************Create New Job******************/
	@GetMapping("/api")
	public String showCreateForm(Model model) {

		SchedulerJobInfo schedulerJobInfo=new SchedulerJobInfo(); 
		model.addAttribute("schedulerJobInfo",schedulerJobInfo);
		return "createjob";
	}
	@GetMapping("/createJob")
	public String createJobForm(Model model)
	{
		AllJobs allJobs=new AllJobs();
		model.addAttribute("allJobs",allJobs);
		return "CreateJob1";
	}
	@GetMapping("/addTenant")
	public String addTenant(Model model)
	{
		Tenant tenant=new Tenant();
		model.addAttribute("tenant",tenant);
		return "AddTenant";
	}
	@Autowired
	private QuartzService quartzService;
	/**************************QuartzTriggerDetails**********************/

/*	@GetMapping("/quartzTriggerDetails") public String showTriggers(Model model) {	 
		model.addAttribute("listTriggers",quartzService.getAllTriggers());
		return "QrtzTriggersTable";
	}*/

	/************************List of Jobs**********************/

	@GetMapping("/listJobs") public String showJobs(Model model) {	 
		model.addAttribute("listjobs",quartzService.getAllJobs());
		return "ListJobs";
	}
	
	/************************AuditTable**********************/
	
	

	/************************TriggerTable**********************/
	@GetMapping("/quartzTriggerDetails") public String showTriggers(Model model) {	 
		  model.addAttribute("listTriggers",quartzService.getAllTriggers());
		  return "QrtzTriggersTable";
		  }

	/************************Status Page**********************/
	@GetMapping("/status/{id}")
	public String status(@PathVariable(value="id") String id, Model model) {
		SchedulerJobInfo schedulerJobInfo=schedulerRepository.findByJobName(id);
		QrtzTriggers qrtzTrigger=quartzService.getTriggerById(id);
		model.addAttribute("listTriggers",qrtzTrigger);
		return "status";
		
	}
//	@Autowired QuartzServiceImpl quartzServiceImpl;
//	@GetMapping("/filter")
//	public String gAt(Model model,AuditTableDto auditTableDto,@RequestParam String fromDate,String toDate){
//	System.out.println("inside /filter ");
//	LocalDate fd=null,td = null;
//	String key="";
//	System.out.println(fromDate);
//	System.out.println(toDate);
//		/*
//		 * fd=fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		 * td=toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		 * List<AuditTable>
//		 * aauditTable=auditTableRepository.findByexecutionDateBetween(fd,td);
//		 * System.out.println("byExecutionDate"); for(int i=0;i<aauditTable.size();i++)
//		 * { System.out.print(aauditTable.get(i).getJobName()); }
//		 */
//	
////	System.out.println("fd="+fd);
////	System.out.println("td="+td);
//		List<AuditTable> auditTable=auditTableRepository.(key);
//		
//		model.addAttribute("AuditTable",auditTable);
//	
//	return "AuditTable";
//}
	@GetMapping("/filterTable")
	public String filterTables(String keyword, Model model,String fromDate,String toDate) {
		System.out.println(keyword);
		System.out.println(fromDate);
		System.out.println(toDate);
		
		if(keyword!=null) {
			List<AuditTable> auditTable=auditTableRepository.findByKeyword(keyword);
			model.addAttribute("AuditTable",auditTable);
		}
		else {
			List<AuditTable> aauditTable=quartzService.getAllExecutedJobNames();
			model.addAttribute("AuditTable",aauditTable);
		}
		return "filterTable";
		
	}
	@GetMapping("/scheduleJobInstance")
	public String scheduleJobInstance(Model model)
	{
		List<Tenant> tenant=quartzService.getAllTenants();
		for(int i=0;i<tenant.size();i++)
			System.out.println(tenant.get(i).getName());
		model.addAttribute("listtenant",quartzService.getAllTenants());
		model.addAttribute("listjobs",quartzService.getAllJob());
		model.addAttribute("lj",quartzService.getAllJobs());
		return "scheduleJob";
	}



	
	/************************AuditTableForSpecificPage**********************/
	@GetMapping("/auditTable/{id}")
	public String auditTableForSpecificJob(@PathVariable(value="id") String id, Model model) {
		List<AuditTable> auditTable=auditTableRepository.findByJobName(id);
		model.addAttribute("job",auditTable);
		return "AuditTableForSpecificJob";
		
	}
	
	/*************************************CreateMessageJobs********************************/ 
	/*
	@GetMapping("/")
	public String showForm(Model model) {

		Message message=new Message(); 
		model.addAttribute("message",message);
		return "new_message";
	}
	@GetMapping("/dat")
	public String showDateForm(Model model) {

		DateTime dateTime=new DateTime(); 
		model.addAttribute("dateTime",dateTime);
		return "DateTime";
	}*/

}
