package com.iemr.mmu.repo.bmiCalculation;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iemr.mmu.data.bmi.BmiCalculation;

public interface BMICalculationRepo extends CrudRepository<BmiCalculation, Long> {
	@Query(" SELECT ba "
			+ "from BmiCalculation ba WHERE ba.month = :month AND ba.gender = :gender AND ba.deleted = false")
	public BmiCalculation getBMIDetails(@Param("month") Integer month, @Param("gender") String gender);

}
