package com.iemr.mmu.service.common.transaction;

import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;

public interface CommonNurseService {
	public Long saveBeneficiaryVisitDetails(BeneficiaryVisitDetail beneficiaryVisitDetail);

	public String getBenSymptomaticData(Long benRegID) throws Exception;

	public String getBenPreviousDiabetesData(Long benRegID) throws Exception;
}
