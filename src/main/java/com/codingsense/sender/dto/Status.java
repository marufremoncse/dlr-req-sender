package com.codingsense.sender.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Status {
	private String status;
	private long tt;
	private String trid;
	private String response;
}
