package com.iemr.mmu.repo.nurse.ncdscreening;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.ncdScreening.PhysicalActivityType;

@Repository
public interface PhysicalActivityTypeRepo extends CrudRepository<PhysicalActivityType, Long> {

}
