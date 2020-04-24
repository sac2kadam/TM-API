package com.iemr.mmu.repo.location;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.CountryCityMaster;

@Repository
public interface CountryCityMasterRepo extends CrudRepository<CountryCityMaster, Integer> {
	ArrayList<CountryCityMaster> findByCountryIDAndDeleted(Integer countryID, Boolean deleted);
}
