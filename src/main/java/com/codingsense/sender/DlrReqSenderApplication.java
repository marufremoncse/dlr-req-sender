package com.codingsense.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.codingsense.sender.service.AService;
import com.codingsense.sender.service.AppConfigService;
import com.codingsense.sender.service.BService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class DlrReqSenderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DlrReqSenderApplication.class, args);

		AppConfigService appConfigService = context.getBean(AppConfigService.class);
		AService aService = context.getBean(AService.class);
		BService bService = context.getBean(BService.class);
		char flag = appConfigService.getFlag();

		if (!appConfigService.isRunning()) {

			appConfigService.setRunning(1);

			while (appConfigService.isRunning()) {
				if (flag == 'A')
					aService.queueProcess();
				else if (flag == 'B')
					bService.queueProcess();

				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		} else {
			System.out.println("App is already running");
		}
		appConfigService.setRunning(0);
		System.out.println("App is closed!");
	}
}
