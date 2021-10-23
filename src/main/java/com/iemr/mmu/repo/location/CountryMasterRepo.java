package com.iemr.mmu.repo.location;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.location.Country;

@Repository
public interface CountryMasterRepo extends CrudRepository<Country, Integer> {
	
	@Query("select c from Country c where deleted = false order by countryName ")
	public ArrayList<Country> findAllCountries();
}
