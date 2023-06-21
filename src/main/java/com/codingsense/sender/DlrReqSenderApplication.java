package com.codingsense.sender;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.codingsense.sender.model.AppConfig;
import com.codingsense.sender.repository.AppConfigRepository;
import com.codingsense.sender.service.AService;
import com.codingsense.sender.service.AppConfigService;
import com.codingsense.sender.service.BService;

import lombok.AllArgsConstructor;


@SpringBootApplication
@AllArgsConstructor
public class DlrReqSenderApplication {
	
	private final AppConfigRepository appConfigRepository;
	private static AService aService;
	private static BService bService;

//    public DlrReqSenderApplication(AppConfigRepository appConfigRepository) {
//        this.appConfigRepository = appConfigRepository;
//    }

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(DlrReqSenderApplication.class, args);
		
		AppConfigService appConfigService = context.getBean(AppConfigService.class);
		boolean running = appConfigService.isRunning();
		aService = context.getBean(AService.class);
		
		if(!running) {
			running = appConfigService.setRunning();
			while(running) {
				aService.queueProcess();
                try {
                    Thread.sleep(1000);
                } 
                catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
			}
		} else {
			System.out.println("App is already running");
		}	
	}
}
