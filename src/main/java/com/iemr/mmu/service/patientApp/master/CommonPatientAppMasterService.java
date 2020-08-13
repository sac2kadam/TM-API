package com.iemr.mmu.service.patientApp.master;

public interface CommonPatientAppMasterService {
	public String getChiefComplaintsMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String getCovidMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String saveCovidScreeningData(String requestObj) throws Exception;
}
