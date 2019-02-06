package com.iemr.mmu.service.anc;

import com.google.gson.JsonObject;

public interface ANCService {

	public Long saveANCNurseData(JsonObject requestOBJ, String Authorization) throws Exception;

	// int UpdateANCVisitDetails(JsonObject jsnOBJ) throws Exception;

	String getBenVisitDetailsFrmNurseANC(Long benRegID, Long visitCode);

	String getBenANCHistoryDetails(Long benRegID, Long visitCode);

	String getANCExaminationDetailsData(Long benRegID, Long visitCode);

	String getBeneficiaryVitalDetails(Long beneficiaryRegID, Long visitCode);

	String getBenANCDetailsFrmNurseANC(Long benRegID, Long visitCode);

}
