package com.iemr.mmu.service.common.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.doctor.LabTestMaster;
import com.iemr.mmu.data.masterdata.ncdscreening.BPAndDiabeticStatus;
import com.iemr.mmu.data.masterdata.ncdscreening.NCDScreeningCondition;
import com.iemr.mmu.data.masterdata.ncdscreening.NCDScreeningReason;
import com.iemr.mmu.repo.doctor.LabTestMasterRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.BPAndDiabeticStatusRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningConditionRepo;
import com.iemr.mmu.repo.masterrepo.ncdScreening.NCDScreeningReasonRepo;

@Service
public class NCDScreeningMasterServiceImpl implements NCDScreeningMasterService {
	
	private NCDScreeningConditionRepo ncdScreeningConditionRepo;
	private NCDScreeningReasonRepo ncdScreeningReasonRepo;
	private BPAndDiabeticStatusRepo bpAndDiabeticStatusRepo;
	private LabTestMasterRepo labTestMasterRepo;
	
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

	@Override
	public List<Object[]> getNCDScreeningConditions() {
		List<Object[]> ncdScreeningConditions  = null;
		try {
			ncdScreeningConditions = ncdScreeningConditionRepo.getNCDScreeningConditions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ncdScreeningConditions;
	}

	@Override
	public List<Object[]> getNCDScreeningReasons() {
		List<Object[]> ncdScreeningReasons = null;
		try {
			ncdScreeningReasons = ncdScreeningReasonRepo.getNCDScreeningReasons();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ncdScreeningReasons;
	}

	@Override
	public List<Object[]> getBPAndDiabeticStatus(Boolean isBPStatus) {
		List<Object[]> bpAndDiabeticStatus = null;
		try {
			bpAndDiabeticStatus = bpAndDiabeticStatusRepo.getBPAndDiabeticStatus(isBPStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bpAndDiabeticStatus;
	}

	@Override
	public ArrayList<Object[]> getNCDTest() {
		ArrayList<Object[]> labTests = null;
		try {
			labTests = labTestMasterRepo.getTestsBYVisitCategory("NCD Screening");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return labTests;
	}

	
	@Override
	public String getNCDScreeningMasterData() {
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		resMap.put("ncdScreeningConditions", NCDScreeningCondition.getNCDScreeningCondition((ArrayList<Object[]>) getNCDScreeningConditions()));
		resMap.put("ncdScreeningReasons", NCDScreeningReason.getNCDScreeningReason((ArrayList<Object[]>) getNCDScreeningReasons()));
		resMap.put("bloodPressureStatus", BPAndDiabeticStatus.getBPAndDiabeticStatus((ArrayList<Object[]>) getBPAndDiabeticStatus(true)));
		resMap.put("diabeticStatus", BPAndDiabeticStatus.getBPAndDiabeticStatus((ArrayList<Object[]>) getBPAndDiabeticStatus(false)));
		resMap.put("ncdTests", LabTestMaster.getNCDScreeningTests(getNCDTest()));
		
		return new Gson().toJson(resMap);
	}
}
