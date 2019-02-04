package com.iemr.mmu.data.labtechnician;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "v_benprocedurecomponentdetails")
public class V_benLabTestOrderedDetails {
	@Id
	private String id;
	@Expose
	@Column(name = "BeneficiaryRegID")
	private Long beneficiaryRegID;
	@Expose
	@Column(name = "BenVisitID")
	private Long benVisitID;
	@Expose
	@Column(name = "VisitCode")
	private Long visitCode;
	@Expose
	@Column(name = "PrescriptionID")
	private Long prescriptionID;
	@Expose
	@Column(name = "ProcedureID")
	private Integer procedureID;
	@Expose
	@Column(name = "ProcedureName")
	private String procedureName;
	@Expose
	@Column(name = "ProcedureDesc")
	private String procedureDesc;
	@Expose
	@Column(name = "ProcedureType")
	private String procedureType;
	@Expose
	@Column(name = "TestComponentID")
	private Integer testComponentID;
	@Expose
	@Column(name = "TestComponentName")
	private String testComponentName;
	@Expose
	@Column(name = "TestComponentDesc")
	private String testComponentDesc;
	@Expose
	@Column(name = "InputType")
	private String inputType;
	@Expose
	@Column(name = "MeasurementUnit")
	private String measurementUnit;
	@Expose
	@Column(name = "Range_min")
	private Integer range_min;
	@Expose
	@Column(name = "Range_max")
	private Integer range_max;
	@Expose
	@Column(name = "Range_normal_min")
	private Integer range_normal_min;
	@Expose
	@Column(name = "Range_normal_max")
	private Integer range_normal_max;
	@Expose
	@Column(name = "ResultValue")
	private String resultValue;

	@Expose
	@Column(name = "isDecimal")
	private Boolean isDecimal;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsDecimal() {
		return isDecimal;
	}

	public void setIsDecimal(Boolean isDecimal) {
		this.isDecimal = isDecimal;
	}

	public Long getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(Long beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public Long getBenVisitID() {
		return benVisitID;
	}

	public void setBenVisitID(Long benVisitID) {
		this.benVisitID = benVisitID;
	}

	public Long getPrescriptionID() {
		return prescriptionID;
	}

	public void setPrescriptionID(Long prescriptionID) {
		this.prescriptionID = prescriptionID;
	}

	public Integer getProcedureID() {
		return procedureID;
	}

	public void setProcedureID(Integer procedureID) {
		this.procedureID = procedureID;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureDesc() {
		return procedureDesc;
	}

	public void setProcedureDesc(String procedureDesc) {
		this.procedureDesc = procedureDesc;
	}

	public String getProcedureType() {
		return procedureType;
	}

	public void setProcedureType(String procedureType) {
		this.procedureType = procedureType;
	}

	public Integer getTestComponentID() {
		return testComponentID;
	}

	public void setTestComponentID(Integer testComponentID) {
		this.testComponentID = testComponentID;
	}

	public String getTestComponentName() {
		return testComponentName;
	}

	public void setTestComponentName(String testComponentName) {
		this.testComponentName = testComponentName;
	}

	public String getTestComponentDesc() {
		return testComponentDesc;
	}

	public void setTestComponentDesc(String testComponentDesc) {
		this.testComponentDesc = testComponentDesc;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getMeasurementUnit() {
		return measurementUnit;
	}

	public void setMeasurementUnit(String measurementUnit) {
		this.measurementUnit = measurementUnit;
	}

	public Integer getRange_min() {
		return range_min;
	}

	public void setRange_min(Integer range_min) {
		this.range_min = range_min;
	}

	public Integer getRange_max() {
		return range_max;
	}

	public void setRange_max(Integer range_max) {
		this.range_max = range_max;
	}

	public Integer getRange_normal_min() {
		return range_normal_min;
	}

	public void setRange_normal_min(Integer range_normal_min) {
		this.range_normal_min = range_normal_min;
	}

	public Integer getRange_normal_max() {
		return range_normal_max;
	}

	public void setRange_normal_max(Integer range_normal_max) {
		this.range_normal_max = range_normal_max;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public Long getVisitCode() {
		return visitCode;
	}

	public void setVisitCode(Long visitCode) {
		this.visitCode = visitCode;
	}

}
