package com.iemr.mmu.service.ncdCare;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.iemr.mmu.data.ncdcare.NCDCareDiagnosis;
import com.iemr.mmu.repo.nurse.ncdcare.NCDCareDiagnosisRepo;
import com.iemr.mmu.repo.quickConsultation.PrescriptionDetailRepo;
import com.iemr.mmu.utils.exception.IEMRException;

@Service
public class NCDCareDoctorServiceImpl implements NCDCareDoctorService {
	private NCDCareDiagnosisRepo ncdCareDiagnosisRepo;
	private PrescriptionDetailRepo prescriptionDetailRepo;

	@Autowired
	public void setPrescriptionDetailRepo(PrescriptionDetailRepo prescriptionDetailRepo) {
		this.prescriptionDetailRepo = prescriptionDetailRepo;
	}

	@Autowired
	public void setNcdCareDiagnosisRepo(NCDCareDiagnosisRepo ncdCareDiagnosisRepo) {
		this.ncdCareDiagnosisRepo = ncdCareDiagnosisRepo;
	}

	public long saveNCDDiagnosisData(NCDCareDiagnosis ncdDiagnosis) {
		long res = 0;

		NCDCareDiagnosis diagnosis = ncdCareDiagnosisRepo.save(ncdDiagnosis);
		if (null != diagnosis) {
			res = diagnosis.getID();
		}
		return res;
	}

	public String getNCDCareDiagnosisDetails(Long beneficiaryRegID, Long visitCode) {

		ArrayList<Object[]> prescriptionData = prescriptionDetailRepo
				.getExternalinvestigationForVisitCode(beneficiaryRegID, visitCode);

		String externalInvestigation = null;
		String instruction = null;
		if (prescriptionData != null && prescriptionData.size() > 0) {
			externalInvestigation = String.valueOf(prescriptionData.get(0)[0]);
			instruction = String.valueOf(prescriptionData.get(0)[1]);
		}

//		String externalInvestigation = prescriptionDetailRepo.getExternalinvestigationForVisitCode(beneficiaryRegID,
//				visitCode);

		ArrayList<Object[]> resList = ncdCareDiagnosisRepo.getNCDCareDiagnosisDetails(beneficiaryRegID, visitCode);
		NCDCareDiagnosis ncdCareDiagnosisDetails = NCDCareDiagnosis.getNCDCareDiagnosisDetails(resList);
		if (externalInvestigation != null)
			ncdCareDiagnosisDetails.setExternalInvestigation(externalInvestigation);
		if (instruction != null)
			ncdCareDiagnosisDetails.setSpecialistDiagnosis(instruction);

		return new Gson().toJson(ncdCareDiagnosisDetails);
	}

	public int updateBenNCDCareDiagnosis(NCDCareDiagnosis ncdCareDiagnosis) throws IEMRException {
		int res = 0;
		String processed = ncdCareDiagnosisRepo.getNCDCareDiagnosisStatus(ncdCareDiagnosis.getBeneficiaryRegID(),
				ncdCareDiagnosis.getVisitCode(), ncdCareDiagnosis.getPrescriptionID());

		if (null != processed && !processed.equals("N")) {
			processed = "U";
		}

		ncdCareDiagnosis.setProcessed(processed);
		if (processed != null) {
			res = ncdCareDiagnosisRepo.updateNCDCareDiagnosis(ncdCareDiagnosis.getNcdCareCondition(),
					ncdCareDiagnosis.getNcdComplication(), ncdCareDiagnosis.getNcdCareType(),
					ncdCareDiagnosis.getCreatedBy(), ncdCareDiagnosis.getProcessed(),
					ncdCareDiagnosis.getBeneficiaryRegID(), ncdCareDiagnosis.getVisitCode(),
					ncdCareDiagnosis.getPrescriptionID());
		} else {
			NCDCareDiagnosis ncdCareDiagnosisRes = ncdCareDiagnosisRepo.save(ncdCareDiagnosis);
			if (null != ncdCareDiagnosisRes && ncdCareDiagnosisRes.getID() > 0) {
				res = 1;
			}
		}

		return res;
	}
}
