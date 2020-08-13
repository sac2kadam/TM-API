package com.iemr.mmu.service.patientApp.master;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.mmu.service.patientApp.master.CommonPatientAppMasterService;
@Service
public class CommonPatientAppMasterServiceImpl implements CommonPatientAppMasterService {
	
	@Autowired
	private CovidSymptomsMasterRepo covidSymptomsMasterRepo;
	@Autowired
	private CovidContactHistoryMasterRepo covidContactHistoryMasterRepo;
	@Autowired
	private CovidRecommnedationMasterRepo covidRecommnedationMasterRepo;
	@Autowired
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	@Override
	public String getChiefComplaintsMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		ArrayList<Object[]> ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();
		resMap.put("chiefComplaintMaster", ChiefComplaintMaster.getChiefComplaintMasters(ccList));
		return new Gson().toJson(resMap);
	}
	@Override
	public String getCovidMaster(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("covidSymptomsMaster", covidSymptomsMasterRepo.findByDeleted(false));
		resMap.put("covidContactHistoryMaster", covidContactHistoryMasterRepo.findByDeleted(false));
		resMap.put("covidRecommendationMaster", covidRecommnedationMasterRepo.findByDeleted(false));
		return new Gson().toJson(resMap);
	}
}
