package com.codingsense.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.model.A;
import com.codingsense.sender.model.B;
import com.codingsense.sender.model.DlrRequest;
import com.codingsense.sender.repository.BRepository;

@Service
public class BService implements ABService{
	@Autowired
	BRepository bRepository;
	
	@Autowired
	Route route;
	
	@Override
	public void queueProcess() {
		char flag = 'B';
		System.out.println("Queue Process - B");
		List<B> bList = bRepository.findByStatus('N');
		int numThreads = Runtime.getRuntime().availableProcessors();
		int chunkSize = 2;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        
        List<DlrRequest> dlrList = new ArrayList<>();
        
        for(B b:bList) {
        	DlrRequest dlrRequest = b;
        	dlrList.add(dlrRequest);
        }
        List<List<DlrRequest>> chunksList = new ArrayList<>();
        for (int i = 0; i < dlrList.size(); i += chunkSize) {
            int endIndex = Math.min(i + chunkSize, dlrList.size());
            List<DlrRequest> chunk = dlrList.subList(i, endIndex);
            chunksList.add(chunk);
        }
        
        for (List<DlrRequest> chunk : chunksList) {
        	executorService.execute(new RequestProcessor(chunk, route, flag));
        }

        executorService.shutdown();
	}
}
