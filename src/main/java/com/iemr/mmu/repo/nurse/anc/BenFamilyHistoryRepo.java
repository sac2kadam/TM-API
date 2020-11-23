package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.anc.BenFamilyHistory;

@Repository
public interface BenFamilyHistoryRepo extends CrudRepository<BenFamilyHistory, Long>{
	
	@Query("select Date(createdDate), familyMember, diseaseType, otherDiseaseType, isGeneticDisorder, geneticDisorder, isConsanguineousMarrige "
			+ "from BenFamilyHistory a where a.beneficiaryRegID = :beneficiaryRegID  AND deleted = false "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenFamilyHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);
	
	@Query(" SELECT beneficiaryRegID, benVisitID, providerServiceMapID, familyMember, diseaseTypeID, diseaseType, otherDiseaseType, "
			+ "isGeneticDisorder, geneticDisorder, isConsanguineousMarrige, visitCode, snomedCode, snomedTerm  FROM BenFamilyHistory "
			+ " WHERE beneficiaryRegID = :benRegID AND deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenFamilyHistoryDetail(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode);
	
	
	@Modifying
	@Transactional
	@Query(" Update BenFamilyHistory set deleted=true, processed=:processed WHERE ID = :ID")
		public int deleteExistingBenFamilyHistory(@Param("ID") Long ID, @Param("processed") String processed);
	
	@Query("SELECT ID, processed from BenFamilyHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenFamilyHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);
	
}
