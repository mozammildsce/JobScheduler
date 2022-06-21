package perfios.intern.model;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "scheduler_job_info")
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerJobInfo {

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String schedulerId;
	private String builderId;
    
	private String jobName;
    private String jobGroup;
    private String jobStatus;
    private String jobClass;
    private String cronExpression;
    private String description;    
    private String interfaceName;
    private Long repeatTime;
    private Boolean cronJob;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="jobId",referencedColumnName="jobId")
    private AllJobs allJobs;
    
	public String getBuilderId() {
		return builderId;
	}
	public String getSchedulerId() {
		return schedulerId;
	}
	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}
	public AllJobs getAllJobs() {
		return allJobs;
	}
	public void setAllJobs(AllJobs allJobs) {
		this.allJobs = allJobs;
	}
	public void setBuilderId(String builderId) {
		this.builderId = builderId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Long getRepeatTime() {
		return repeatTime;
	}
	public void setRepeatTime(Long repeatTime) {
		this.repeatTime = repeatTime;
	}
	public Boolean getCronJob() {
		return cronJob;
	}
	public void setCronJob(Boolean cronJob) {
		this.cronJob = cronJob;
	}
	/*
	 * @OneToMany(targetEntity=AuditTable.class,cascade=CascadeType.ALL)
	 * 
	 * @JoinColumn(name="sji_fk",referencedColumnName="jobId") private
	 * List<AuditTable> auditTables;
	 */
}