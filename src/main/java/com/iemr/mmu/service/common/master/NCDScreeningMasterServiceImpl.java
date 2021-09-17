package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.doctor.ChiefComplaintMaster;
import com.iemr.mmu.data.labModule.ProcedureData;
import com.iemr.mmu.data.masterdata.anc.DiseaseType;
import com.iemr.mmu.data.masterdata.nurse.FamilyMemberType;
import com.iemr.mmu.repo.doctor.ChiefComplaintMasterRepo;
import com.iemr.mmu.repo.doctor.LabTestMasterRepo;
import com.iemr.mmu.repo.labModule.ProcedureRepo;
import com.iemr.mmu.repo.masterrepo.anc.DiseaseTypeRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.BPAndDiabeticStatusRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.IDRS_ScreenQuestionsRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningConditionRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningReasonRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.PhysicalActivityRepo;
import com.iemr.mmu.repo.masterrepo.nurse.FamilyMemberMasterRepo;

@Service
public class NCDScreeningMasterServiceImpl implements NCDScreeningMasterService {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private NCDScreeningConditionRepo ncdScreeningConditionRepo;
	private NCDScreeningReasonRepo ncdScreeningReasonRepo;
	private BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo;
	private LabTestMasterRepo labTestMasterRepo;
	private ChiefComplaintMasterRepo chiefComplaintMasterRepo;
	private ProcedureRepo procedureRepo;

	@Autowired
	private IDRS_ScreenQuestionsRepo iDRS_ScreenQuestionsRepo;
	@Autowired
	private PhysicalActivityRepo physicalActivityRepo;
	@Autowired
	private DiseaseTypeRepo diseaseTypeRepo;
	@Autowired
	private FamilyMemberMasterRepo familyMemberMasterRepo;

	@Autowired
	public void setNcdScreeningConditionRepo(NCDScreeningConditionRepo ncdScreeningConditionRepo) {
		this.ncdScreeningConditionRepo = ncdScreeningConditionRepo;
	}

	@Autowired
	public void setNcdScreeningReasonRepo(NCDScreeningReasonRepo ncdScreeningReasonRepo) {
		this.ncdScreeningReasonRepo = ncdScreeningReasonRepo;
	}

	@Autowired
	public void setBpAndDiabeticStatusRepo(BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo) {
		this.bpAndDiabeticStatusRepo = bpAndDiabeticStatusRepo;
	}

	@Autowired
	public void setLabTestMasterRepo(LabTestMasterRepo labTestMasterRepo) {
		this.labTestMasterRepo = labTestMasterRepo;
	}

	@Autowired
	public void setChiefComplaintMasterRepo(ChiefComplaintMasterRepo chiefComplaintMasterRepo) {
		this.chiefComplaintMasterRepo = chiefComplaintMasterRepo;
	}

	@Autowired
	public void setProcedureRepo(ProcedureRepo procedureRepo) {
		this.procedureRepo = procedureRepo;
	}

	@Override
	public List<Object[]> getNCDScreeningConditions() {
		List<Object[]> ncdScreeningConditions = null;
		try {
			ncdScreeningConditions = ncdScreeningConditionRepo.getNCDScreeningConditions();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ncdScreeningConditions;
	}

	@Override
	public List<Object[]> getNCDScreeningReasons() {
		List<Object[]> ncdScreeningReasons = null;
		try {
			ncdScreeningReasons = ncdScreeningReasonRepo.getNCDScreeningReasons();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ncdScreeningReasons;
	}

	@Override
	public List<Object[]> getBPAndDiabeticStatus(Boolean isBPStatus) {
		List<Object[]> bpAndDiabeticStatus = null;
		try {
			bpAndDiabeticStatus = bpAndDiabeticStatusRepo.getBPAndDiabeticStatus(isBPStatus);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bpAndDiabeticStatus;
	}

	@Override
	public ArrayList<Object[]> getNCDTest() {
		ArrayList<Object[]> labTests = null;
		try {
			labTests = labTestMasterRepo.getTestsBYVisitCategory("NCD Screening");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return labTests;
	}

	@Override
	public List<Object[]> getChiefComplaintMaster() {
		List<Object[]> ccList = null;
		try {
			ccList = chiefComplaintMasterRepo.getChiefComplaintMaster();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ccList;
	}

	@Override
	public String getNCDScreeningMasterData(Integer visitCategoryID, Integer providerServiceMapID, String gender) {
		Map<String, Object> resMap = new HashMap<String, Object>();

//		resMap.put("ncdScreeningConditions",
//				NCDScreeningCondition.getNCDScreeningCondition((ArrayList<Object[]>) getNCDScreeningConditions()));
//		resMap.put("ncdScreeningReasons",
//				NCDScreeningReason.getNCDScreeningReason((ArrayList<Object[]>) getNCDScreeningReasons()));
//		resMap.put("bloodPressureStatus",
//				BPAndDiabeticStatus.getBPAndDiabeticStatus((ArrayList<Object[]>) getBPAndDiabeticStatus(true)));
//		resMap.put("diabeticStatus",
//				BPAndDiabeticStatus.getBPAndDiabeticStatus((ArrayList<Object[]>) getBPAndDiabeticStatus(false)));
//		resMap.put("ncdTests", LabTestMaster.getNCDScreeningTests(getNCDTest()));
//		resMap.put("chiefComplaintMaster",
//				ChiefComplaintMaster.getChiefComplaintMasters((ArrayList<Object[]>) getChiefComplaintMaster()));

		resMap.put("chiefComplaintMaster",
				ChiefComplaintMaster.getChiefComplaintMasters((ArrayList<Object[]>) getChiefComplaintMaster()));

		ArrayList<Object[]> DiseaseTypes = diseaseTypeRepo.getDiseaseTypes();
		resMap.put("DiseaseTypes", DiseaseType.getDiseaseTypes(DiseaseTypes));

		ArrayList<Object[]> familyMemberTypes = familyMemberMasterRepo.getFamilyMemberTypeMaster();
		resMap.put("familyMemberTypes", FamilyMemberType.getFamilyMemberTypeMasterData(familyMemberTypes));

		resMap.put("IDRSQuestions", iDRS_ScreenQuestionsRepo.findByDeleted(false));
		resMap.put("physicalActivity", physicalActivityRepo.findByDeleted(false));
		ArrayList<Object[]> procedures = procedureRepo.getProcedureMasterData(providerServiceMapID, gender);
		resMap.put("procedures", ProcedureData.getProcedures(procedures));

		return new Gson().toJson(resMap);
	}

}
