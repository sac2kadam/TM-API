package com.iemr.mmu.repo.doctor;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.doctor.DocWorkList;

@Repository
public interface DocWorkListRepo extends CrudRepository<DocWorkList, Long> {
	@Query("SELECT DISTINCT beneficiaryRegID, beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, benVisitID, visitNo, visitFlowStatusFlag, "
			+ " visitCategory, fatherName, districtName, villageName, phoneNo, DATE_FORMAT(visitCreatedDate, '%d-%m-%Y')  from DocWorkList  "
			+ " where (visitFlowStatusFlag  = 'N') or  (visitFlowStatusFlag  = 'D' and Date(visitCreatedDate) = curdate()) ")
	public List<Object[]> getDocWorkList();
	
//	@Query("SELECT DISTINCT beneficiaryRegID, beneficiaryID, "
//			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
//			+ " Date(dob), genderID, genderName, benVisitID, visitNo, visitFlowStatusFlag, "
//			+ " visitCategory, fatherName, districtName, villageName, phoneNo, DATE_FORMAT(visitCreatedDate, '%d-%m-%Y')  from DocWorkList  "
//			+ " where visitFlowStatusFlag in('D') and Date(visitCreatedDate) = curdate() and visitCategory = 'Cancer Screening' ")
	@Query("SELECT DISTINCT beneficiaryRegID, beneficiaryID, "
			+ " UPPER( concat(IFNULL(firstName, ''), ' ',IFNULL(lastName,''))) as benName, "
			+ " Date(dob), genderID, genderName, benVisitID, visitNo, visitFlowStatusFlag, "
			+ " visitCategory, fatherName, districtName, villageName, phoneNo, DATE_FORMAT(visitCreatedDate, '%d-%m-%Y')  from DocWorkList  "
			+ " where visitFlowStatusFlag in('D', 'Z')  and visitCategory = 'Cancer Screening' ")
	public List<Object[]> getRadiologistWorkList();
}
