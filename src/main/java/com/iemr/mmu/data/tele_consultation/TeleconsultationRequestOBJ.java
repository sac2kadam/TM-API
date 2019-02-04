package com.iemr.mmu.data.tele_consultation;

import java.sql.Timestamp;

public class TeleconsultationRequestOBJ {
	private Integer userID;
	private Timestamp allocationDate;
	private String fromTime;
	private String toTime;
	private Integer specializationID;

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Timestamp getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Timestamp allocationDate) {
		this.allocationDate = allocationDate;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public Integer getSpecializationID() {
		return specializationID;
	}

	public void setSpecializationID(Integer specializationID) {
		this.specializationID = specializationID;
	}

}
