package com.codingsense.sender.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.model.A;
import com.codingsense.sender.model.DlrRequest;
import com.codingsense.sender.repository.ARepository;


@Service
public class AService {
	@Autowired
	ARepository aRepository;
	
	@Autowired
	Route route;
	
	public void queueProcess() {
		
		System.out.println("Hello World");
//		List<A> aList = aRepository.findByStatus('N');
//		int numThreads = Runtime.getRuntime().availableProcessors();
//        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
//        
//        for (A a : aList) {
//        	DlrRequest dlrRequest = a;
//        	RequestProcessor rp = new RequestProcessor(dlrRequest, route);
//            executorService.submit(rp);
//        }
//
//        executorService.shutdown();
	}
}
