package com.iemr.mmu.repo.tc_consultation;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.tele_consultation.TCRequestModel;

@Repository
public interface TCRequestModelRepo extends CrudRepository<TCRequestModel, Long> {
	@Transactional
	@Modifying
	@Query(" UPDATE TCRequestModel t SET t.status = :statusFlag, t.modifiedBy = :modifiedBy, t.deleted = :deleted "
			+ " WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('N', 'A', 'O') ")
	public int updateBeneficiaryStatus(@Param("benRegID") Long benRegID, @Param("visitCode") Long visitCode,
			@Param("statusFlag") String statusFlag, @Param("modifiedBy") String modifiedBy,
			@Param("userID") Integer userID, @Param("deleted") Boolean deleted);

	@Query(" SELECT t from TCRequestModel t WHERE t.beneficiaryRegID = :benRegID AND t.visitCode = :visitCode "
			+ " AND t.deleted is false AND t.userID = :userID AND t.status IN ('A', 'O') ")
	public ArrayList<TCRequestModel> checkBenTcStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("userID") Integer userID);

	@Query(" SELECT a FROM TCRequestModel a WHERE a.beneficiaryRegID = :benRegID AND a.visitCode = :visitCode "
			+ " AND a.userID = :userID AND a.deleted is false AND a.status IN :statusSet ")
	public ArrayList<TCRequestModel> getTcDetailsList(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode, @Param("userID") Integer userID,
			@Param("statusSet") Set<String> statusSet);

}
