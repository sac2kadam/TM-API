package com.iemr.mmu.service.fetosense;

import com.iemr.mmu.data.fetosense.Fetosense;
import com.iemr.mmu.utils.exception.IEMRException;

public interface FetosenseService {

	int updateFetosenseData(Fetosense fetosenseData) throws IEMRException;
	String sendFetosenseTestDetails(Fetosense request, String auth) throws IEMRException;
	String getFetosenseDetails(Long benFlowID) throws IEMRException;
}
