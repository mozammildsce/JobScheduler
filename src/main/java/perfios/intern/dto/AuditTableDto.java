package perfios.intern.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Transient;

public class AuditTableDto {
private String id; 
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
private Date fromDate;
private Date toDate;
private String keyword;

public String getKeyword() {
	return keyword;
}


public void setKeyword(String keyword) {
	this.keyword = keyword;
}


public Date getFromDate() {
	return fromDate;
}


public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
}




public Date getToDate() {
	return toDate;
}


public void setToDate(Date toDate) {
	this.toDate = toDate;
}

private LocalDate executionDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public LocalDate getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(LocalDate executionDate) {
		this.executionDate = executionDate;
	}
	public String getJobName() {
		return jobName;
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
	public Long getJobRunTime() {
		return jobRunTime;
	}
	public void setJobRunTime(Long jobRunTime) {
		this.jobRunTime = jobRunTime;
	}
	public String getFireTime() {
		return fireTime;
	}
	public void setFireTime(String fireTime) {
		this.fireTime = fireTime;
	}
	public String getScheduledFireTime() {
		return scheduledFireTime;
	}
	public void setScheduledFireTime(String scheduledFireTime) {
		this.scheduledFireTime = scheduledFireTime;
	}
	
	}
