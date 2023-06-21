package com.codingsense.sender.model;

import java.time.LocalDateTime;

public interface DlrRequest {
	public long getId();

	public void setId(long id); 

	public String getMessageId(); 

	public void setMessageId(String messageId);

	public String getSentDate(); 

	public void setSentDate(String sentDate); 

	public String getDoneDate(); 

	public void setDoneDate(String doneDate); 

	public String getMessageStatus(); 

	public void setMessageStatus(String messageStatus); 

	public String getGsmError(); 

	public void setGsmError(String gsmError); 

	public String getPrice(); 

	public void setPrice(String price); 

	public String getPduCount();

	public void setPduCount(String pduCount); 

	public String getShortMessage(); 
	
	public void setShortMessage(String shortMessage); 
	
	public String getMobile(); 

	public void setMobile(String mobile); 

	public char getStatus(); 

	public void setStatus(char status); 

	public LocalDateTime getCreatedAt(); 

	public void setCreatedAt(LocalDateTime createdAt); 

	public LocalDateTime getUpdatedAt(); 

	public void setUpdatedAt(LocalDateTime updatedAt); 

	public String getApiResponse(); 

	public void setApiResponse(String apiResponse); 
}
