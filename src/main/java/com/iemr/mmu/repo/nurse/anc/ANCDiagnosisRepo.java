package com.iemr.mmu.repo.nurse.anc;

import java.sql.Date;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.ANCDiagnosis;

@Repository
public interface ANCDiagnosisRepo extends CrudRepository<ANCDiagnosis, Long> {

	@Query(" SELECT ID, beneficiaryRegID, benVisitID, providerServiceMapID, prescriptionID, highRiskStatus, highRiskCondition, complicationOfCurrentPregnancy, "
			+ "isMaternalDeath, placeOfDeath, dateOfDeath, causeOfDeath, visitCode from ANCDiagnosis ba "
			+ "WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false "
			+ " ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getANCDiagnosisDetails(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	ArrayList<ANCDiagnosis> findByBeneficiaryRegIDAndVisitCode(Long benRegID, Long visitCode);

	@Query("SELECT processed from ANCDiagnosis" + " WHERE beneficiaryRegID=:benRegID "
			+ " AND visitCode = :visitCode AND prescriptionID = :prescriptionID")
	public String getANCDiagnosisStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("prescriptionID") Long prescriptionID);

	@Transactional
	@Modifying
	@Query("update ANCDiagnosis set highRiskStatus=:highRiskStatus, highRiskCondition=:highRiskCondition, "
			+ "complicationOfCurrentPregnancy=:complicationOfCurrentPregnancy, isMaternalDeath=:isMaternalDeath, placeOfDeath=:placeOfDeath,"
			+ "dateOfDeath=:dateOfDeath, causeOfDeath=:causeOfDeath, modifiedBy=:modifiedBy, processed=:processed, "
			+ " otherCurrPregComplication = :othrPrgComp "
			+ " where visitCode=:visitCode AND beneficiaryRegID=:beneficiaryRegID")
	public int updateANCDiagnosis(@Param("highRiskStatus") String highRiskStatus,
			@Param("highRiskCondition") String highRiskCondition,
			@Param("complicationOfCurrentPregnancy") String complicationOfCurrentPregnancy,
			@Param("isMaternalDeath") Boolean isMaternalDeath, @Param("placeOfDeath") String placeOfDeath,
			@Param("dateOfDeath") Date dateOfDeath, @Param("causeOfDeath") String causeOfDeath,
			@Param("modifiedBy") String modifiedBy, @Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID, @Param("visitCode") Long visitCode,
			@Param("othrPrgComp") String othrPrgComp);

	@Query(" SELECT ID FROM ANCDiagnosis WHERE beneficiaryRegID = :benRegID "
			+ " AND complicationOfCurrentPregnancy like %:pregComp%  AND deleted = false ")
	public ArrayList<Long> getANCDiagnosisDataForHRP(@Param("benRegID") Long benRegID,
			@Param("pregComp") String pregComp);
}
