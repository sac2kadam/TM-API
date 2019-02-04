package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.OccupationMaster;

@Repository
public interface OccupationMasterRepo extends CrudRepository<OccupationMaster, Short> {
	@Query("select occupationID, occupationType from OccupationMaster where deleted = false order by occupationType ")
	public ArrayList<Object[]> getOccupationMaster();
}
