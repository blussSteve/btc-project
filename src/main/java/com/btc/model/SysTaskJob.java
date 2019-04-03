package com.btc.model;

import java.util.Date;

public class SysTaskJob {
    private Long id;

    private Date startDate;

    private Date endDate;

    private Integer jobType;

    private Integer isSuccess;
 
    private String errorLog;
    
    

    public SysTaskJob(Date startDate, Date endDate, Integer jobType,
			Integer isSuccess, String errorLog) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.jobType = jobType;
		this.isSuccess = isSuccess;
		this.errorLog = errorLog;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorLog() {
        return errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}