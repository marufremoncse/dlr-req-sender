package com.codingsense.sender.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FlagChange {
	@Value("${flag.api.url}")
	private String flagApiRoot;

	public boolean flagChange(char flag) {

		try {
			String urlString = flagApiRoot + "/api/v2/flag/" + flag;
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			con.setConnectTimeout(10000);
			con.setReadTimeout(10000);

			InputStream inputStream = con.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuilder response = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();
			inputStream.close();

			System.out.println("Flag changes to: " + flag);
			con.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
