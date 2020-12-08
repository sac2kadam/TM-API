package com.iemr.mmu.repo.masterrepo.ncdScreening;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.ncdscreening.PhysicalActivity;

@Repository
public interface PhysicalActivityRepo extends CrudRepository<PhysicalActivity, Integer> {

	ArrayList<PhysicalActivity> findByDeleted(Boolean deleted);
}
