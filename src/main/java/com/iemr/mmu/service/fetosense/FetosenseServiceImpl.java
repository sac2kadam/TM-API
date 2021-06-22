package com.iemr.mmu.service.fetosense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.data.fetosense.FetosenseData;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.fetosense.FetosenseRepo;
import com.iemr.mmu.utils.config.ConfigProperties;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.http.HttpUtils;

@Service
public class FetosenseServiceImpl implements FetosenseService {
	private static HttpUtils httpUtils = new HttpUtils();

	@Autowired
	private FetosenseRepo fetosenseRepo;
	
	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	
	/***
	 * update the feto-sense data in the DB
	 */
	@Override
	public int updateFetosenseData(String requestObj) throws Exception {
		Fetosense fetosenseData = InputMapper.gson().fromJson(requestObj, Fetosense.class);
		
		fetosenseData.setAccelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setDecelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setMovementEntriesDB(fetosenseData.getMovementEntries().toString());
		fetosenseData.setAutoFetalMovementDB(fetosenseData.getAutoFetalMovement().toString());		
		
		//fetching data from the db
		Fetosense fetosenseFetchData = fetosenseRepo.getFetosenseDetails(Long.parseLong(fetosenseData.getMother().get("partnerMotherId")),Integer.parseInt(fetosenseData.getMother().get("partnerFetosenseID")));
		
		//setting tthe values from the DB response 
		fetosenseData.setBeneficiaryRegID(fetosenseFetchData.getBeneficiaryRegID());
		fetosenseData.setVisitCode(fetosenseFetchData.getVisitCode());
		fetosenseData.setTestTime(fetosenseFetchData.getTestTime());
		fetosenseData.setMotherLMPDate(fetosenseFetchData.getMotherLMPDate());
		fetosenseData.setMotherName(fetosenseFetchData.getMotherName());
		fetosenseData.setFetosenseTestId(fetosenseFetchData.getFetosenseTestId());
		fetosenseData.setProviderServiceMapID(fetosenseFetchData.getProviderServiceMapID());
		fetosenseData.setBenFlowID(fetosenseFetchData.getBenFlowID());
		
		fetosenseData.setResponseStatus(true);
		
		//need to write the code for changing the report path data to base 64 and save it in DB
		
		
		
		//saving the feto sense response to DB
		Fetosense fetosenseDateUpdated = fetosenseRepo.save(fetosenseData);
		
		int flagUpdate = 0;
		
		//updating lab technician flag to 3 from 2 as we got the response from feto sense
		if(fetosenseDateUpdated != null && fetosenseDateUpdated.getFetosenseID() > 0)
		flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag((short) 3, fetosenseFetchData.getBenFlowID());

		 
		if(flagUpdate > 0) {
			return 1;
		}else {
			return 0;
		}
//		return fetosenseData.toString();
	}
	
	
	@Override
	public String sendFetosenseTestDetails(Fetosense request, String auth) throws Exception {
		

		Fetosense response=null;
		
		//Saving Fetosense Data in Amrit DB
		response = fetosenseRepo.save(request);
	
		if (response != null && response.getFetosenseID() > 0) {
			
			FetosenseData fetosenseTestDetails=new FetosenseData();
			fetosenseTestDetails.setPartnerfetosenseID(response.getFetosenseID());
			fetosenseTestDetails.setBeneficiaryRegID(request.getBeneficiaryRegID());
			fetosenseTestDetails.setMotherLMPDate(request.getMotherLMPDate());
			fetosenseTestDetails.setMotherName(request.getMotherName());
			fetosenseTestDetails.setTestName(request.getTestName());

			
			
			JsonParser parser = new JsonParser();
			String result=null;

			HashMap<String, Object> header = new HashMap<>();
			if (auth != null)
			{
				header.put("Authorization", auth);
			}
			
			//Invoking Fetosense API - Sending mother data and test details to fetosense
			result = httpUtils.post(ConfigProperties.getPropertyByName("fetosense-api-url-ANCTestDetails"), fetosenseTestDetails.toString(), header);

			JsonObject responseObj = (JsonObject) parser.parse(result);
			JsonObject data1 = (JsonObject) responseObj.get("data");
			int statusCode = responseObj.get("statusCode").getAsInt();
			String responseData = data1.get("response").getAsString();
			
			
			if((statusCode == 200) && (responseData!=null))
			{
				
				
					
				return responseData;
				
				
				
			}
			else
				return "Error in sending Mother Data";

			
		}
			
	   else
			return "Unable to save";
    }
		
}
