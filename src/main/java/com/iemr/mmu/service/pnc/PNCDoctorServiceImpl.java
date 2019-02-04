package com.iemr.mmu.service.pnc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.mmu.data.pnc.PNCDiagnosis;
import com.iemr.mmu.repo.nurse.pnc.PNCDiagnosisRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.service.common.transaction.CommonDoctorServiceImpl;
import com.iemr.mmu.utils.exception.IEMRException;
import com.iemr.mmu.utils.mapper.InputMapper;

@Service
public class PNCDoctorServiceImpl implements PNCDoctorService {

	private PNCDiagnosisRepo pncDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;
	private CommonDoctorServiceImpl commonDoctorServiceImpl;

	@Autowired
	public void setCommonDoctorServiceImpl(CommonDoctorServiceImpl commonDoctorServiceImpl) {
		this.commonDoctorServiceImpl = commonDoctorServiceImpl;
	}

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setPncDiagnosisRepo(PNCDiagnosisRepo pncDiagnosisRepo) {
		this.pncDiagnosisRepo = pncDiagnosisRepo;
	}

	public Long saveBenPNCDiagnosis(JsonObject obj, Long prescriptionID) throws IEMRException {
		Long ID = null;
		PNCDiagnosis pncDiagnosis = InputMapper.gson().fromJson(obj, PNCDiagnosis.class);
		pncDiagnosis.setPrescriptionID(prescriptionID);

		// getting snomedCT code for provisional diagnosis
		String[] snomedCTArrPD = commonDoctorServiceImpl.getSnomedCTcode(pncDiagnosis.getProvisionalDiagnosis());
		// getting snomedCT code for confirmatory diagnosis
		String[] snomedCTArrCD = commonDoctorServiceImpl.getSnomedCTcode(pncDiagnosis.getConfirmatoryDiagnosis());

		if (snomedCTArrPD != null && snomedCTArrPD.length > 1) {
			pncDiagnosis.setProvisionalDiagnosisSCTCode(snomedCTArrPD[0]);
			pncDiagnosis.setProvisionalDiagnosisSCTTerm(snomedCTArrPD[1]);

		}
		if (snomedCTArrCD != null && snomedCTArrCD.length > 1) {
			pncDiagnosis.setConfirmatoryDiagnosisSCTCode(snomedCTArrCD[0]);
			pncDiagnosis.setConfirmatoryDiagnosisSCTTerm(snomedCTArrCD[1]);
		}

		PNCDiagnosis res = pncDiagnosisRepo.save(pncDiagnosis);
		if (null != res && res.getID() > 0) {
			ID = res.getID();
		}
		return ID;
	}

	public String getPNCDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {
		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
				visitCode);
		ArrayList<Object[]> resList = pncDiagnosisRepo.getPNCDiagnosisDetails(beneficiaryRegID, visitCode);
		PNCDiagnosis pncDiagnosisDetails = PNCDiagnosis.getPNCDiagnosisDetails(resList);
		if (externalInvestigation != null)
			pncDiagnosisDetails.setExternalInvestigation(externalInvestigation);
		return new Gson().toJson(pncDiagnosisDetails);
	}

	public int updateBenPNCDiagnosis(PNCDiagnosis pncDiagnosis) throws IEMRException {
		int res = 0;

		// getting snomedCT code for provisional diagnosis
		String[] snomedCTArrPD = commonDoctorServiceImpl.getSnomedCTcode(pncDiagnosis.getProvisionalDiagnosis());
		// getting snomedCT code for confirmatory diagnosis
		String[] snomedCTArrCD = commonDoctorServiceImpl.getSnomedCTcode(pncDiagnosis.getConfirmatoryDiagnosis());

		if (snomedCTArrPD != null && snomedCTArrPD.length > 1) {
			pncDiagnosis.setProvisionalDiagnosisSCTCode(snomedCTArrPD[0]);
			pncDiagnosis.setProvisionalDiagnosisSCTTerm(snomedCTArrPD[1]);

		}
		if (snomedCTArrCD != null && snomedCTArrCD.length > 1) {
			pncDiagnosis.setConfirmatoryDiagnosisSCTCode(snomedCTArrCD[0]);
			pncDiagnosis.setConfirmatoryDiagnosisSCTTerm(snomedCTArrCD[1]);
		}

		String processed = pncDiagnosisRepo.getPNCDiagnosisStatus(pncDiagnosis.getBeneficiaryRegID(),
				pncDiagnosis.getVisitCode(), pncDiagnosis.getPrescriptionID());

		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}

		if (processed != null) {
			res = pncDiagnosisRepo.updatePNCDiagnosis(pncDiagnosis.getProvisionalDiagnosis(),
					pncDiagnosis.getConfirmatoryDiagnosis(), pncDiagnosis.getIsMaternalDeath(),
					pncDiagnosis.getPlaceOfDeath(), pncDiagnosis.getDateOfDeath(), pncDiagnosis.getCauseOfDeath(),
					pncDiagnosis.getCreatedBy(), processed, pncDiagnosis.getBeneficiaryRegID(),
					pncDiagnosis.getVisitCode(), pncDiagnosis.getProvisionalDiagnosisSCTCode(),
					pncDiagnosis.getProvisionalDiagnosisSCTTerm(), pncDiagnosis.getConfirmatoryDiagnosisSCTCode(),
					pncDiagnosis.getConfirmatoryDiagnosisSCTTerm(), pncDiagnosis.getPrescriptionID());
		} else {
			PNCDiagnosis pncDiagnosisRes = pncDiagnosisRepo.save(pncDiagnosis);
			if (null != pncDiagnosisRes && pncDiagnosisRes.getID() > 0) {
				res = 1;
			}
		}
		return res;
	}
}
