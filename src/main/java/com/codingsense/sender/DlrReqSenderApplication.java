package com.codingsense.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.codingsense.sender.service.AppConfigService;
import com.codingsense.sender.service.MainService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class DlrReqSenderApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DlrReqSenderApplication.class, args);

		AppConfigService appConfigService = context.getBean(AppConfigService.class);
		MainService mainService = context.getBean(MainService.class);

		if (!appConfigService.isRunning()) {

			appConfigService.setRunning(1);

			while (appConfigService.isRunning()) {
				mainService.queueProcess(appConfigService.getFlag());

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
