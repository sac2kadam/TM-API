package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.DevelopmentProblems;

@Repository
public interface DevelopmentProblemsRepo extends CrudRepository<DevelopmentProblems, Short>{
	
	@Query("select ID, developmentProblem, problemDesc from DevelopmentProblems where deleted = false order by developmentProblem")
	public ArrayList<Object[]> getDevelopmentProblems();
}
