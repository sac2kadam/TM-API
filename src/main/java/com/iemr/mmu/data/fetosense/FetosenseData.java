package com.iemr.mmu.data.fetosense;

import java.sql.Timestamp;

public class FetosenseData {

	private Long partnerFetosenseID;
	private Long beneficiaryRegID;
	private Timestamp motherLMPDate;
	private String motherName;
	private String testName;
	
	
	
	
	public Long getPartnerFetosenseID() {
		return partnerFetosenseID;
	}
	public void setPartnerFetosenseID(Long partnerFetosenseID) {
		this.partnerFetosenseID = partnerFetosenseID;
	}
	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}
	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}
	public Timestamp getMotherLMPDate() {
		return motherLMPDate;
	}
	public void setMotherLMPDate(Timestamp motherLMPDate) {
		this.motherLMPDate = motherLMPDate;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}

}
