package com.iemr.mmu.data.tele_consultation;

public class SmsRequestOBJ {
	private Long beneficiaryRegID;
	private Integer smsTemplateID;
	private Integer specializationID;
	private String smsTypeTM;
	private String createdBy;
	private String tcDate;
	private String tcPreviousDate;

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Integer getSmsTemplateID() {
		return smsTemplateID;
	}

	public void setSmsTemplateID(Integer smsTemplateID) {
		this.smsTemplateID = smsTemplateID;
	}

	public Integer getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(Integer specializationID) {
		this.specializationID = specializationID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getTcDate() {
		return tcDate;
	}

	public void setTcDate(String tcDate) {
		this.tcDate = tcDate;
	}

	public String getTcPreviousDate() {
		return tcPreviousDate;
	}

	public void setTcPreviousDate(String tcPreviousDate) {
		this.tcPreviousDate = tcPreviousDate;
	}

	public String getSmsType() {
		return smsTypeTM;
	}

	public void setSmsType(String smsType) {
		this.smsTypeTM = smsType;
	}

}
