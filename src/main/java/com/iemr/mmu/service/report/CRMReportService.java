package com.iemr.mmu.service.report;

import java.util.List;
import java.util.Set;

import com.iemr.mmu.data.report.ConsultationReport;
import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.utils.exception.TMException;

public interface CRMReportService {

	Set<SpokeReport> getChiefcomplaintreport(ReportInput input)throws TMException;

	List<ConsultationReport> getConsultationReport(ReportInput input) throws TMException;

	
	
	

}
