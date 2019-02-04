package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.Musculoskeletal;

@Repository
public interface MusculoskeletalRepo extends CrudRepository<Musculoskeletal, Short>{
	
	@Query("select ID, type, value from Musculoskeletal where type=:type and deleted = false order by value")
	public ArrayList<Object[]> getMusculoskeletalvalues(@Param("type") String type);
}
