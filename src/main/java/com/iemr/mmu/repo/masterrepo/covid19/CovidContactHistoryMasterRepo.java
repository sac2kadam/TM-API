package com.iemr.mmu.repo.masterrepo.covid19;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.covid19.CovidContactHistoryMaster;

@Repository
public interface CovidContactHistoryMasterRepo extends CrudRepository<CovidContactHistoryMaster, Integer> {
	ArrayList<CovidContactHistoryMaster> findByDeleted(Boolean deleted);
}
