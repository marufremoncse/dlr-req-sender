package com.codingsense.sender.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Route {
	private String apiRoot = "https://api.mnpspbd.com";
	private String userIdParam = "user";
	private String passParam = "password";
	private String msisdnParam = "GSM";
	private String senderParam = "sender";
	private String smsTextParam = "SMSText";
	private String userIdValue;
	private String passValue;
	private String senderValue = "";
	
	public Route(){
		this.apiRoot = "https://api.mnpspbd.com";
		this.userIdParam = "user";
		this.passParam = "password";
		this.msisdnParam = "GSM";
		this.senderParam = "sender";
		this.smsTextParam = "SMSText";
		this.userIdValue = "";
		this.passValue = "";
		this.senderValue = "";
	}
}
