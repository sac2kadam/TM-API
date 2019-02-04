package com.iemr.mmu.repo.nurse.anc;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.BenChildDevelopmentHistory;

public interface BenChildDevelopmentHistoryRepo extends CrudRepository<BenChildDevelopmentHistory, Long> {

	@Query("select Date(createdDate), grossMotorMilestone, isGMMAttained, fineMotorMilestone, isFMMAttained, "
			+ "socialMilestone, isSMAttained, languageMilestone, isLMAttained, developmentProblem "
			+ "from BenChildDevelopmentHistory a where a.beneficiaryRegID = :beneficiaryRegID "
			+ "AND deleted = false ORDER BY createdDate DESC ")
	public ArrayList<Object[]> getBenDevelopmentHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, grossMotorMilestone, isGMMAttained, fineMotorMilestone, "
			+ "isFMMAttained, socialMilestone, isSMAttained, languageMilestone, isLMAttained, developmentProblem, visitCode  "
			+ "from BenChildDevelopmentHistory a where a.beneficiaryRegID = :beneficiaryRegID and a.visitCode = :visitCode AND deleted=false")

	public ArrayList<Object[]> getBenDevelopmentDetails(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	
	@Query("SELECT processed from BenChildDevelopmentHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted = false")
	public String getDevelopmentHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update BenChildDevelopmentHistory set grossMotorMilestone=:grossMotorMilestone, isGMMAttained=:isGMMAttained, "
			+ "fineMotorMilestone=:fineMotorMilestone, isFMMAttained=:isFMMAttained,"
			+ " socialMilestone=:socialMilestone, isSMAttained=:isSMAttained, languageMilestone=:languageMilestone, "
			+ "isLMAttained=:isLMAttained, developmentProblem=:developmentProblem, "
			+ "  modifiedBy=:modifiedBy, processed=:processed where "
			+ "beneficiaryRegID=:beneficiaryRegID AND visitCode = :visitCode")
	public int updatePerinatalDetails(
			@Param("grossMotorMilestone") String grossMotorMilestone,
			@Param("isGMMAttained") Boolean isGMMAttained,
			@Param("fineMotorMilestone") String fineMotorMilestone,
			@Param("isFMMAttained") Boolean isFMMAttained,
			@Param("socialMilestone") String socialMilestone,
			@Param("isSMAttained") Boolean isSMAttained,
			@Param("languageMilestone") String languageMilestone,
			@Param("isLMAttained") Boolean isLMAttained,
			@Param("developmentProblem") String developmentProblem,
			@Param("modifiedBy") String modifiedBy,
			@Param("processed") String processed,
			@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);
	
}
