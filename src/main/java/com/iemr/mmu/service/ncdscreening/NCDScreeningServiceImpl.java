package com.iemr.mmu.service.ncdscreening;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.ncdScreening.NCDScreening;
import com.iemr.mmu.data.nurse.BenAnthropometryDetail;
import com.iemr.mmu.data.nurse.BenPhysicalVitalDetail;
import com.iemr.mmu.data.nurse.BeneficiaryVisitDetail;
import com.iemr.mmu.data.nurse.CommonUtilityClass;
import com.iemr.mmu.repo.benFlowStatus.BeneficiaryFlowStatusRepo;
import com.iemr.mmu.repo.nurse.BenVisitDetailRepo;
import com.iemr.mmu.service.benFlowStatus.CommonBenStatusFlowServiceImpl;
import com.iemr.mmu.service.common.transaction.CommonNurseServiceImpl;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class NCDScreeningServiceImpl implements NCDScreeningService {

	private NCDScreeningNurseServiceImpl ncdScreeningNurseServiceImpl;
	private CommonNurseServiceImpl commonNurseServiceImpl;
	private CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl;
	private BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo;

	@Autowired
	private BenVisitDetailRepo benVisitDetailRepo;

	@Autowired
	public void setBeneficiaryFlowStatusRepo(BeneficiaryFlowStatusRepo beneficiaryFlowStatusRepo) {
		this.beneficiaryFlowStatusRepo = beneficiaryFlowStatusRepo;
	}

	@Autowired
	public void setCommonBenStatusFlowServiceImpl(CommonBenStatusFlowServiceImpl commonBenStatusFlowServiceImpl) {
		this.commonBenStatusFlowServiceImpl = commonBenStatusFlowServiceImpl;
	}

	@Autowired
	public void setCommonNurseServiceImpl(CommonNurseServiceImpl commonNurseServiceImpl) {
		this.commonNurseServiceImpl = commonNurseServiceImpl;
	}

	@Autowired
	public void setNcdScreeningNurseServiceImpl(NCDScreeningNurseServiceImpl ncdScreeningNurseServiceImpl) {
		this.ncdScreeningNurseServiceImpl = ncdScreeningNurseServiceImpl;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer saveNCDScreeningNurseData(JsonObject jsonObject) throws Exception {

		Integer result = null;

		if (jsonObject != null && jsonObject.has("visitDetails") && jsonObject.has("ncdScreeningDetails")) {
			// JsonElement visitDetails = jsonObject.get("visitDetails");
			// JsonElement ncdScreeningDetails =
			// jsonObject.get("ncdScreeningDetails");
			CommonUtilityClass nurseUtilityClass = InputMapper.gson().fromJson(jsonObject, CommonUtilityClass.class);
			Long benFlowID = nurseUtilityClass.getBenFlowID();

			BeneficiaryVisitDetail beneficiaryVisitDetail = InputMapper.gson().fromJson(jsonObject.get("visitDetails"),
					BeneficiaryVisitDetail.class);

			NCDScreening ncdScreening = InputMapper.gson().fromJson(jsonObject.get("ncdScreeningDetails"),
					NCDScreening.class);

			if (ncdScreening.getNextScreeningDate() != null)
				ncdScreening.setNextScreeningDateDB(Timestamp
						.valueOf(ncdScreening.getNextScreeningDate().replaceAll("T", " ").replaceAll("Z", " ")));

			Long visitID = commonNurseServiceImpl.saveBeneficiaryVisitDetails(beneficiaryVisitDetail);

			// 11-06-2018 visit code
			Long benVisitCode = commonNurseServiceImpl.generateVisitCode(visitID, nurseUtilityClass.getVanID(),
					nurseUtilityClass.getSessionID());

			if (null != visitID) {

				Long vitalSuccessFlag = saveNCDScreeningVitalDetails(jsonObject, visitID, benVisitCode);
				Long saveNCDScreeningDetails = null;
				ncdScreening.setBenVisitID(visitID);
				ncdScreening.setVisitCode(benVisitCode);
				saveNCDScreeningDetails = ncdScreeningNurseServiceImpl.saveNCDScreeningDetails(ncdScreening);

				if (null != vitalSuccessFlag && null != saveNCDScreeningDetails) {

					int i = updateBenFlowNurseAfterNurseActivityANC(beneficiaryVisitDetail.getBeneficiaryRegID(),
							visitID, benFlowID, beneficiaryVisitDetail.getVisitReason(),
							beneficiaryVisitDetail.getVisitCategory(), ncdScreening.getIsScreeningComplete(),
							benVisitCode, nurseUtilityClass.getVanID());

					result = 1;
				} else {
					throw new RuntimeException("Error occurred while saving data");
				}
			} else
				throw new RuntimeException("Error occurred while creating beneficiary visit");
		} else {
			throw new Exception("Invalid input");
		}
		return result;
	}

	private int updateBenFlowNurseAfterNurseActivityANC(Long benRegID, Long benVisitID, Long benFlowID,
			String visitReason, String visitCategory, Boolean isScreeningDone, Long benVisitCode, Integer vanID) {
		short nurseFlag;
		short docFlag = (short) 0;
		short labIteration = (short) 0;

		short specialistFlag = (short) 0;
		Timestamp tcDate = null;
		Integer tcSpecialistUserID = null;

		if (isScreeningDone != null && isScreeningDone == true)
			nurseFlag = (short) 9;
		else
			nurseFlag = (short) 100;

		int rs = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseActivity(benFlowID, benRegID, benVisitID,
				visitReason, visitCategory, nurseFlag, docFlag, labIteration, (short) 0, (short) 0, benVisitCode, vanID,
				specialistFlag, tcDate, tcSpecialistUserID);

		return rs;
	}

	public Long saveNCDScreeningVitalDetails(JsonObject jsonObject, Long benVisitID, Long benVisitCode)
			throws Exception {

		Long vitalSuccessFlag = null;
		JsonElement ncdScreeningDetails = jsonObject.get("ncdScreeningDetails");

		BenAnthropometryDetail anthropometryDetail = null;
		BenPhysicalVitalDetail physicalVitalDetail = null;

		anthropometryDetail = InputMapper.gson().fromJson(ncdScreeningDetails, BenAnthropometryDetail.class);

		physicalVitalDetail = InputMapper.gson().fromJson(ncdScreeningDetails, BenPhysicalVitalDetail.class);

		Long saveAnthropometryDetail = null;
		if (null != anthropometryDetail) {
			anthropometryDetail.setBenVisitID(benVisitID);
			anthropometryDetail.setVisitCode(benVisitCode);
			saveAnthropometryDetail = commonNurseServiceImpl
					.saveBeneficiaryPhysicalAnthropometryDetails(anthropometryDetail);
		}
		Long savePhysicalVitalDetails = null;
		if (null != physicalVitalDetail) {
			physicalVitalDetail.setBenVisitID(benVisitID);
			physicalVitalDetail.setVisitCode(benVisitCode);
			savePhysicalVitalDetails = commonNurseServiceImpl.saveBeneficiaryPhysicalVitalDetails(physicalVitalDetail);
		}

		if ((null != saveAnthropometryDetail && saveAnthropometryDetail > 0)
				&& (null != savePhysicalVitalDetails && savePhysicalVitalDetails > 0)) {
			vitalSuccessFlag = saveAnthropometryDetail;
		}

		return vitalSuccessFlag;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateNurseNCDScreeningDetails(JsonObject jsonObject) throws Exception {

		NCDScreening ncdScreening = InputMapper.gson().fromJson(jsonObject, NCDScreening.class);

		if (ncdScreening.getNextScreeningDate() != null)
			ncdScreening.setNextScreeningDateDB(
					Timestamp.valueOf(ncdScreening.getNextScreeningDate().replaceAll("T", " ").replaceAll("Z", " ")));

		BenAnthropometryDetail anthropometryDetail = InputMapper.gson().fromJson(jsonObject,
				BenAnthropometryDetail.class);
		BenPhysicalVitalDetail physicalVitalDetail = InputMapper.gson().fromJson(jsonObject,
				BenPhysicalVitalDetail.class);

		Integer result = null;

		Integer updateNCDScreeningDetails = ncdScreeningNurseServiceImpl.updateNCDScreeningDetails(ncdScreening);
		Integer updateANCAnthropometryDetails = commonNurseServiceImpl
				.updateANCAnthropometryDetails(anthropometryDetail);
		Integer updateANCPhysicalVitalDetails = commonNurseServiceImpl
				.updateANCPhysicalVitalDetails(physicalVitalDetail);

		// add file/doc id
		Integer[] docIdArr = ncdScreening.getFileIDs();
		StringBuilder sb = new StringBuilder();
		if (docIdArr != null && docIdArr.length > 0) {
			for (Integer i : docIdArr) {
				sb.append(i + ",");
			}
		}
		if (sb.length() > 0)
			benVisitDetailRepo.updateFileID(sb.toString(), ncdScreening.getBeneficiaryRegID(),
					ncdScreening.getVisitCode());

		if (null != updateANCAnthropometryDetails && null != updateANCPhysicalVitalDetails
				&& null != updateNCDScreeningDetails) {

			short nurseFlag = (short) 0;

			if (ncdScreening.getIsScreeningComplete() != null && ncdScreening.getIsScreeningComplete() == true)
				nurseFlag = (short) 9;
			else
				nurseFlag = (short) 100;

			int i = commonBenStatusFlowServiceImpl.updateBenFlowNurseAfterNurseUpdateNCD_Screening(
					ncdScreening.getBenFlowID(), ncdScreening.getBeneficiaryRegID(), nurseFlag);

			result = 1;
		} else
			throw new RuntimeException("Error occured while updating record in between...");

		return result;

	}

	@Override
	public String getNCDScreeningDetails(Long beneficiaryRegID, Long visitCode) {
		String ncdScreeningDetails = ncdScreeningNurseServiceImpl.getNCDScreeningDetails(beneficiaryRegID, visitCode);
		String anthropometryDetails = commonNurseServiceImpl
				.getBeneficiaryPhysicalAnthropometryDetails(beneficiaryRegID, visitCode);
		String vitalDetails = commonNurseServiceImpl.getBeneficiaryPhysicalVitalDetails(beneficiaryRegID, visitCode);

		Map<String, Object> res = new HashMap<String, Object>();

		if (ncdScreeningDetails != null && anthropometryDetails != null && vitalDetails != null) {
			res.put("ncdScreeningDetails", ncdScreeningDetails);
			res.put("anthropometryDetails", anthropometryDetails);
			res.put("vitalDetails", vitalDetails);
		} else {
			// Failed to Fetch Beneficiary NCD Screening Details
		}
		return res.toString();
	}

	public String getNcdScreeningVisitCnt(Long beneficiaryRegID) {
		Map<String, Long> returnMap = new HashMap<>();
		Long visitCount = beneficiaryFlowStatusRepo.getNcdScreeningVisitCount(beneficiaryRegID);
		returnMap.put("ncdScreeningVisitCount", visitCount + 1);
		return new Gson().toJson(returnMap);
	}

}
