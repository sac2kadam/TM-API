/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.tm.service.generalOPD;

import java.text.ParseException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.iemr.tm.data.nurse.CommonUtilityClass;

public interface GeneralOPDService {

	String saveNurseData(JsonObject requestOBJ, String Authorization) throws Exception;
	
	public void deleteVisitDetails(JsonObject requestOBJ) throws Exception;

	Map<String, Long> saveBenVisitDetails(JsonObject visitDetailsOBJ, CommonUtilityClass nurseUtilityClass)
			throws Exception;

	Long saveDoctorData(JsonObject requestOBJ, String Authorization) throws Exception;

	Long saveBenGeneralOPDHistoryDetails(JsonObject generalOPDHistoryOBJ, Long benVisitID, Long benVisitCode)
			throws Exception;

	Long saveBenVitalDetails(JsonObject vitalDetailsOBJ, Long benVisitID, Long benVisitCode) throws Exception;

	Long saveBenExaminationDetails(JsonObject examinationDetailsOBJ, Long benVisitID, Long benVisitCode)
			throws Exception;

	String getBenVisitDetailsFrmNurseGOPD(Long benRegID, Long visitCode);

	String getBenHistoryDetails(Long benRegID, Long visitCode);

	String getBeneficiaryVitalDetails(Long benRegID, Long visitCode);

	String getExaminationDetailsData(Long benRegID, Long visitCode);

	String getBenCaseRecordFromDoctorGeneralOPD(Long benRegID, Long visitCode) throws JsonProcessingException, ParseException;

	int UpdateVisitDetails(JsonObject jsnOBJ) throws Exception;

	int updateBenHistoryDetails(JsonObject jsnOBJ)throws Exception;

	int updateBenVitalDetails(JsonObject jsnOBJ ) throws Exception;

	int updateBenExaminationDetails(JsonObject jsnOBJ) throws Exception;

	Long updateGeneralOPDDoctorData(JsonObject jsnOBJ, String authorization) throws Exception;

}
