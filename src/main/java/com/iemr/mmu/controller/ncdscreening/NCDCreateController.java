package com.iemr.mmu.controller.ncdscreening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iemr.mmu.service.ncdscreening.NCDScreeningServiceImpl;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author NA874500
 * @Objective Saving NCD Screening nurse data.
 * @Date 24-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/NCD", headers = "Authorization")
public class NCDCreateController {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDScreeningServiceImpl ncdScreeningServiceImpl;

	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}

	/**
	 * @Objective Save NCD Screening data for nurse.
	 * @param JSON requestObj 
	 * @return success or failure response
	 */
	@CrossOrigin
	@ApiOperation(value = "save Beneficiary NCD Screening Detail", consumes = "application/json", produces = "application/json")

	@RequestMapping(value = { "/save/nurseData" }, method = { RequestMethod.POST })
	public String saveBeneficiaryNCDScreeningDetails(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {

		logger.info("Request object for NCD Screening nurse data saving :" + requestObj);
		OutputResponse response = new OutputResponse();

		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();

		try {
			JsonElement jsonElement = jsonParser.parse(requestObj);
			jsonObject = jsonElement.getAsJsonObject();

			if (jsonObject != null) {
				Long r = ncdScreeningServiceImpl.saveNCDScreeningNurseData(jsonObject,Authorization);
				if (r != null && r > 0) {
					response.setResponse("Data saved successfully");
				} else {
					response.setError(5000, "Unable to save data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to save data");
			logger.error("Error while storing NCD Screening nurse data: " + e);
		}
		return response.toString();
	}
}
