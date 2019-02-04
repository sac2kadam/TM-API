package com.iemr.mmu.controller.ncdscreening;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @Objective Updating NCD Screening nurse data.
 * @Date 24-01-2018
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value =  "/NCD", headers = "Authorization")
public class NCDUpdateController {
private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	private NCDScreeningServiceImpl ncdScreeningServiceImpl;
	
	@Autowired
	public void setNcdScreeningServiceImpl(NCDScreeningServiceImpl ncdScreeningServiceImpl) {
		this.ncdScreeningServiceImpl = ncdScreeningServiceImpl;
	}
	
	/**
	 * 
	 * @param requestObj
	 * @return success or failure response
	 * @objective Replace NCD Screening Data entered by Nurse
	 * 
	 * NOT using as of now
	 */
	@CrossOrigin
	@ApiOperation(value = "update Beneficiary NCD Screening Detail", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/update/nurseData" }, method = { RequestMethod.POST })
	public String updateBeneficiaryNCDScreeningDetails(@RequestBody String requestObj) {

		logger.info("Update NCDScreening Details request:" + requestObj);
		OutputResponse response = new OutputResponse();
		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser = new JsonParser();

		try {
			JsonElement jsonElement = jsonParser.parse(requestObj);
			jsonObject = jsonElement.getAsJsonObject();

			if (jsonObject != null) {
				Integer r = ncdScreeningServiceImpl.updateNurseNCDScreeningDetails(jsonObject);
				if (r != null && r == 1) {
					response.setResponse("Data updated successfully");
				} else {
					response.setError(5000, "Unable to modify data");
				}
			} else {
				response.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			response.setError(5000, "Unable to modify data");
			logger.error("Error in updating beneficiary NCD screening data: " + e);
		}
		logger.info("Update NCDScreening Details response:" + response);
		return response.toString();
	}
	
}
