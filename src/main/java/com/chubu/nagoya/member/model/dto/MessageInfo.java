package com.chubu.nagoya.member.model.dto;

import java.util.Date;

public class MessageInfo {
	private String title;
	private String descript;
	private Date startTime;
	private Date endTime;
	private String mailFrom;
	private String location;
	
	@SuppressWarnings("deprecation")
	public double getNumberStartTime() {
				
		double hours = this.startTime.getHours();
		double minutes = this.startTime.getMinutes();
		
		if(minutes >= 30) {
			hours = hours + 0.5;
		}
		
		return hours;
	}

	@SuppressWarnings("deprecation")
	public double getNumberEndTime() {
		
		double hours = this.endTime.getHours();
		double minutes = this.endTime.getMinutes();
		
		if(minutes >= 30) {
			hours = hours + 0.5;
		}
		
		return hours;
	}

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public String getMailFrom() {
		return mailFrom;
	}
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
