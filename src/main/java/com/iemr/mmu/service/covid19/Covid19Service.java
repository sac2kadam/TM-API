package com.iemr.mmu.service.covid19;

import com.google.gson.JsonObject;

public interface Covid19Service {
	public Long saveCovid19NurseData(JsonObject requestOBJ, String Authorization) throws Exception;
}
