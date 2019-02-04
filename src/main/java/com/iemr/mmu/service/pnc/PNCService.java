package com.iemr.mmu.service.pnc;

import com.google.gson.JsonObject;

public interface PNCService
{

	Long savePNCNurseData(JsonObject requestOBJ) throws Exception;

	String getBenVisitDetailsFrmNursePNC(Long benRegID, Long benVisitID);

	String getBenPNCDetailsFrmNursePNC(Long benRegID, Long benVisitID);


}
