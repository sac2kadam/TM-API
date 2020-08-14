package com.iemr.mmu.controller.patientApp.master;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.controller.common.master.CommonMasterController;
import com.iemr.mmu.service.patientApp.master.CommonPatientAppMasterService;
import com.iemr.mmu.utils.response.OutputResponse;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping(value = "/master", headers = "Authorization")

public class PatientAppCommonMasterController {

	private Logger logger = LoggerFactory.getLogger(CommonMasterController.class);
	@Autowired
	private CommonPatientAppMasterService commonPatientAppMasterService;

	/**
	 * 
	 * @param visitCategoryID
	 * @return nurse master data for the provided visitCategoryID
	 * 
	 */
	@ApiOperation(value = "Master Data API for PatientApp", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/patientApp/chiefComplaintsMaster/{visitCategoryID}/{providerServiceMapID}/{gender}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppChiefComplaintsMasterData(@PathVariable("visitCategoryID") Integer visitCategoryID,
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("gender") String gender) {
		logger.info("Nurse master Data for categoryID:" + visitCategoryID + " and providerServiceMapID:"
				+ providerServiceMapID);
		;
		OutputResponse response = new OutputResponse();
		response.setResponse(
				commonPatientAppMasterService.getChiefComplaintsMaster(visitCategoryID, providerServiceMapID, gender));
		logger.info("Nurse master Data for categoryID:" + response.toString());
		return response.toString();
	}

	@ApiOperation(value = "Master Data API for PatientApp", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = "/patientApp/covidMaster/{visitCategoryID}/{providerServiceMapID}/{gender}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppCovidMasterData(@PathVariable("visitCategoryID") Integer visitCategoryID,
			@PathVariable("providerServiceMapID") Integer providerServiceMapID, @PathVariable("gender") String gender) {
		logger.info("Nurse master Data for categoryID:" + visitCategoryID + " and providerServiceMapID:"
				+ providerServiceMapID);
		;
		OutputResponse response = new OutputResponse();
		response.setResponse(
				commonPatientAppMasterService.getCovidMaster(visitCategoryID, providerServiceMapID, gender));
		logger.info("Nurse master Data for categoryID:" + response.toString());
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save Covid  data.. patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/covidScreeningDataPatientApp" }, method = { RequestMethod.POST })
	public String saveBenCovidDoctorDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for Covid data saving - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.saveCovidScreeningData(requestObj);

			response.setResponse(s);

		} catch (Exception e) {
			logger.error("Error while saving Covid data - patient APP :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save chief-Complaints  data.. patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/chiefComplaintsDataPatientApp" }, method = { RequestMethod.POST })
	public String saveBenChiefComplaintsDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for chief-Complaints data saving - patient APP :" + requestObj);

			String s = commonPatientAppMasterService.savechiefComplaintsData(requestObj);

			response.setResponse(s);

		} catch (Exception e) {
			logger.error("Error while saving chief-Complaints data - patient APP :" + e);
			response.setError(5000, "Unable to save data. " + e.getMessage());
		}
		return response.toString();
	}

	@CrossOrigin
	@ApiOperation(value = "Save tele-consultation slot data.. patient app", consumes = "application/json", produces = "application/json")
	@RequestMapping(value = { "/save/tcSlotDetailsDataPatientApp" }, method = { RequestMethod.POST })
	public String saveTCSlotDataPatientApp(@RequestBody String requestObj,
			@RequestHeader(value = "Authorization") String Authorization) {
		OutputResponse response = new OutputResponse();
		try {
			logger.info("Request object for TC slot data saving - patient APP :" + requestObj);

			Integer i = commonPatientAppMasterService.bookTCSlotData(requestObj, Authorization);
			if (i > 0)
				response.setResponse("Teleconsultation slot booked successfully");
			else
				response.setError(5000, "Error in slot booking");

		} catch (Exception e) {
			logger.error("Error while booking TC slot - patient APP :" + e);
			response.setError(5000, "error in . " + e.getMessage());
		}
		return response.toString();
	}
	
	@ApiOperation(value = "Master Data  for Patient", consumes = "application/json", produces = "application/json")
	@RequestMapping(value ={"/patientApp/details/{stateID}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public String patientAppMasterData(@PathVariable("stateID") Integer stateID) {
		logger.info("master Data for beneficiary:" );
		
		OutputResponse response = new OutputResponse();
		response.setResponse(
				commonPatientAppMasterService.getMaster(stateID));
		logger.info("Nurse master Data for beneficiary:" + response.toString());
		return response.toString();
	}

}
