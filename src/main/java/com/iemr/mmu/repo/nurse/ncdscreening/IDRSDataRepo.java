package com.iemr.mmu.repo.nurse.ncdscreening;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.IDRSData;
@Repository
public interface IDRSDataRepo extends CrudRepository<IDRSData, Long> {

}
