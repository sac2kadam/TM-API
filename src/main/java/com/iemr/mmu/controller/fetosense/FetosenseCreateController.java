package com.iemr.mmu.controller.fetosense;


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
import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.service.fetosense.FetosenseService;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author DE40034072
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense", headers = "Authorization")
public class FetosenseCreateController {
	@Autowired
	private FetosenseService fetosenseService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	/**
	 * @Objective Transfer Mother Data and NST/CTG Test Details to Fetosense.
	 * @param JSON
	 *            requestObj
	 * @return success or failure response
	 */
	
	@CrossOrigin()
	@ApiOperation(value = "Provides the mother data and prescribed test details to fetosense")
	@RequestMapping(value = "/sendMotherTestDetailsToFetosense", method = RequestMethod.POST, headers = "Authorization")
	public String sendANCMotherTestDetailsToFetosense(@ApiParam("{\"beneficiaryRegID\":\"Long\",\"testTime\":\"Timestamp\",\"motherLMPDate\":\"Timestamp\",\"motherName\":\"String\",\"fetosenseTestId\":\"Long\",\"testName\":\"String\",\"ProviderServiceMapID\":\"Integer\",\"createdBy\":\"String\"}") @RequestBody String requestObj, @RequestHeader(value = "Authorization") String authorization) {
		logger.info("Request Object for transfering mother data to fetosense" + requestObj);
		OutputResponse output = new OutputResponse();
		
		try {

			if (requestObj != null) {
				
				Fetosense fetosenseRequest = InputMapper.gson().fromJson(requestObj, Fetosense.class);
				
				String response=fetosenseService.sendFetosenseTestDetails(fetosenseRequest, authorization);
				
				if(response=="Unable to save")
				{
			         output.setError(5000, "Error in saving Fetosense details in DB");
				}
				else if(response=="Unable to save test status")
				{

			         output.setError(5000, "Error in saving Fetosense test status in DB");
				}
				else if(response=="Error in sending Mother Data")
				{

			         output.setError(5000, "Error in sending Mother Data and Test Details to Fetosense");
				}
				else
				{
					 output.setResponse(response);
				}
			}
			else {
				
				output.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			logger.error("sendANCMotherTestDetailsToFetosense failed with error " + e.getMessage(), e);
			output.setError(e);
		}
		logger.info("sendANCMotherTestDetailsToFetosense response: " + output);
		return output.toString();
	}
	
	
	@CrossOrigin()
	@ApiOperation(value = "Register Mother")
	@RequestMapping(value = "/registerMother", method = RequestMethod.POST, headers = "Authorization")
	public String saveMother(@RequestBody String requestObj, @RequestHeader(value = "Authorization") String authorization) {
	
		OutputResponse output = new OutputResponse();
		
		try {
			if (requestObj != null) {
				
				
					 output.setResponse("Test is WIP");
		
			}
			else {
				
				output.setError(5000, "Invalid request");
			}
		} catch (Exception e) {
			
			output.setError(e);
		}
		
		return output.toString();
	}
}
