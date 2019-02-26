package com.iemr.mmu.service.report;

import java.util.Set;

import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;

public interface CRMReportService {

	Set<SpokeReport> getChiefcomplaintreport(ReportInput input);

	
	
	

}
