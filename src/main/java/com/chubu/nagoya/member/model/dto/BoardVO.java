package com.chubu.nagoya.member.model.dto;

import java.util.Date;

public class BoardVO {
	private int brdNo;
	private String brdComment;
	private String brdWriter;
	private Date brdDate;
	
	public int getBrdNo() {
		return brdNo;
	}
	public void setBrdNo(int brdNo) {
		this.brdNo = brdNo;
	}
	public String getBrdComment() {
		return brdComment;
	}
	public void setBrdComment(String brdComment) {
		this.brdComment = brdComment;
	}
	public String getBrdWriter() {
		return brdWriter;
	}
	public void setBrdWriter(String brdWriter) {
		this.brdWriter = brdWriter;
	}
	public Date getBrdDate() {
		return brdDate;
	}
	public void setBrdDate(Date brdDate) {
		this.brdDate = brdDate;
	}
	
	// toString()
    @Override
    public String toString() {
        return "BoardVO [brdNo=" + brdNo + ", brdComment=" + brdComment + ", brdWriter=" + brdWriter + ", brdDate="
                + brdDate + "]";
    }
}
