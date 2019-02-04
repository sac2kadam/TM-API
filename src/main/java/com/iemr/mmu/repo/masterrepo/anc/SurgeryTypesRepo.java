package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.SurgeryTypes;

@Repository
public interface SurgeryTypesRepo extends CrudRepository<SurgeryTypes, Integer> {

	@Query(" SELECT surgeryID, surgeryType FROM SurgeryTypes WHERE deleted = false "
			+ " AND (visitCategoryID = :visitCategoryID OR visitCategoryID is null) "
			+ " AND (gender = 'unisex' or gender = :gender) ORDER BY surgeryType")
	public ArrayList<Object[]> getSurgeryTypes(@Param("visitCategoryID") Integer visitCategoryID,
			@Param("gender") String gender);
}
