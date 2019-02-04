package com.iemr.mmu.repo.doctor;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.iemr.mmu.data.doctor.TempMasterDrug;

public interface TempMasterDrugRepo extends CrudRepository<TempMasterDrug, Long> {
	ArrayList<TempMasterDrug> findByDeletedFalseOrderByDrugDisplayNameAsc();

}
