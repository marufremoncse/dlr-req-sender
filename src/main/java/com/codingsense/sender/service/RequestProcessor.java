package com.codingsense.sender.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.model.A;
import com.codingsense.sender.model.B;
import com.codingsense.sender.model.DlrRequest;
import com.codingsense.sender.model.Dump;
import com.codingsense.sender.repository.ARepository;
import com.codingsense.sender.repository.BRepository;
import com.codingsense.sender.repository.DumpRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RequestProcessor extends Thread {
	private DumpRepository dumpRepository;
	private ARepository aRepository;
	private BRepository bRepository;
	private List<DlrRequest> dlrRequestList;
	private Route route;
	private char flag;

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println("Thread:" + threadName);

		for (DlrRequest dlrRequest : dlrRequestList) {
			UUID uuid = UUID.randomUUID();
			LinkedHashMap<String, Object> main = new LinkedHashMap<>();

			main.put("username", "RanksITT_admin");
			main.put("password", "ritt@359-!");
			main.put("messageId", dlrRequest.getMessageId());

			String deliveryStatus = "";

			switch (dlrRequest.getMessageStatus()) {
			case "DELIVERED":
				deliveryStatus = "Delivered";
				break;
			case "NOT_DELIVERED":
				deliveryStatus = "Undelivered";
				break;
			default:
				deliveryStatus = "Delivery Pending";
			}
			main.put("status", deliveryStatus);
			main.put("errorCode", dlrRequest.getGsmError());
			main.put("mobile", dlrRequest.getMobile());
			main.put("shortMesssage", dlrRequest.getShortMessage());
			main.put("submitDate", dlrRequest.getSentDate());
			main.put("doneDate", dlrRequest.getDoneDate());

			String response = process(dlrRequest, main, route, uuid);

			Dump dump = new Dump();
			dump.setId(dlrRequest.getId());
			dump.setMessageId(dlrRequest.getMessageId());
			dump.setMessageStatus(dlrRequest.getMessageStatus());
			dump.setMobile(dlrRequest.getMobile());
			dump.setPduCount(dlrRequest.getPduCount());
			dump.setPrice(dlrRequest.getPrice());
			dump.setSentDate(dlrRequest.getSentDate());
			dump.setShortMessage(dlrRequest.getShortMessage());
			dump.setDoneDate(dlrRequest.getDoneDate());
			dump.setGsmError(dlrRequest.getGsmError());

			if (flag == 'A') {
				Optional<A> aOptional = aRepository.findById(dlrRequest.getId());
				A a = aOptional.get();
				a.setApiResponse(response);
				a.setStatus('P');
				dump.setApiResponse(response);
				dump.setStatus('A');
				aRepository.save(a);
			} else {
				Optional<B> bOptional = bRepository.findById(dlrRequest.getId());
				B b = bOptional.get();
				b.setApiResponse(response);
				b.setStatus('P');
				dump.setApiResponse(response);
				dump.setStatus('B');
				bRepository.save(b);
			}

			dumpRepository.save(dump);
		}
	}

	public String process(DlrRequest dlrRequest, HashMap<String, Object> jsonInput, Route route, UUID uuid) {
		String SMS_RESPONSE = "";
		try {
			String urlString = route.getApiRoot() + "/a2p-proxy-api-iptsp/api/v1/MessageStatus";
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setConnectTimeout(20000);
			con.setReadTimeout(20000);

			var objectMapper = new ObjectMapper();
			String jsonInputString = objectMapper.writeValueAsString(jsonInput);

			System.out.println("IATLID: " + uuid + " " + "PAYLOAD_JSON: " + jsonInputString);

			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				SMS_RESPONSE = response.toString();
				System.out.println("IATLID: " + uuid + " " + "RESPONSE: " + SMS_RESPONSE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}

		return SMS_RESPONSE;
	}

}
