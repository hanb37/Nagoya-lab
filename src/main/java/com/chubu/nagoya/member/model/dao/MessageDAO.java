package com.chubu.nagoya.member.model.dao;

import java.util.Date;
import java.util.List;

import com.chubu.nagoya.member.model.dto.MessageInfo;

public interface MessageDAO {
	
	public List<MessageInfo> listMessages(Date date) throws Exception;	

}
