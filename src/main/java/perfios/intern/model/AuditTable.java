	package perfios.intern.model;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "audit_table")
@ToString
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditTable {

	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; 
	
	private LocalDate executionDate;
	private String runType;
	public String getRunType() {
		return runType;
	}
	public void setRunType(String runType) {
		this.runType = runType;
	}
	private String jobName;
	private String prevFireTime;
	private String nextFireTime;
	private Long jobRunTime;
	private String fireTime;
	private String scheduledFireTime;
	@Transient
	private String fromDate;
	@Transient
	private String keyword;
	@Transient
	private String toDate;
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	public String getId() {
		return id;
	}
	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFireTime() {
		return fireTime;
	}
	public void setFireTime(String fireTime) {	
		this.fireTime = fireTime;
	}
	public Long getJobRunTime() {
		return jobRunTime;
	}
	public void setJobRunTime(Long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}
	public String getScheduledFireTime() {
		return scheduledFireTime;
	}
	public void setScheduledFireTime(String scheduledFireTime) {
		this.scheduledFireTime = scheduledFireTime;
	}
	
	public String getJobName() {
		return jobName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getPrevFireTime() {
		return prevFireTime;
	}
	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}
	public String getNextFireTime() {
		return nextFireTime;
	}
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}
	public LocalDate getExecutionDate() {
		return executionDate;
	}

	
	
	
	
	}