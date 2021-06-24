package com.iemr.mmu.service.fetosense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.data.fetosense.FetosenseData;
import com.iemr.mmu.data.fetosense.FetosenseTestMaster;
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
	 * @author DU20091017
	 * update the feto-sense data in the DB
	 */
	@Override
	public int updateFetosenseData(String requestObj) throws Exception {
		Fetosense fetosenseData = InputMapper.gson().fromJson(requestObj, Fetosense.class);

		fetosenseData.setAccelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setDecelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setMovementEntriesDB(fetosenseData.getMovementEntries().toString());
		fetosenseData.setAutoFetalMovementDB(fetosenseData.getAutoFetalMovement().toString());
		fetosenseData.setFetosenseMotherID(fetosenseData.getMother().get("cmMotherId"));
		fetosenseData.setFetosensePartnerID(fetosenseData.getMother().get("partnerId"));
		fetosenseData.setPartnerName(fetosenseData.getMother().get("partnerName"));
		fetosenseData.setFetosenseID(Long.parseLong(fetosenseData.getMother().get("partnerFetosenseID")));

		Long beneficiarRegID = Long.parseLong(fetosenseData.getMother().get("partnerMotherId"));
		// fetching data from the db
		Fetosense fetosenseFetchData = fetosenseRepo.getFetosenseDetails(beneficiarRegID,
				fetosenseData.getFetosenseID());

		// setting tthe values from the DB response
		fetosenseData.setBeneficiaryRegID(fetosenseFetchData.getBeneficiaryRegID());
		fetosenseData.setVisitCode(fetosenseFetchData.getVisitCode());
		fetosenseData.setTestTime(fetosenseFetchData.getTestTime());
		fetosenseData.setMotherLMPDate(fetosenseFetchData.getMotherLMPDate());
		fetosenseData.setMotherName(fetosenseFetchData.getMotherName());
		fetosenseData.setFetosenseTestId(fetosenseFetchData.getFetosenseTestId());
		fetosenseData.setProviderServiceMapID(fetosenseFetchData.getProviderServiceMapID());
		fetosenseData.setBenFlowID(fetosenseFetchData.getBenFlowID());
		fetosenseData.setCreatedBy(fetosenseFetchData.getCreatedBy());

		fetosenseData.setResultState(true);

		// need to write the code for changing the report path data to base 64 and save
		// it in DB

		// saving the feto sense response to DB
		Fetosense fetosenseDateUpdated = fetosenseRepo.save(fetosenseData);

		int flagUpdate = 0;

		// updating lab technician flag to 3 from 2 as we got the response from feto
		// sense
		if (fetosenseDateUpdated != null && fetosenseDateUpdated.getFetosenseID() > 0) {

			// need to check how many records are there with benflowID
			short lab_technician_flag = 3;
			ArrayList<Fetosense> fetosenseDataOnBenFlowID = fetosenseRepo
					.getFetosenseDetailsByFlowId(fetosenseFetchData.getBenFlowID());
			if (fetosenseDataOnBenFlowID.size() > 0) {
				// if any of the record is not updated then marking lab flag as 2 - not able to
				// open in doctor screen.
				for (Fetosense data : fetosenseDataOnBenFlowID) {
					if (data != null && !data.getResultState()) {
						lab_technician_flag = 2;
					}
				}
			}
			flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag(lab_technician_flag,
					fetosenseFetchData.getBenFlowID());
		}

		if (flagUpdate > 0) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String sendFetosenseTestDetails(Fetosense request, String auth) throws Exception {

		Fetosense response = null;

		// Saving Fetosense Data in Amrit DB
		response = fetosenseRepo.save(request);

		if (response != null && response.getFetosenseID() > 0) {

			FetosenseData fetosenseTestDetails = new FetosenseData();
			fetosenseTestDetails.setPartnerFetosenseID(response.getFetosenseID());
			fetosenseTestDetails.setBeneficiaryRegID(request.getBeneficiaryRegID());
			fetosenseTestDetails.setMotherLMPDate(request.getMotherLMPDate());
			fetosenseTestDetails.setMotherName(request.getMotherName());
			fetosenseTestDetails.setTestName(request.getTestName());

			JsonParser parser = new JsonParser();
			String result = null;

			HashMap<String, Object> header = new HashMap<>();
			if (auth != null) {
				header.put("Authorization", auth);
			}

			// Invoking Fetosense API - Sending mother data and test details to fetosense
			result = httpUtils.post(ConfigProperties.getPropertyByName("fetosense-api-url-ANCTestDetails"),
					fetosenseTestDetails.toString(), header);

			JsonObject responseObj = (JsonObject) parser.parse(result);
			JsonObject data1 = (JsonObject) responseObj.get("data");
			int statusCode = responseObj.get("statusCode").getAsInt();
			String responseData = data1.get("response").getAsString();

			if ((statusCode == 200) && (responseData != null)) {

				return responseData;

			} else
				return "Error in sending Mother Data";

		}

		else
			return "Unable to save";
	}

	@Override
	public String getFetosenseDetails(Long benFlowID) throws Exception {

		Map<String, Object> resMap = new HashMap<>();
		ArrayList<Fetosense> fetosenseData = fetosenseRepo.getFetosenseDetailsByFlowId(benFlowID);

		List<Fetosense> fetosenseList = new ArrayList<Fetosense>();
		for (Fetosense data : fetosenseData) {
			Fetosense listData = new Fetosense(data.getFetosenseID(), data.getBeneficiaryRegID(), data.getBenFlowID(),
					data.getVisitCode(), data.getFetosenseTestId(), data.getResultState());
			fetosenseList.add(listData);
		}
		resMap.put("benFetosenseData", fetosenseList);
		return new Gson().toJson(resMap);
	}

}
