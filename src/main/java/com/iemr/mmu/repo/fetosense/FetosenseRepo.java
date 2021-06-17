package com.iemr.mmu.repo.fetosense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.fetosense.Fetosense;

@Repository
public interface FetosenseRepo extends CrudRepository<Fetosense, Short> {

	@Query("SELECT f FROM Fetosense f WHERE f.beneficiaryRegID = :beneficiaryRegID AND f.fetosenseID = :fetosenseID")
	public Fetosense getFetosenseDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("fetosenseID") Integer fetosenseID);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE Fetosense f SET f.partnerName = :partnerName,f.fetosensePartnerID = :fetosensePartnerID,f.fetosenseMotherID = :fetosenseMotherID, "
			+ "f.testId = :testId, f.deviceId = :deviceId, f.testDoneAt = :testDoneAt, f.lengthOfTest = :lengthOfTest, "
			+ "f.basalHeartRate = :basalHeartRate, f.accelerationsListDB = :accelerationsListDB, f.decelerationsListDB = :decelerationsListDB, "
			+ "f.shortTermVariationBpm = :shortTermVariationBpm, f.shortTermVariationMilli = :shortTermVariationMilli, f.longTermVariation = :longTermVariation, "
			+ "f.movementEntriesDB = :movementEntriesDB, f.autoFetalMovementDB = :autoFetalMovementDB, f.reportPath = :reportPath "
			+ "WHERE f.beneficiaryRegID = :beneficiaryRegID AND f.fetosenseID = :fetosenseID")
	public int updateFetosenseDetails (@Param("partnerName") String partnerName,@Param("fetosensePartnerID") String fetosensePartnerID,
			@Param("fetosenseMotherID") String fetosenseMotherID,@Param("testId") String testId,@Param("deviceId") String deviceId,
			@Param("testDoneAt") String testDoneAt,@Param("lengthOfTest") Integer lengthOfTest,@Param("basalHeartRate") Integer basalHeartRate,
			@Param("accelerationsListDB") String accelerationsListDB,@Param("decelerationsListDB") String decelerationsListDB,
			@Param("shortTermVariationBpm") String shortTermVariationBpm,@Param("shortTermVariationMilli") Integer shortTermVariationMilli,
			@Param("longTermVariation") Integer longTermVariation,@Param("movementEntriesDB") String movementEntriesDB,
			@Param("autoFetalMovementDB") String autoFetalMovementDB,@Param("reportPath") String reportPath,
			@Param("beneficiaryRegID") Long beneficiaryRegID,@Param("fetosenseID") Integer fetosenseID);
}
