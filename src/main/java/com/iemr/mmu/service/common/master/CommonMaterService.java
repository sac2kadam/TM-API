package com.iemr.mmu.service.common.master;

public interface CommonMaterService {

	public String getVisitReasonAndCategories();

	public String getMasterDataForNurse(Integer visitCategoryID, Integer providerServiceMapID, String gender);

	public String getMasterDataForDoctor(Integer visitCategoryID, Integer providerServiceMapID, String gender,
			Integer facilityID, Integer vanID);

}
