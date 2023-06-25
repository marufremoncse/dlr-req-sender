package com.codingsense.sender.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Route {
	private String apiRoot;
	private String userIdParam;
	private String passParam;
	private String msisdnParam;
	private String senderParam;
	private String smsTextParam;
	private String userIdValue;
	private String passValue;
	private String senderValue;

	public Route() {
		this.apiRoot = "http://localhost:8080"; // "https://api.mnpspbd.com"
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
