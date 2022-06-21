package perfios.intern.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="qrtz_triggers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrtzTriggers {
	private String SCHED_NAME;
	private String TRIGGER_NAME;
	private String TRIGGER_GROUP;
	@Id
	@Column(name="JOB_NAME")
	public String JOB_NAME;
	
	
	private String JOB_GROUP;
	private String DESCRIPTION;
	private Long NEXT_FIRE_TIME;
	private Long PREV_FIRE_TIME;
	private int PRIORITY;
	private String TRIGGER_STATE;
	private String TRIGGER_TYPE;
	private Long START_TIME;
	private Long END_TIME;
	private String CALENDAR_NAME;
	private Short MISFIRE_INSTR;
	private Blob JOB_DATA;
	
	public String getSCHED_NAME() {
		return SCHED_NAME;
	}
	public void setSCHED_NAME(String sCHED_NAME) {
		SCHED_NAME = sCHED_NAME;
	}
	public String getTRIGGER_NAME() {
		return TRIGGER_NAME;
	}
	public void setTRIGGER_NAME(String tRIGGER_NAME) {
		TRIGGER_NAME = tRIGGER_NAME;
	}
	public String getTRIGGER_GROUP() {
		return TRIGGER_GROUP;
	}
	public void setTRIGGER_GROUP(String tRIGGER_GROUP) {
		TRIGGER_GROUP = tRIGGER_GROUP;
	}
	

	public String getJOB_GROUP() {
		return JOB_GROUP;
	}
	public void setJOB_GROUP(String jOB_GROUP) {
		JOB_GROUP = jOB_GROUP;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public Long getNEXT_FIRE_TIME() {
		return NEXT_FIRE_TIME;
	}
	public void setNEXT_FIRE_TIME(Long nEXT_FIRE_TIME) {
		NEXT_FIRE_TIME = nEXT_FIRE_TIME;
	}
	public Long getPREV_FIRE_TIME() {
		return PREV_FIRE_TIME;
	}
	public void setPREV_FIRE_TIME(Long pREV_FIRE_TIME) {
		PREV_FIRE_TIME = pREV_FIRE_TIME;
	}
	public int getPRIORITY() {
		return PRIORITY;
	}
	public void setPRIORITY(int pRIORITY) {
		PRIORITY = pRIORITY;
	}
	public String getTRIGGER_STATE() {
		return TRIGGER_STATE;
	}
	public void setTRIGGER_STATE(String tRIGGER_STATE) {
		TRIGGER_STATE = tRIGGER_STATE;
	}
	public String getTRIGGER_TYPE() {
		return TRIGGER_TYPE;
	}
	public void setTRIGGER_TYPE(String tRIGGER_TYPE) {
		TRIGGER_TYPE = tRIGGER_TYPE;
	}
	public Long getSTART_TIME() {
		return START_TIME;
	}
	public void setSTART_TIME(Long sTART_TIME) {
		START_TIME = sTART_TIME;
	}
	public Long getEND_TIME() {
		return END_TIME;
	}
	public void setEND_TIME(Long eND_TIME) {
		END_TIME = eND_TIME;
	}
	public String getCALENDAR_NAME() {
		return CALENDAR_NAME;
	}
	public void setCALENDAR_NAME(String cALENDAR_NAME) {
		CALENDAR_NAME = cALENDAR_NAME;
	}
	public Short getMISFIRE_INSTR() {
		return MISFIRE_INSTR;
	}
	public void setMISFIRE_INSTR(Short mISFIRE_INSTR) {
		MISFIRE_INSTR = mISFIRE_INSTR;
	}
	public Blob getJOB_DATA() {
		return JOB_DATA;
	}
	public void setJOB_DATA(Blob jOB_DATA) {
		JOB_DATA = jOB_DATA;
	}
}
