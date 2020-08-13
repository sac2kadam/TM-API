package com.iemr.mmu.service.patientApp.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.iemr.mmu.data.covid19.Covid19BenFeedback;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidContactHistoryMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidRecommnedationMasterRepo;
import com.iemr.mmu.repo.masterrepo.covid19.CovidSymptomsMasterRepo;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.service.covid19.Covid19ServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;

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
	@Autowired
	private CommonNurseServiceImpl commonNurseServiceImpl;
	@Autowired
	private Covid19ServiceImpl covid19ServiceImpl;

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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String saveCovidScreeningData(String requestObj) throws Exception {
		Map<String, Long> responseOBJ = new HashMap<>();

		JSONObject jsonOBJ = new JSONObject(requestObj);

		CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(requestObj, CommonUtilityClass.class);

		BeneficiaryVisitDetail benDetailsOBJ = InputMapper.gson().fromJson(requestObj, BeneficiaryVisitDetail.class);
		benDetailsOBJ.setVisitReason("New Chief Complaint");
		benDetailsOBJ.setVisitCategory("COVID-19 Screening");

		if (benDetailsOBJ != null && benDetailsOBJ.getVanID() != null && benDetailsOBJ.getBeneficiaryRegID() != null
				&& benDetailsOBJ.getProviderServiceMapID() != null) {
			Long visitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(benDetailsOBJ);
			Long visitCode = null;
			if (visitID != null) {
				visitCode = commonNurseServiceImpl.generateVisitCode(visitID, nurseUtilityClass.getVanID(), 3);
				if (visitCode != null) {
					Covid19BenFeedback covid19BenFeedbackOBJ = InputMapper.gson()
							.fromJson(jsonOBJ.getJSONObject("covidDetails").toString(), Covid19BenFeedback.class);

					if (covid19BenFeedbackOBJ != null) {
						covid19BenFeedbackOBJ.setVisitCode(visitCode);
						Integer i = covid19ServiceImpl.saveCovidDetails(covid19BenFeedbackOBJ);
					} else
						throw new RuntimeException("error in saving covid screening details");

					responseOBJ.put("visitCode", visitCode);
				} else
					throw new RuntimeException(" Error in episode creation - visit code");
			} else
				throw new RuntimeException(" Error in episode creation");

		} else
			throw new RuntimeException("Error in episode creation. Missing Beneficiary registration details");

		return new Gson().toJson(responseOBJ);
	}
}
