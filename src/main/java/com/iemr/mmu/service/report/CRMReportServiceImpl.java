package com.iemr.mmu.service.report;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.report.ChiefComplaintReport;
import com.iemr.mmu.data.report.ReportInput;
import com.iemr.mmu.data.report.SpokeReport;
import com.iemr.mmu.repo.report.BenChiefComplaintReportRepo;

@Service
public class CRMReportServiceImpl implements CRMReportService {

	@Autowired
	private BenChiefComplaintReportRepo benChiefComplaintReportRepo;

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

	static SpokeReport getSpokeReportObj(Object[] obj) {
		SpokeReport spoke = new SpokeReport();
		spoke.setVanID((Integer) obj[2]);
		spoke.setVanName((String) obj[3]);

		return spoke;

	}

	@Override
	public Set<SpokeReport> getChiefcomplaintreport(ReportInput input) {
		// TODO Auto-generated method stub

		List<Object[]> result = benChiefComplaintReportRepo.getcmreport(input.getFromDate(), input.getToDate(),
				input.getParkingPlaceID());

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

}
