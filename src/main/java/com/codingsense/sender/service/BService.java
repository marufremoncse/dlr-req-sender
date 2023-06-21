package com.codingsense.sender.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.model.B;
import com.codingsense.sender.model.DlrRequest;
import com.codingsense.sender.repository.ARepository;
import com.codingsense.sender.repository.BRepository;

@Service
public class BService {
	@Autowired
	BRepository bRepository;
	
	@Autowired
	Route route;
	
	public void queueProcess() {
		
		List<B> bList = bRepository.findByStatus('N');
		int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        
        for (B b : bList) {
        	DlrRequest dlrRequest = b;
        	RequestProcessor rp = new RequestProcessor(dlrRequest, route);
            executorService.submit(rp);
        }

        executorService.shutdown();
	}
}
