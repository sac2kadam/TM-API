package com.iemr.mmu.service.ncdscreening;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.ncdScreening.NCDScreening;

public interface NCDScreeningService {
	
	public String getNCDScreeningDetails(Long beneficiaryRegID, Long benVisitID);

	Integer saveNCDScreeningNurseData(JsonObject jsonObject) throws Exception;

	Integer updateNurseNCDScreeningDetails(JsonObject jsonObject) throws Exception;
	
}
