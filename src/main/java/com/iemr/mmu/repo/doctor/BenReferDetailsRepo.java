package com.iemr.mmu.repo.doctor;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iemr.mmu.data.doctor.BenReferDetails;

public interface BenReferDetailsRepo extends CrudRepository<BenReferDetails, Long> {

	@Query(" SELECT benReferID, beneficiaryRegID, benVisitID, providerServiceMapID, referredToInstituteID, "
			+ "referredToInstituteName, serviceID, serviceName, visitCode, revisitDate "
			+ "from BenReferDetails ba WHERE ba.beneficiaryRegID = :benRegID AND ba.visitCode = :visitCode AND ba.deleted = false")
	public ArrayList<Object[]> getBenReferDetails(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);

	/*
	 * @Query("SELECT processed from BenReferDetails where beneficiaryRegID=:benRegID AND benVisitID = :benVisitID"
	 * ) public String getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
	 * 
	 * @Param("benVisitID") Long benVisitID);
	 */

	@Query("SELECT benReferID, processed from BenReferDetails where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenReferDetailsStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update BenReferDetails  set referredToInstituteID=:referredToInstituteID, "
			+ "referredToInstituteName=:referredToInstituteName,revisitDate=:revisitDate, processed=:processed "
			+ "WHERE benReferID =:benReferID")
	public int updateReferredInstituteName(@Param("referredToInstituteID") Integer referredToInstituteID,
			@Param("referredToInstituteName") String referredToInstituteName,@Param("revisitDate") Timestamp revisitDate, 
			@Param("benReferID") Long benReferID,
			@Param("processed") String processed);
}
