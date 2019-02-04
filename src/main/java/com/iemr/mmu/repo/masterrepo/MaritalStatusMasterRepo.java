package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.MaritalStatusMaster;

@Repository
public interface MaritalStatusMasterRepo extends CrudRepository<MaritalStatusMaster, Short> {
	@Query(" select maritalStatusID, status from MaritalStatusMaster where deleted = false order by status ")
	public ArrayList<Object[]> getMaritalStatusMaster();
}
