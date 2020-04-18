package com.iemr.mmu.repo.masterrepo.covid19;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.covid19.CovidRecommendationMaster;

@Repository
public interface CovidRecommnedationMasterRepo extends CrudRepository<CovidRecommendationMaster, Integer> {
	ArrayList<CovidRecommendationMaster> findByDeleted(Boolean deleted);
}
