package com.codingsense.sender.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingsense.sender.dto.Route;
import com.codingsense.sender.dto.Status;
import com.codingsense.sender.impl.DlrRequestImpl;
import com.codingsense.sender.model.A;
import com.codingsense.sender.model.DlrRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class RequestProcessor extends Thread{
	private DlrRequest dlrRequest;
	private Route route;
	
	public void run() {
		String threadName = Thread.currentThread().getName();
        System.out.println("Task executed by Thread #" + threadName + " for user: " + dlrRequest);
        UUID uuid = UUID.randomUUID();
        LinkedHashMap<String, Object> main = new LinkedHashMap<>();
        
        main.put("username", "RanksITT_admin");
		main.put("password", "ritt@359-!");
		main.put("messageId", dlrRequest.getMessageId());
		
		String deliveryStatus = "";
		
		switch(dlrRequest.getMessageStatus()) {
			case "DELIVERED":
				deliveryStatus = "Delivered";
				break;
			case "NOT_DELIVERED":
				deliveryStatus = "Undelivered";
				break;
			default:
				deliveryStatus = "Delivery Pending";
				break;
		}
		main.put("status", deliveryStatus);
		main.put("errorCode", dlrRequest.getGsmError());
		main.put("mobile", dlrRequest.getMobile());
		main.put("shortMesssage", dlrRequest.getShortMessage());
		main.put("submitDate", dlrRequest.getSentDate());
		main.put("doneDate", dlrRequest.getDoneDate());
		
        process(dlrRequest, main, route, uuid);
	}
	
	public void process(DlrRequest dlrRequest, HashMap<String, Object> jsonInput, Route route, UUID uuid) {
		Status st = new Status();
		st.setStatus("");
		String SMS_RESPONSE = "";
		String status = "";
		long tm = System.currentTimeMillis();
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

			System.out.println("IATLID: " + uuid + " " +"PAYLOAD_JSON: " + jsonInputString);

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
				System.out.println("IATLID: " + uuid + " " +"RESPONSE: " + SMS_RESPONSE);
			}

			JSONParser j = new JSONParser();
			JSONObject o = (JSONObject) j.parse(SMS_RESPONSE);
			JSONObject statusInfo = (JSONObject) o.get("statusInfo");

			String statusCode = (String) statusInfo.get("statusCode");
		    String errordescription = (String) statusInfo.get("errordescription");
		    
//			responsePayload.setStatusCode(statusCode);
//			responsePayload.setErrordescription(errordescription);
		} catch (Exception e) {
			e.printStackTrace();
//			responsePayload.setStatusCode("1020");
//			responsePayload.setErrordescription("Internal Server Error");
		}

		long tmt = System.currentTimeMillis();

		//st.setTrid(String.valueOf(apiPayload.getId()));
		st.setStatus(status);
		st.setTt(tmt - tm);

	}

}
