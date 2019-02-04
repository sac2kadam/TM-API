package com.iemr.mmu.data.masterdata.doctor;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "m_item")
public class ItemMaster {
	@Id
	@GeneratedValue
	@Expose
	@Column(name = "ItemID")
	private Integer itemID;
	@Expose
	@Column(name = "ItemName")
	private String itemName;
	@Expose
	@Column(name = "ItemDesc")
	private String itemDesc;
	@Expose
	@Column(name = "ItemCode")
	private String itemCode;
	@Expose
	@Column(name = "ItemCategoryID")
	private Integer itemCategoryID;
	@Expose
	@Column(name = "IsMedical")
	private Boolean isMedical;
	@Expose
	@Column(name = "ItemFormID")
	private Integer itemFormID;
	@Expose
	@Column(name = "PharmacologyCategoryID")
	private Integer pharmacologyCategoryID;
	@Expose
	@Column(name = "ManufacturerID")
	private Integer manufacturerID;
	@Expose
	@Column(name = "Strength")
	private String strength;
	@Expose
	@Column(name = "UOMID")
	private Integer uOMID;
	@Expose
	@Column(name = "IsScheduledDrug")
	private Boolean isScheduledDrug;
	@Expose
	@Column(name = "Composition")
	private String composition;
	@Expose
	@Column(name = "ExpiryDate")
	private Timestamp expiryDate;
	@Expose
	@Column(name = "Max_Stock")
	private Integer max_Stock;
	@Expose
	@Column(name = "Min_Stock")
	private Integer min_Stock;
	@Expose
	@Column(name = "RouteID")
	private Integer routeID;
	@Expose
	@Column(name = "ProviderServiceMapID")
	private Integer providerServiceMapID;
	@Expose
	@Column(name = "Status")
	private String status;
	@Expose
	@Column(name = "Discontinued")
	private Boolean discontinued;
	@Expose
	@Column(name = "Deleted")
	private Boolean deleted;
	@Expose
	@Column(name = "Processed")
	private String processed;
	@Expose
	@Column(name = "CreatedBy")
	private String createdBy;
	@Expose
	@Column(name = "CreatedDate")
	private Timestamp createdDate;
	@Expose
	@Column(name = "ModifiedBy")
	private String modifiedBy;
	@Expose
	@Column(name = "LastModDate")
	private Timestamp lastModDate;

	public Integer getItemID() {
		return itemID;
	}

	public void setItemID(Integer itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Integer getItemCategoryID() {
		return itemCategoryID;
	}

	public void setItemCategoryID(Integer itemCategoryID) {
		this.itemCategoryID = itemCategoryID;
	}

	public Boolean getIsMedical() {
		return isMedical;
	}

	public void setIsMedical(Boolean isMedical) {
		this.isMedical = isMedical;
	}

	public Integer getItemFormID() {
		return itemFormID;
	}

	public void setItemFormID(Integer itemFormID) {
		this.itemFormID = itemFormID;
	}

	public Integer getPharmacologyCategoryID() {
		return pharmacologyCategoryID;
	}

	public void setPharmacologyCategoryID(Integer pharmacologyCategoryID) {
		this.pharmacologyCategoryID = pharmacologyCategoryID;
	}

	public Integer getManufacturerID() {
		return manufacturerID;
	}

	public void setManufacturerID(Integer manufacturerID) {
		this.manufacturerID = manufacturerID;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public Integer getuOMID() {
		return uOMID;
	}

	public void setuOMID(Integer uOMID) {
		this.uOMID = uOMID;
	}

	public Boolean getIsScheduledDrug() {
		return isScheduledDrug;
	}

	public void setIsScheduledDrug(Boolean isScheduledDrug) {
		this.isScheduledDrug = isScheduledDrug;
	}

	public String getComposition() {
		return composition;
	}

	public void setComposition(String composition) {
		this.composition = composition;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getMax_Stock() {
		return max_Stock;
	}

	public void setMax_Stock(Integer max_Stock) {
		this.max_Stock = max_Stock;
	}

	public Integer getMin_Stock() {
		return min_Stock;
	}

	public void setMin_Stock(Integer min_Stock) {
		this.min_Stock = min_Stock;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getProviderServiceMapID() {
		return providerServiceMapID;
	}

	public void setProviderServiceMapID(Integer providerServiceMapID) {
		this.providerServiceMapID = providerServiceMapID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Boolean discontinued) {
		this.discontinued = discontinued;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getLastModDate() {
		return lastModDate;
	}

	public void setLastModDate(Timestamp lastModDate) {
		this.lastModDate = lastModDate;
	}

}
