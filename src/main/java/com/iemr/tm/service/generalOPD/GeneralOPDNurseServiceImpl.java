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

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iemr.tm.data.anc.BenChildDevelopmentHistory;
import com.iemr.tm.data.anc.ChildFeedingDetails;
import com.iemr.tm.data.anc.FemaleObstetricHistory;
import com.iemr.tm.data.anc.PerinatalHistory;
import com.iemr.tm.data.anc.SysGastrointestinalExamination;
import com.iemr.tm.data.anc.WrapperFemaleObstetricHistory;
import com.iemr.tm.data.anc.WrapperImmunizationHistory;
import com.iemr.tm.data.quickConsultation.BenChiefComplaint;
import com.iemr.tm.repo.nurse.anc.BenChildDevelopmentHistoryRepo;
import com.iemr.tm.repo.nurse.anc.ChildFeedingDetailsRepo;
import com.iemr.tm.repo.nurse.anc.PerinatalHistoryRepo;
import com.iemr.tm.repo.nurse.anc.SysGastrointestinalExaminationRepo;

@Service
public class GeneralOPDNurseServiceImpl implements GeneralOPDNurseService {
	

}
