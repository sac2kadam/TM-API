package com.iemr.mmu.service.report;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.login.UserParkingplaceMapping;
import com.iemr.mmu.data.report.ChiefComplaintReport;
import com.iemr.mmu.data.report.ConsultationReport;
import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.repo.login.UserParkingplaceMappingRepo;
import com.iemr.mmu.repo.report.BenChiefComplaintReportRepo;
import com.iemr.mmu.utils.exception.TMException;

@Service
public class CRMReportServiceImpl implements CRMReportService {

	@Autowired
	private BenChiefComplaintReportRepo benChiefComplaintReportRepo;

	@Autowired
	private UserParkingplaceMappingRepo userParkingplaceMappingRepo;

	Integer getParkingplaceID(Integer userid, Integer providerServiceMapId) throws TMException {
		UserParkingplaceMapping usermap = userParkingplaceMappingRepo
				.findOneByUserIDAndProviderServiceMapIdAndDeleted(userid, providerServiceMapId, 0);
		
		if(usermap==null || usermap.getParkingPlaceID()==null){
			throw new TMException("User Not mapped to any Parking Place");
		}
		return usermap.getParkingPlaceID();
	}

	static ChiefComplaintReport getBenChiefComplaintReportObj(Object[] obj) {
		ChiefComplaintReport report = new ChiefComplaintReport();
		report.setChiefComplaintID((Integer) obj[0]);
		report.setChiefComplaint((String) obj[1]);
		report.setMale((BigInteger) obj[7]);
		report.setFemale((BigInteger) obj[8]);
		report.setTransgender((BigInteger) obj[9]);
		report.setGrandTotal((BigInteger) obj[6]);

		return report;

	}

	static ConsultationReport getConsultationReportObj(Object[] obj) {
		ConsultationReport report = new ConsultationReport();
		report.setBeneficiaryRegID(obj[2].toString());
		report.setBeneficiaryName((String) obj[3]);
		report.setSpecialistName((String) obj[11]);
		report.setScheduledTime((Timestamp) obj[12]);
		if (obj[14] != null && ((String) obj[14]).equals("D")) {
			report.setConsulted("YES");
		} else {
			report.setConsulted("NO");
		}
		report.setArrivalTime((Timestamp) obj[16]);
		report.setConsultedTime((Timestamp) obj[17]);
		if (report.getConsultedTime() != null && report.getArrivalTime() != null) {
			Long waitingtime = report.getConsultedTime().getTime() - report.getArrivalTime().getTime();
			Long totalmin=waitingtime/(1000*60);
			if(totalmin<0){
				totalmin=0L;
			}
			Long min=totalmin%60;
			Long hour=totalmin/60;
			StringBuilder st=new StringBuilder();
			if(hour>1){
				st.append(hour);
				st.append(" hrs");
			}else if(hour==1){
				st.append(hour);
				st.append(" hr");
			}
			if(min>0){
				st.append(min);
				st.append(" mins");
			}
			report.setWaitingTime(st.toString());
		}

		return report;

	}

	static SpokeReport getSpokeReportObj(Object[] obj) {
		SpokeReport spoke = new SpokeReport();
		spoke.setVanID((Integer) obj[2]);
		spoke.setVanName((String) obj[3]);

		return spoke;

	}

	@Override
	public Set<SpokeReport> getChiefcomplaintreport(ReportInput input) throws TMException {
		// TODO Auto-generated method stub
		Integer ppid=getParkingplaceID(input.getUserID(),input.getProviderServiceMapID());
		List<Object[]> result = benChiefComplaintReportRepo.getcmreport(input.getFromDate(), input.getToDate(),
				ppid);

		HashMap<SpokeReport, List<ChiefComplaintReport>> hashmap = new HashMap();

		for (Object[] obj : result) {
			if (obj[0] != null && obj[1] != null && obj[2] != null && obj[3] != null) {
				SpokeReport spoke = getSpokeReportObj(obj);
				List<ChiefComplaintReport> listcc = new ArrayList<>();
				if (hashmap.containsKey(spoke)) {
					listcc = hashmap.get(spoke);
				}
				listcc.add(getBenChiefComplaintReportObj(obj));
				hashmap.put(spoke, listcc);
			}
		}
		for (Map.Entry<SpokeReport, List<ChiefComplaintReport>> entry : hashmap.entrySet()) {
			SpokeReport key = entry.getKey();
			List<ChiefComplaintReport> value = entry.getValue();
			key.setChiefComplaintReport(value);
		}

		return hashmap.keySet();
	}

	@Override
	public List<ConsultationReport> getConsultationReport(ReportInput input) throws TMException {
		Integer ppid=getParkingplaceID(input.getUserID(),input.getProviderServiceMapID());
		List<Object[]> objarr = benChiefComplaintReportRepo.getConsultationReport(input.getFromDate(),
				input.getToDate(), ppid);
		List<ConsultationReport> report = new ArrayList<>();
		for (Object[] obj : objarr) {
			report.add(getConsultationReportObj(obj));
		}

		return report;
	}

}
