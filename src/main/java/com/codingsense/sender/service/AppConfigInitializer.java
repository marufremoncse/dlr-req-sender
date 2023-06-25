package com.codingsense.sender.service;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.codingsense.sender.model.AppConfig;
import com.codingsense.sender.repository.AppConfigRepository;

@Component
public class AppConfigInitializer implements CommandLineRunner {

	private final AppConfigRepository appConfigRepository;

	public AppConfigInitializer(AppConfigRepository appConfigRepository) {
		this.appConfigRepository = appConfigRepository;
	}

	@Override
	public void run(String... args) {
		AppConfig appConfig = AppConfig.builder().id(1).appConf(0).numThread(10).createdAt(LocalDateTime.now()).build();
		appConfigRepository.save(appConfig);
	}
}
