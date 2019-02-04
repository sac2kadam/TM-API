package com.iemr.mmu.controller.snomedct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.iemr.mmu.data.snomedct.SCTDescription;
import com.iemr.mmu.service.snomedct.SnomedService;
import com.iemr.mmu.utils.mapper.InputMapper;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping(value = "/snomed")
@RestController
public class SnomedController {
	private Logger logger = LoggerFactory.getLogger(SnomedController.class);

	private SnomedService snomedService;

	@Autowired
	public void setSnomedService(SnomedService snomedService) {
		this.snomedService = snomedService;
	}

	@CrossOrigin
	@ApiOperation(value = "retrives SnomedCT Record (Entire term case insensitive)", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/getSnomedCTRecord", method = RequestMethod.POST, headers = "Authorization")
	public String getSnomedCTRecord(@ApiParam(value = "{\"term\":\"String\"}") @RequestBody String request) {
		OutputResponse output = new OutputResponse();
		try {

			SCTDescription sctdescription = InputMapper.gson().fromJson(request, SCTDescription.class);

			logger.info("getSnomedCTRecord request " + sctdescription.toString());

			SCTDescription sctdescriptions = snomedService.findSnomedCTRecordFromTerm(sctdescription.getTerm());

			if (sctdescriptions == null || sctdescriptions.getConceptID() == null)
				output.setResponse("No Records Found");
			else
				output.setResponse(new Gson().toJson(sctdescriptions));

			logger.info("ggetSnomedCTRecord response: " + output);
		} catch (Exception e) {
			logger.error("ggetSnomedCTRecord failed with error " + e.getMessage(), e);
			output.setError(e);
		}
		return output.toString();
	}

	// @CrossOrigin
	// @ApiOperation(value = "retrives SnomedCT Records", consumes =
	// "application/json", produces = "application/json")
	// @RequestMapping(value = "/getSnomedCTRecords", method = RequestMethod.POST,
	// headers = "Authorization")
	// public String getSnomedCTRecords(@ApiParam(value = "{\"term\":\"String\"}")
	// @RequestBody String request) {
	// OutputResponse output = new OutputResponse();
	// try {
	//
	// SCTDescription sctdescription = InputMapper.gson().fromJson(request,
	// SCTDescription.class);
	//
	// logger.info("getSnomedCTRecord request " + sctdescription.toString());
	//
	// List<SCTDescription> sctdescriptions =
	// snomedService.findSnomedCTRecordFromTerm(sctdescription.getTerm());
	//
	// output.setResponse(sctdescriptions.toString());
	// logger.info("ggetSnomedCTRecord response: " + output);
	// } catch (Exception e) {
	// logger.error("ggetSnomedCTRecord failed with error " + e.getMessage(), e);
	// output.setError(e);
	// }
	// return output.toString();
	// }

}
