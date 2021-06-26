package com.iemr.mmu.controller.fetosense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.service.fetosense.FetosenseService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping(value = "/fetosense")
public class FetosenseFetchController {

	@Autowired
	private FetosenseService fetosenseService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@CrossOrigin
	@ApiOperation(value = "Provides the fetosense details")
	@RequestMapping(value = "/fetch/fetosenseDetails/{benFlowID}", method = RequestMethod.GET, headers = "Authorization")
	public String getFetosenseDetails(@ApiParam("{\"benFlowID\":\"Long\"}") @PathVariable("benFlowID") Long benFlowID) throws Exception {
	
		logger.info("Request Object for getting fetosense data - " + benFlowID);
		OutputResponse output = new OutputResponse();
		try {
			String response=fetosenseService.getFetosenseDetails(benFlowID);
			if(response != null) 
				output.setResponse(response);
			else 				
				output.setError(5000, "Error in fetching the details");
		} catch (Exception e) {
			logger.error("sendANCMotherTestDetailsToFetosense failed with error " + e.getMessage(), e);
			output.setError(e);
		}
		
		return output.toString();
	}
}
