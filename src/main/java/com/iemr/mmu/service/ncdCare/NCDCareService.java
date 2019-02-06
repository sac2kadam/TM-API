package com.iemr.mmu.service.ncdCare;

import com.google.gson.JsonObject;

public interface NCDCareService {

	Long saveNCDCareNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

}
