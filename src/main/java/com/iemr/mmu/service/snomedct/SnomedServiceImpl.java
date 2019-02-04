package com.iemr.mmu.service.snomedct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iemr.mmu.data.snomedct.SCTDescription;
import com.iemr.mmu.repo.snomedct.SnomedRepository;

@Service
public class SnomedServiceImpl implements SnomedService {

	private SnomedRepository snomedRepository;

	@Autowired
	public void setSnomedRepository(SnomedRepository snomedRepository) {
		this.snomedRepository = snomedRepository;
	}

	@Override
	public SCTDescription findSnomedCTRecordFromTerm(String term) {

		List<Object[]> records = snomedRepository.findSnomedCTRecordFromTerm(term);
		SCTDescription obj = SCTDescription.getSnomedCTOBJ(records);
		return obj;

	}

}
