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

import com.iemr.mmu.service.fetosense.FetosenseService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author DU20091017
 *
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense", headers = "Authorization")
public class FetosenseUpdateController {

	@Autowired
	private FetosenseService fetosenseService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@CrossOrigin
	@ApiOperation(value = "update ANC Doctor Data", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/update/fetosenseData", method = {RequestMethod.POST})
	public String updateFetosenseData(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization ) throws Exception {
		OutputResponse response = new OutputResponse();
		logger.info("Request object for ANC doctor data updating :" + requestObj);
		
//		System.out.println(requestObj);
//		JsonObject jsnOBJ = new JsonObject();
//		JsonParser jsnParser = new JsonParser();
//		JsonElement jsnElmnt = jsnParser.parse(requestObj);
//		jsnOBJ = jsnElmnt.getAsJsonObject();
		try {
			int responseValue = fetosenseService.updateFetosenseData(requestObj);
			if(responseValue == 1)
				response.setResponse("Data updated successfully");
			else
				response.setError(500, "Unable to modify data");
			logger.info("Error while updating fetosense data :" + response);
		}catch(Exception e) {
			response.setError(5000, "Unable to modify data. " + e.getMessage());
			logger.error("Error while updating fetosense data :" + e);
		}
		return response.toString();
	}
}
