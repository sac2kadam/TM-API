package com.iemr.mmu.repo.report;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.report.BenChiefComplaintReport;

@Repository
public interface BenChiefComplaintReportRepo extends CrudRepository<BenChiefComplaintReport, Long> {

	@Query(value = "call db_reporting.SP_ChiefComplaintReport(:startDate, :toDate,:ppID)", nativeQuery = true)
	List<Object[]> getcmreport(@Param("startDate")Date fromDate,@Param("toDate") Date toDate,@Param("ppID") Integer parkingPlaceID);


	
	
	

}
