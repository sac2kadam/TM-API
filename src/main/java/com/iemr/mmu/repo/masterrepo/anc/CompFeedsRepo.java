package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.CompFeeds;

@Repository
public interface CompFeedsRepo extends CrudRepository<CompFeeds, Integer>{

	@Query("select feedID, type, value from CompFeeds where deleted = false and type=:type order by value")
	public ArrayList<Object[]> getCompFeeds(@Param("type") String type);
	
}
