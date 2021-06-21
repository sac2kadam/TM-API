package com.iemr.mmu.service.fetosense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets.SetView;
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
	
	/***
	 * update the feto-sense data in the DB
	 */
	@Override
	public int updateFetosenseData(String requestObj) throws Exception {
		Fetosense fetosenseData = InputMapper.gson().fromJson(requestObj, Fetosense.class);
		
		fetosenseData.setAccelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setDecelerationsListDB(fetosenseData.getAccelerationsList().toString());
		fetosenseData.setMovementEntriesDB(fetosenseData.getMovementEntries().toString());
		fetosenseData.setAutoFetalMovementDB(fetosenseData.getAutoFetalMovement().toString());		
		
		//fetching data from the db
		Fetosense fetosenseFetchData = fetosenseRepo.getFetosenseDetails(Long.parseLong(fetosenseData.getMother().get("partnerMotherId")),Integer.parseInt(fetosenseData.getMother().get("partnerFetosenseID")));
		
		//setting tthe values from the DB response 
		fetosenseData.setBeneficiaryRegID(fetosenseFetchData.getBeneficiaryRegID());
		fetosenseData.setVisitCode(fetosenseFetchData.getVisitCode());
		fetosenseData.setTestTime(fetosenseFetchData.getTestTime());
		fetosenseData.setMotherLMPDate(fetosenseFetchData.getMotherLMPDate());
		fetosenseData.setMotherName(fetosenseFetchData.getMotherName());
		fetosenseData.setFetosenseTestId(fetosenseFetchData.getFetosenseTestId());
//		fetosenseData.setprovider
		fetosenseData.setBenFlowID(fetosenseFetchData.getBenFlowID());
		
		fetosenseData.setResponseStatus(true);
		
		//need to write the code for changing the report path data to base 64 and save it in DB
		
		
		
		//saving the feto sense response to DB
		Fetosense fetosenseDateUpdated = fetosenseRepo.save(fetosenseData);
		
		int flagUpdate = 0;
		
		//updating lab technician flag to 3 from 2 as we got the response from feto sense
		if(fetosenseDateUpdated != null && fetosenseDateUpdated.getFetosenseID() > 0)
		flagUpdate = beneficiaryFlowStatusRepo.updateLabTechnicianFlag((short) 3, fetosenseFetchData.getBenFlowID());

		 
		if(flagUpdate > 0) {
			return 1;
		}else {
			return 0;
		}
//		return fetosenseData.toString();
	}
}
