package com.iemr.mmu.repo.masterrepo.doctor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.doctor.ItemMaster;

@Repository
public interface ItemMasterRepo extends CrudRepository<ItemMaster, Integer> {

}
