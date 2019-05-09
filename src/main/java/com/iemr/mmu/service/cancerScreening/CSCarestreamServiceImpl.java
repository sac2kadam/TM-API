package com.iemr.mmu.service.cancerScreening;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Service
@PropertySource("classpath:application.properties")
public class CSCarestreamServiceImpl implements CSCarestreamService {

	@Value("${carestreamOrderCreateURL}")
	private String carestreamOrderCreateURL;

	@Override
	public int createMamographyRequest(ArrayList<Object[]> benDataForCareStream, long benRegID, long benVisitID,
			String Authorization) {
		int responseData = 0;
		RestTemplate restTemplate = new RestTemplate();
		try {
			// HttpHeaders headers = new HttpHeaders();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Content-Type", "application/json");
			headers.add("AUTHORIZATION", Authorization);
			String requestOBJ = getOrderCreationRequestOBJ(benDataForCareStream, benRegID, benVisitID);

			HttpEntity<Object> request = new HttpEntity<Object>(requestOBJ, headers);
			// System.out.println("hello");
			ResponseEntity<String> response = restTemplate.exchange(carestreamOrderCreateURL, HttpMethod.POST, request,
					String.class);
			if (response != null) {
				JSONObject obj = new JSONObject(response.getBody());
				if (obj.getInt("statusCode") == 200) {
					responseData = 1;
				}
			}
			// System.out.println("hello");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
	}

	private String getOrderCreationRequestOBJ(ArrayList<Object[]> benDataForCareStream, long benRegID, long benVisitID)
			throws Exception {
		Map<String, Object> requestOBJ = new HashMap<>();
		if (benDataForCareStream != null && benDataForCareStream.size() > 0) {
			Object[] objArr = benDataForCareStream.get(0);

			Date dob = (Date) objArr[2];
			String dobStr = dob.toString();
			dobStr = dobStr.replaceAll("-", "");
			short genderID = (short) objArr[3];
			String gender = "";

			if (genderID == 1)
				gender = "M";
			else if (genderID == 2)
				gender = "F";
			else
				gender = "T";

			requestOBJ.put("firstName", objArr[0]);
			requestOBJ.put("LastName", objArr[1]);
			requestOBJ.put("patientID", benRegID);
			requestOBJ.put("acc", benVisitID);
			requestOBJ.put("gender", gender);
			requestOBJ.put("dob", dobStr);
			// System.out.println("hellooo.......");
		}
		return new Gson().toJson(requestOBJ);
	}
}
