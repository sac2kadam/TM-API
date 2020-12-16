package com.iemr.mmu.repo.nurse.ncdscreening;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.IDRSData;

@Repository
public interface IDRSDataRepo extends CrudRepository<IDRSData, Long> {

	@Query(" SELECT id,beneficiaryRegID, benVisitID, providerServiceMapID, idrsQuestionID, idrsScore, question, answer, "
			+ "suspectedDisease, visitCode FROM IDRSData "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenIdrsDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	@Query("select a from IDRSData a where a.beneficiaryRegID = :beneficiaryRegID AND a.createdDate >= :tDate "
			+ " AND a.diseaseQuestionType "
			+ " IN ('Epilepsy', 'Asthma', 'Malaria Screening', 'Vision Screening', 'Tuberculosis Screening') "
			+ " ORDER BY Date(a.createdDate) DESC, a.visitCode ")
	public ArrayList<IDRSData> getBenIdrsDetailsLast_3_Month(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("tDate") Timestamp tDate);

	@Query("select count(a.id) from IDRSData a where a.beneficiaryRegID = :beneficiaryRegID and a.isDiabetic is true ")
	public Integer isDiabeticCheck(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select a from IDRSData a where a.beneficiaryRegID = :beneficiaryRegID AND a.diseaseQuestionType = 'Diabetes' "
			+ " ORDER BY Date(a.createdDate) DESC  ")
	public ArrayList<IDRSData> getBenPreviousDiabetesDetails(@Param("beneficiaryRegID") Long beneficiaryRegID);
}
