package com.iemr.mmu.controller.report;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.service.report.CRMReportService;
import com.iemr.mmu.utils.response.OutputResponse;

@RequestMapping("/TMReport")
@RestController
public class CRMReportController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private CRMReportService cRMReportService;
	
	@CrossOrigin()
	@RequestMapping(value = "/chiefcomplaintreport", headers = "Authorization", method = { RequestMethod.POST }, produces = {
			"application/json" })
	public String chiefcomplaintreport(@RequestBody ReportInput input) {

		OutputResponse response = new OutputResponse();

		try {

			Set<SpokeReport> report= cRMReportService.getChiefcomplaintreport(input);
			
			response.setResponse(report.toString());
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setError(e);

		}
		/**
		 * sending the response...
		 */
		return response.toString();

	}
	
	
	
	
	

}
