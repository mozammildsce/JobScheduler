package perfios.intern.dto;

public class AllJobDto {
	private String jobId;
	private Integer parameter;
	
	public Integer getParameter() {
		return parameter;
	}
	public void setParameter(Integer parameter) {
		this.parameter = parameter;
	}
	private String jobName;
	private String jobGroup;
	private String jobStatus;
	private String jobClass;
	private String description;
	public String getDescription() {
		return description;
	}
	public void setJobDescription(String description) {
		this.description = description;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
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
	
}
