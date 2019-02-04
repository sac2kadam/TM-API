package com.iemr.mmu.repo.masterrepo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.registrar.IncomeStatusMaster;

@Repository
public interface IncomeStatusMasterRepo extends CrudRepository<IncomeStatusMaster, Short> {
	@Query(" select incomeStatusID, incomeStatus from IncomeStatusMaster where deleted = false order by incomeStatus ")
	public ArrayList<Object[]> getIncomeStatusMaster();
}
