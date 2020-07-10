package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.ServiceFacilityMaster;

@Repository
public interface ServiceFacilityMasterRepo extends CrudRepository<ServiceFacilityMaster, Integer> {

	public ArrayList<ServiceFacilityMaster> findByDeleted(Boolean deleted);
}
