package com.iemr.mmu.service.anc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.anc.ANCDiagnosis;
import com.iemr.mmu.repo.nurse.anc.ANCDiagnosisRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class ANCDoctorServiceImpl implements ANCDoctorService {

	private ANCDiagnosisRepo ancDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setAncDiagnosisRepo(ANCDiagnosisRepo ancDiagnosisRepo) {
		this.ancDiagnosisRepo = ancDiagnosisRepo;
	}

	public Long saveBenANCDiagnosis(JsonObject obj, Long prescriptionID) throws IEMRException {
		Long ID = null;
		ANCDiagnosis ancDiagnosis = InputMapper.gson().fromJson(obj, ANCDiagnosis.class);
		ancDiagnosis.setPrescriptionID(prescriptionID);

		ANCDiagnosis res = ancDiagnosisRepo.save(ancDiagnosis);
		if (null != res && res.getID() > 0) {
			ID = res.getID();
		}
		return ID;
	}

	public String getANCDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {
		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
				visitCode);
		ArrayList<Object[]> resList = ancDiagnosisRepo.getANCDiagnosisDetails(beneficiaryRegID, visitCode);
		ANCDiagnosis ancDiagnosisDetails = ANCDiagnosis.getANCDiagnosisDetails(resList);
		if (externalInvestigation != null)
			ancDiagnosisDetails.setExternalInvestigation(externalInvestigation);
		return new Gson().toJson(ancDiagnosisDetails);
	}

	public int updateBenANCDiagnosis(ANCDiagnosis ancDiagnosis) throws IEMRException {
		int res = 0;
		String processed = ancDiagnosisRepo.getANCDiagnosisStatus(ancDiagnosis.getBeneficiaryRegID(),
				ancDiagnosis.getVisitCode(), ancDiagnosis.getPrescriptionID());
		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}
		ancDiagnosis.setProcessed(processed);

		if (processed != null) {
			int i = ancDiagnosisRepo.updateANCDiagnosis(ancDiagnosis.getHighRiskStatus(),
					ancDiagnosis.getHighRiskCondition(), ancDiagnosis.getComplicationOfCurrentPregnancy(),
					ancDiagnosis.getIsMaternalDeath(), ancDiagnosis.getPlaceOfDeath(), ancDiagnosis.getDateOfDeath(),
					ancDiagnosis.getCauseOfDeath(), ancDiagnosis.getCreatedBy(), ancDiagnosis.getProcessed(),
					ancDiagnosis.getBeneficiaryRegID(), ancDiagnosis.getVisitCode());
			if (i > 0) {
				res = 1;
			}
		} else {
			ANCDiagnosis ancDiagnosisRS = ancDiagnosisRepo.save(ancDiagnosis);
			if (ancDiagnosisRS != null && ancDiagnosisRS.getID() > 0)
				res = 1;
		}

		return res;
	}
}
