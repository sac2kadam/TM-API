package com.iemr.mmu.service.ncdscreening;

import com.google.gson.JsonObject;
import com.iemr.mmu.data.ncdScreening.NCDScreening;

public interface NCDScreeningService {
	
	public String getNCDScreeningDetails(Long beneficiaryRegID, Long benVisitID);

    Long saveNCDScreeningNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

	Integer updateNurseNCDScreeningDetails(JsonObject jsonObject) throws Exception;
	
	public Integer UpdateNCDScreeningHistory(JsonObject requestOBJ) throws Exception;
	
	public Long UpdateIDRSScreen(JsonObject requestOBJ) throws Exception;
}
