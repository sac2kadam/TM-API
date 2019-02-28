package com.iemr.mmu.data.report;

import java.sql.Timestamp;

import javax.persistence.Transient;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ConsultationReport {

	@Expose
	private String beneficiaryRegID;
	@Expose
	private String beneficiaryName;
	
	@Expose
	private String specialistName;
	
	@Expose
	private Timestamp scheduledTime;
		
	@Expose
	private String consulted;
	
	@Expose
	private Timestamp arrivalTime;
	
	@Expose
	private Timestamp consultedTime;
	
	@Expose
	private String waitingTime;

	@Transient
	private static GsonBuilder builder;

	@Override
	public String toString() {
		if (builder == null) {
			builder = new GsonBuilder();
			builder.setDateFormat("dd-MM-yyyy h:mm a ");
			builder.excludeFieldsWithoutExposeAnnotation();
			builder.serializeNulls();
		}
		return builder.create().toJson(this);
	}
	
	

	public static GsonBuilder getBuilder() {
		return builder;
	}



	public static void setBuilder(GsonBuilder builder) {
		ConsultationReport.builder = builder;
	}



	public String getBeneficiaryRegID() {
		return beneficiaryRegID;
	}

	public void setBeneficiaryRegID(String beneficiaryRegID) {
		this.beneficiaryRegID = beneficiaryRegID;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getSpecialistName() {
		return specialistName;
	}

	public void setSpecialistName(String specialistName) {
		this.specialistName = specialistName;
	}

	public Timestamp getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(Timestamp scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getConsulted() {
		return consulted;
	}

	public void setConsulted(String consulted) {
		this.consulted = consulted;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Timestamp getConsultedTime() {
		return consultedTime;
	}

	public void setConsultedTime(Timestamp consultedTime) {
		this.consultedTime = consultedTime;
	}

	public String getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(String waitingTime) {
		this.waitingTime = waitingTime;
	}
	
	
}
