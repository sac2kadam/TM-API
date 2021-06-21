package com.iemr.mmu.service.fetosense;

import com.iemr.mmu.data.fetosense.Fetosense;

public interface FetosenseService {

	int updateFetosenseData(String requestObj) throws Exception;
	String sendFetosenseTestDetails(Fetosense request, String auth) throws Exception;
}
