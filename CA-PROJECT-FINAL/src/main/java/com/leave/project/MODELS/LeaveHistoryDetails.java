package com.leave.project.MODELS;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.leave.project.UTILITIES.Status;
import com.leave.project.UTILITIES.Utils;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.CsvDate;

@Entity
public class LeaveHistoryDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leaveHistoryId;
	@ManyToOne
	@JoinColumn(name="Emp_Id")
	private Employee employee;
	@ManyToOne
	@JoinColumn(name="Leave_Type_Id")
	private LeaveType leaveType;
	@NotNull
	@CsvDate("dd-MM-yyyy")
	private Date startDate;
	@NotNull
	@CsvDate("dd-MM-yyyy")
	private Date endDate;
	@NotNull
	private String applyingReason;
	private String rejectionReason;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Status leaveStatus;
	private String workDesemination;
	
	
	public int getLeaveHistoryId() {
		return leaveHistoryId;
	}
	public void setLeaveHistoryId(int leaveHistoryId) {
		this.leaveHistoryId = leaveHistoryId;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public LeaveType getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getApplyingReason() {
		return applyingReason;
	}
	public void setApplyingReason(String applyingReason) {
		this.applyingReason = applyingReason;
	}
	public String getRejectionReason() {
		return rejectionReason;
	}
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
	public Status getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(Status status) {
		this.leaveStatus = status;
	}
	public String getWorkDesemination() {
		return workDesemination;
	}
	public void setWorkDesemination(String workDesemination) {
		this.workDesemination = workDesemination;
	}
	public LeaveHistoryDetails(int leaveHistoryId, Employee employee, LeaveType leaveType, @NotNull Date startDate,
			@NotNull Date endDate, @NotNull String applyingReason, String rejectionReason, @NotNull Status status,
			String workDesemination) {
		super();
		this.leaveHistoryId = leaveHistoryId;
		this.employee = employee;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.applyingReason = applyingReason;
		this.rejectionReason = rejectionReason;
		this.leaveStatus = status;
		this.workDesemination = workDesemination;
	}
	public LeaveHistoryDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "LeaveHistoryDetails [leaveHistoryId=" + leaveHistoryId + ", manager=" + employee.getFullName() + ", leaveType="
				+ leaveType.getType() + ", startDate=" + Utils.format(startDate) + ",endDate=" + Utils.format(endDate) + ", applyingReason=" + applyingReason
				+ ", rejectionReason=" + rejectionReason + ", leaveStatus=" + leaveStatus + ", workDesemination="
				+ workDesemination + "]";
	}
	public boolean check(String startDate2, String endDate2) {
		// TODO Auto-generated method stub
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	boolean isAfter = LocalDate.parse(sdf.format(this.startDate)).isAfter(LocalDate.parse(startDate2).minusDays(1));
    	boolean isBefore = LocalDate.parse(sdf.format(this.endDate)).isBefore(LocalDate.parse(endDate2).plusDays(1));
    	
    	if( isAfter && isBefore)
    			{
    				return true;
    			}    	
		return false;
	}
	public int getLeaveCount() {
		long diff = this.endDate.getTime() - this.startDate.getTime();
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	
	
}
