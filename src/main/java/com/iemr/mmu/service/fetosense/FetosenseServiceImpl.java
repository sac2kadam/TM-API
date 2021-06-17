package com.iemr.mmu.service.fetosense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.fetosense.FetosenseRepo;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class FetosenseServiceImpl implements FetosenseService {

	@Autowired
	private FetosenseRepo fetosenseRepo;
	
	@Autowired
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;
	
	@Override
	public int updateFetosenseData(String requestObj) throws Exception {
		Fetosense fetosenseData = InputMapper.gson().fromJson(requestObj, Fetosense.class);
		
		fetosenseData.setAccelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setDecelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setMovementEntriesDB(fetosenseData.getMovementEntries().toString());
		fetosenseData.setAutoFetalMovementDB(fetosenseData.getAutoFetalMovement().toString());
		
		int updated = fetosenseRepo.updateFetosenseDetails(fetosenseData.getMother().get("partnerName"), fetosenseData.getMother().get("partnerId"),
				fetosenseData.getMother().get("cmMotherId"), fetosenseData.getTestId(), fetosenseData.getDeviceId(), fetosenseData.getTestDoneAt(),
				fetosenseData.getLengthOfTest(), fetosenseData.getBasalHeartRate(), fetosenseData.getAccelerationsListDB(),
				fetosenseData.getAccelerationsListDB(), fetosenseData.getShortTermVariationBpm(), fetosenseData.getShortTermVariationMilli(),
				fetosenseData.getLongTermVariation(), fetosenseData.getMovementEntriesDB(), fetosenseData.getAutoFetalMovementDB(),
				fetosenseData.getReportPath(),Long.parseLong(fetosenseData.getMother().get("partnerMotherId")),Integer.parseInt(fetosenseData.getMother().get("partnerFetosenseID")));
		
		
		Fetosense fetosenseFetchData = fetosenseRepo.getFetosenseDetails(Long.parseLong(fetosenseData.getMother().get("partnerMotherId")),Integer.parseInt(fetosenseData.getMother().get("partnerFetosenseID")));
		int flagUpdate = 0;
		if(updated > 0)
		flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag((short) 11, fetosenseFetchData.getVisitCode(), fetosenseFetchData.getBeneficiaryRegID());
//		Fetosense fetosenseDataSaved = new Fetosense();
//		
//		if(fetosenseDataFetch != null && fetosenseDataFetch.getFetosenseID() > 0) {
//			fetosenseDataSaved = fetosenseRepo.save(fetosenseData);
//		}
		 
		if(flagUpdate > 0) {
			return 1;
		}else {
			return 0;
		}
//		return fetosenseData.toString();
	}
}
