package com.codingsense.sender.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.model.A;
import com.codingsense.sender.model.B;
import com.codingsense.sender.model.DlrRequest;
import com.codingsense.sender.repository.ARepository;
import com.codingsense.sender.repository.BRepository;
import com.codingsense.sender.repository.DumpRepository;

@Service
public class MainService {
	@Autowired
	ARepository aRepository;

	@Autowired
	BRepository bRepository;

	@Autowired
	DumpRepository dumpRepository;

	@Autowired
	Route route;

	@Autowired
	FlagChange flagChange;

	@Autowired
	AppConfigService appConfigService;

	public void queueProcess(char flag) {
		System.out.println("Queue Process: " + flag);
		int numThreads = appConfigService.getNumThread();

		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

		List<DlrRequest> dlrList = new ArrayList<>();
		List<A> aList = new ArrayList<>();
		List<B> bList = new ArrayList<>();

		Boolean boolFlagChange;
		long numRows;
		if (flag == 'A') {
			numRows = aRepository.count();
			System.out.println("numOfRows - A: " + numRows);
			if (numRows > 0) {
				boolFlagChange = flagChange.flagChange('B'); // change flag for stopping data entry in A & data entry
																// into B
				if (boolFlagChange) {
					aList = aRepository.findByStatus('N');
					for (A a : aList) {
						DlrRequest dlrRequest = a;
						dlrList.add(dlrRequest);
					}
				}
			}
		} else {
			numRows = bRepository.count();
			System.out.println("numOfRows - B: " + numRows);
			if (numRows > 0) {
				boolFlagChange = flagChange.flagChange('A'); // change flag for stopping data entry in B & data entry
																// into A
				if (boolFlagChange) {
					bList = bRepository.findByStatus('N');
					for (B b : bList) {
						DlrRequest dlrRequest = b;
						dlrList.add(dlrRequest);
					}
				}
			}
		}

		int dlrListSize = dlrList.size();

		if (dlrListSize > 0) {
			numThreads = Math.min(dlrListSize, numThreads);

			int chunkSize = dlrListSize / numThreads;
			int remainingElement = dlrListSize % numThreads;

			List<List<DlrRequest>> chunksList = new ArrayList<>();
			int startIndex = 0;
			int endIndex = chunkSize;
			for (int i = 0; i < numThreads; i++) {
				List<DlrRequest> chunk = dlrList.subList(startIndex, endIndex);
				chunksList.add(chunk);

				startIndex += chunkSize;
				endIndex += chunkSize;

				if (i == numThreads - 2)
					endIndex += remainingElement;

			}
			for (List<DlrRequest> chunk : chunksList) {
				executorService
						.execute(new RequestProcessor(dumpRepository, aRepository, bRepository, chunk, route, flag));
			}

			executorService.shutdown();

			try {
				boolean terminated = executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

				if (terminated) {
					System.out.println("Thread terminated");

					dlrList.clear();
					aList.clear();
					bList.clear();
					chunksList.clear();

					boolFlagChange = flagChange.flagChange(flag); // again push data to current flag
					if (flag == 'A') {
						appConfigService.setFlag('B');
						aRepository.deleteAll();
					} else {
						appConfigService.setFlag('A');
						bRepository.deleteAll();
					}
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (flag == 'A') {
			appConfigService.setFlag('B');
		} else {
			appConfigService.setFlag('A');
		}

	}
}
