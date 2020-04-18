package com.iemr.mmu.repo.nurse.covid19;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.covid19.Covid19BenFeedback;

@Repository
public interface Covid19BenFeedbackRepo extends CrudRepository<Covid19BenFeedback, Long> {

}
