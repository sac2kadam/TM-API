package com.iemr.mmu.repo.nurse.ncdscreening;

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
}
