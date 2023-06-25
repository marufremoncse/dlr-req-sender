package com.codingsense.sender.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codingsense.sender.model.AppConfig;
import com.codingsense.sender.repository.AppConfigRepository;

import lombok.Data;

@Configuration
@Data
public class AppConfigService {

	@Autowired
	AppConfigRepository appConfigRepository;

	private char flag = 'A';
	private int numThread;

	@Bean
	char myCharBean() {
		return 'A';
	}

	public boolean isRunning() {
		try {
			Optional<AppConfig> appConfigOptional = appConfigRepository.findById(1);
			AppConfig appConfig = appConfigOptional.get();
			numThread = appConfig.getNumThread();

			if (appConfig.getAppConf() == 1)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean setRunning(int i) {
		try {
			Optional<AppConfig> appConfigOptional = appConfigRepository.findById(1);
			AppConfig appConfig = appConfigOptional.get();

			appConfig.setAppConf(i);
			appConfigRepository.save(appConfig);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
