package com.iemr.mmu.service.patientApp.master;

public interface CommonPatientAppMasterService {
	public String getChiefComplaintsMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String getCovidMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String saveCovidScreeningData(String requestObj) throws Exception;

	public String savechiefComplaintsData(String requestObj) throws Exception;

	public Integer bookTCSlotData(String requestObj, String Authorization) throws Exception;
<<<<<<< HEAD

	public String getPatientEpisodeData(String requestObj) throws Exception;
=======
	
	public String getMaster(Integer stateID );
>>>>>>> a020b425b5abba74d880d975b14ba8fb623e986f
}
