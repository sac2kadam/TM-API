package com.iemr.mmu.repo.snomedct;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.snomedct.SCTDescription;

@Repository
public interface SnomedRepository extends CrudRepository<SCTDescription, Long> {

	/* @Query("select u from SCTDescription u where u.term like %:term%") */
	@Query("SELECT s.conceptID,s.term, s.caseSignificanceID "
			+ " FROM SCTDescription s WHERE s.term =:term and s.active = '1'")
	public List<Object[]> findSnomedCTRecordFromTerm(@Param("term") String term);

}
