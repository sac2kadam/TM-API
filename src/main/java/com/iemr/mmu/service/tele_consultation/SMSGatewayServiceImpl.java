package com.iemr.mmu.service.tele_consultation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.quickConsultation.PrescribedDrugDetail;
import com.iemr.mmu.data.tele_consultation.SmsRequestOBJ;
import com.iemr.mmu.repo.tc_consultation.TCRequestModelRepo;

@Service
@PropertySource("classpath:application.properties")
public class SMSGatewayServiceImpl implements SMSGatewayService {
	@Value("${sendSMSUrl}")
	private String sendSMSUrl;
	@Value("${schedule}")
	private String schedule;
	@Value("${prescription}")
	private String prescription;
	@Value("${cancel}")
	private String cancel;
	@Value("${reSchedule}")
	private String reSchedule;

	@Autowired
	private TCRequestModelRepo tCRequestModelRepo;
	@Autowired
	RestTemplate restTemplate;

	@Override
	public int smsSenderGateway(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate, String Authorization) {

		int returnOBJ = 0;
		String requestOBJ = createSMSRequest(smsType, benRegID, specializationID, tMRequestID, tMRequestCancelID,
				createdBy, tcDate, tcPreviousDate);

		if (requestOBJ != null) {
			String smsStatus = sendSMS(requestOBJ, Authorization);
			if (smsStatus != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(smsStatus);
				jsnOBJ = jsnElmnt.getAsJsonObject();
				if (jsnOBJ != null && jsnOBJ.get("statusCode").getAsInt() == 200)
					returnOBJ = 1;
			}
			// System.out.println("hello");
		}

		return returnOBJ;

	}
@Override
	public int smsSenderGateway2(String smsType, List<PrescribedDrugDetail> object, String Authorization,Long benregID,String createdBy
			,List<Object> diagnosis) {

		int returnOBJ = 0;
//		String requestOBJ = createSMSRequest(smsType, benRegID, specializationID, tMRequestID, tMRequestCancelID,
//				createdBy, tcDate, tcPreviousDate);
		int smsTypeID=0;SmsRequestOBJ obj;
		ArrayList<SmsRequestOBJ> objList = new ArrayList<>();
		if (smsType.equalsIgnoreCase("prescription")) {
			 smsTypeID = tCRequestModelRepo.getSMSTypeID(prescription);
		}
		if (smsTypeID != 0) {
			obj = new SmsRequestOBJ();
			obj.setSmsTemplateID(tCRequestModelRepo.getSMSTemplateID(smsTypeID));
			obj.setObj(object);
			obj.setDiagnosis(diagnosis);
			obj.setBeneficiaryRegID(benregID);
			obj.setCreatedBy(createdBy);
			objList.add(obj);
		}

		String requestOBJ=new  Gson().toJson(objList);
		if (requestOBJ != null) {
			String smsStatus = sendSMS(requestOBJ, Authorization);
			if (smsStatus != null) {
				JsonObject jsnOBJ = new JsonObject();
				JsonParser jsnParser = new JsonParser();
				JsonElement jsnElmnt = jsnParser.parse(smsStatus);
				jsnOBJ = jsnElmnt.getAsJsonObject();
				if (jsnOBJ != null && jsnOBJ.get("statusCode").getAsInt() == 200)
					returnOBJ = 1;
			}
			// System.out.println("hello");
		}

		return returnOBJ;

	}

	@Override
	public String createSMSRequest(String smsType, Long benRegID, Integer specializationID, Long tMRequestID,
			Long tMRequestCancelID, String createdBy, String tcDate, String tcPreviousDate) {

		SmsRequestOBJ obj;
		ArrayList<SmsRequestOBJ> objList = new ArrayList<>();

		int smsTypeID;

		switch (smsType) {
		case "schedule":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(schedule);
			break;
		case "cancel":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(cancel);
			break;
		case "reSchedule":
			smsTypeID = tCRequestModelRepo.getSMSTypeID(reSchedule);
			break;
		default:
			smsTypeID = 0;
		}

		if (smsTypeID != 0) {
			obj = new SmsRequestOBJ();

			obj.setSmsTemplateID(tCRequestModelRepo.getSMSTemplateID(smsTypeID));
			obj.setBeneficiaryRegID(benRegID);
			obj.setSpecializationID(specializationID);
			obj.setSmsType(smsType);
			obj.setCreatedBy(createdBy);
			obj.setTcDate(tcDate);
			obj.setTcPreviousDate(tcPreviousDate);

			objList.add(obj);
		}

		return new Gson().toJson(objList);
	}

	@Override
	public String sendSMS(String request, String Authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("AUTHORIZATION", Authorization);

		HttpEntity<Object> requestOBJ = new HttpEntity<Object>(request, headers);

		return restTemplate.exchange(sendSMSUrl, HttpMethod.POST, requestOBJ, String.class).getBody();
	}
}
