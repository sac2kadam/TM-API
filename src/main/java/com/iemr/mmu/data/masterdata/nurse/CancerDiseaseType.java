package com.iemr.mmu.data.masterdata.nurse;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_cancerdiseasetype")
public class CancerDiseaseType {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "CancerDiseaseTypeID")
	private Short cancerDiseaseTypeID;
	
	@Expose
	@Column(name = "CancerDiseaseType")
	private String cancerDiseaseType;
	
	@Expose
	@Column(name = "CancerDiseaseTypeDesc")
	private String cancerDiseaseTypeDesc;
	
	@Expose
	@Column(name = "Gender")
	private String gender;
	
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;

	public CancerDiseaseType(Short cancerDiseaseTypeID, String cancerDiseaseType, String gender) {
		super();
		this.cancerDiseaseTypeID = cancerDiseaseTypeID;
		this.cancerDiseaseType = cancerDiseaseType;
		this.gender = gender;
	}
	
	public static ArrayList<CancerDiseaseType> getCancerDiseaseTypeMasterData(ArrayList<Object[]> resList) {
		ArrayList<CancerDiseaseType> resArray = new ArrayList<>();
		for (Object[] obj : resList) {
			CancerDiseaseType cOBJ = new CancerDiseaseType((Short) obj[0], (String) obj[1],  (String) obj[2]);
			resArray.add(cOBJ);
		}
		return resArray;
	}

	
}
