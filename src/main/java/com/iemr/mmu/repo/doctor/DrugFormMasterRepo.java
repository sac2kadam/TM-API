package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.DrugFormMaster;
@Repository
public interface DrugFormMasterRepo extends CrudRepository<DrugFormMaster, Integer> {
	
	@Query("SELECT drugFormID, drugForm FROM DrugFormMaster c where c.deleted != 1 order by drugForm")
	public  ArrayList<Object[]> getDrugFormMaster();
	
}
