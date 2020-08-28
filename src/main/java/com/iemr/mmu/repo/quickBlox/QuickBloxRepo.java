package com.iemr.mmu.repo.quickBlox;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.quickBlox.Quickblox;


@Repository
public interface QuickBloxRepo extends CrudRepository<Quickblox, Long> {
	@Query(" SELECT specialistQuickbloxID, specialistBenQuickbloxID "
			+ "from Quickblox ba WHERE ba.specialistUserID = :specialistUserID "
			+ " AND ba.deleted = false")
	public Quickblox getQuickbloxIds2(@Param("specialistUserID") Long specialistUserID
			);
	//List[] findAll(Long ids);

	//public List<Quickblox> findAll(Long specialistUserID);
	@Query("SELECT t FROM Quickblox t WHERE t.specialistUserID = :specialistUserID")
	Quickblox getQuickbloxIds(@Param("specialistUserID") Long specialistUserID);
}
