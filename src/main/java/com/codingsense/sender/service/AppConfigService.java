package com.codingsense.sender.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.model.AppConfig;
import com.codingsense.sender.repository.AppConfigRepository;

@Service
public class AppConfigService {
	
	@Autowired
	AppConfigRepository appConfigRepository;
	
	public boolean isRunning() {
		try {
			Optional<AppConfig> appConfigOptional = appConfigRepository.findById(1);
			AppConfig appConfig = appConfigOptional.get();
			
			if(appConfig.getAppConf()==1)
				return true;
			else return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean setRunning() {
		try {
			Optional<AppConfig> appConfigOptional = appConfigRepository.findById(1);
			AppConfig appConfig = appConfigOptional.get();
			
			appConfig.setAppConf(1);
			appConfigRepository.save(appConfig);
            return true; 
        } catch (Exception e) {
            return false; 
        }
	}
}
