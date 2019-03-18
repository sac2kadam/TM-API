package com.iemr.mmu.repo.quickConsultation;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickConsultation.PrescriptionDetail;

@Repository
public interface PrescriptionDetailRepo extends CrudRepository<PrescriptionDetail, Long> {

	@Query(" SELECT prescriptionID, beneficiaryRegID, benVisitID, providerServiceMapID, diagnosisProvided, "
			+ " instruction, externalInvestigation, visitCode from PrescriptionDetail ba "
			+ " WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false "
			+ " ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenPrescription(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	public ArrayList<PrescriptionDetail> findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);

	@Query(" SELECT prescriptionID, beneficiaryRegID, benVisitID, providerServiceMapID, diagnosisProvided, instruction, "
			+ " visitCode FROM PrescriptionDetail ba "
			+ " WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode "
			+ " AND ba.deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getGeneralOPDDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Query("SELECT processed from PrescriptionDetail where prescriptionID = :prescriptionID AND "
			+ " beneficiaryRegID=:benRegID AND visitCode = :visitCode")
	public String getGeneralOPDDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Query(nativeQuery = true, value = "SELECT ExternalInvestigation FROM t_prescription "
			+ " WHERE BeneficiaryRegID=:benRegID AND VisitCode = :visitCode ORDER BY CreatedDate DESC LIMIT 1")
	public String getExternalinvestigationForVisitCode(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update PrescriptionDetail set diagnosisProvided=:diagnosisProvided, "
			+ " instruction=:instruction, externalInvestigation =:externalInvestigation, "
			+ " modifiedBy=:modifiedBy, processed=:processed, "
			+ " diagnosisProvided_SCTCode =:diagnosisProvided_SCTCode, "
			+ " diagnosisProvided_SCTTerm =:diagnosisProvided_SCTTerm "
			+ " where prescriptionID=:prescriptionID AND visitCode=:visitCode "
			+ " AND beneficiaryRegID=:beneficiaryRegID ")
	public int updatePrescription(@Param("diagnosisProvided") String diagnosisProvided,
			@Param("instruction") String instruction, @Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode, @Param("prescriptionID") Long prescriptionID,
			@Param("externalInvestigation") String externalInvestigation,
			@Param("diagnosisProvided_SCTCode") String diagnosisProvided_SCTCode,
			@Param("diagnosisProvided_SCTTerm") String diagnosisProvided_SCTTerm);

}
