package com.iemr.mmu.repo.nurse.anc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iemr.mmu.data.anc.FemaleObstetricHistory;

@Repository
public interface FemaleObstetricHistoryRepo extends CrudRepository<FemaleObstetricHistory, Long> {

	@Query("select Date(createdDate), pregOrder, pregComplicationType, otherPregComplication, durationType, deliveryType, deliveryPlace, otherDeliveryPlace, "
			+ " deliveryComplicationType, otherDeliveryComplication, pregOutcome, postpartumComplicationType, otherPostpartumCompType, "
			+ " postNatalComplication, otherPostNatalComplication, congenitalAnomalies, newBornComplication, otherNewBornComplication from "
			+ "FemaleObstetricHistory a where a.beneficiaryRegID = :beneficiaryRegID AND pregOrder is not null AND deleted = false "
			+ "order by createdDate DESC")
	public ArrayList<Object[]> getBenFemaleObstetricHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID);

	@Query("select beneficiaryRegID, benVisitID, providerServiceMapID, pregOrder, totalNoOfPreg, pregComplicationID, pregComplicationType, otherPregComplication, pregDurationID, "
			+ "durationType, deliveryTypeID, deliveryType, deliveryPlaceID, deliveryPlace, otherDeliveryPlace, deliveryComplicationID, "
			+ "deliveryComplicationType, otherDeliveryComplication, pregOutcomeID, pregOutcome, postpartumComplicationID, "
			+ " postpartumComplicationType, otherPostpartumCompType, postNatalComplicationID, postNatalComplication, otherPostNatalComplication,"
			+ " congenitalAnomalies, newBornComplicationID, newBornComplication, otherNewBornComplication, visitCode, "
			+ " abortionTypeID, postAbortionComplication_db, pregDuration, typeofFacilityID, "
			+ " postAbortionComplicationsValues,typeOfAbortionValue, serviceFacilityValue FROM "
			+ "FemaleObstetricHistory a where a.beneficiaryRegID = :beneficiaryRegID and deleted = false AND visitCode = :visitCode")
	public ArrayList<Object[]> getBenFemaleObstetricHistoryDetail(@Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("visitCode") Long visitCode);

	@Modifying
	@Transactional
	@Query(" Update FemaleObstetricHistory  set deleted=true, processed=:processed WHERE obstetricHistoryID = :obstetricHistoryID")
	public int deleteExistingObstetricHistory(@Param("obstetricHistoryID") Long obstetricHistoryID,
			@Param("processed") String processed);

	@Query("SELECT obstetricHistoryID, processed from FemaleObstetricHistory where beneficiaryRegID=:benRegID AND visitCode = :visitCode AND deleted=false")
	public ArrayList<Object[]> getBenObstetricHistoryStatus(@Param("benRegID") Long benRegID,
			@Param("visitCode") Long visitCode);

	@Transactional
	@Modifying
	@Query("update FemaleObstetricHistory set pregOrder=:pregOrder, pregComplicationID=:pregComplicationID, pregComplicationType=:pregComplicationType, otherPregComplication=:otherPregComplication,"
			+ " pregDurationID=:pregDurationID, durationType=:durationType, deliveryTypeID=:deliveryTypeID, deliveryType=:deliveryType,"
			+ " deliveryPlaceID=:deliveryPlaceID, deliveryPlace=:deliveryPlace, otherDeliveryPlace=:otherDeliveryPlace, pregOutcomeID=:pregOutcomeID, pregOutcome=:pregOutcome,"
			+ " deliveryComplicationID=:deliveryComplicationID, deliveryComplicationType=:deliveryComplicationType, otherDeliveryComplication=:otherDeliveryComplication,"
			+ " postpartumComplicationID=:postpartumComplicationID, postpartumComplicationType=:postpartumComplicationType, otherPostpartumCompType=:otherPostpartumCompType,"
			+ " postNatalComplicationID=:postNatalComplicationID, postNatalComplication=:postNatalComplication, otherPostNatalComplication=:otherPostNatalComplication,"
			+ " newBornComplicationID=:newBornComplicationID, newBornComplication=:newBornComplication, otherNewBornComplication=:otherNewBornComplication,"
			+ " congenitalAnomalies=:congenitalAnomalies,modifiedBy=:modifiedBy where beneficiaryRegID=:beneficiaryRegID AND benVisitID = :benVisitID")
	public int updatePastObstetricHistory(@Param("pregOrder") Short pregOrder,
			@Param("pregComplicationID") Short pregComplicationID,
			@Param("pregComplicationType") String pregComplicationType,
			@Param("otherPregComplication") String otherPregComplication,

			@Param("pregDurationID") Short pregDurationID, @Param("durationType") String durationType,

			@Param("deliveryTypeID") Short deliveryTypeID, @Param("deliveryType") String deliveryType,

			@Param("deliveryPlaceID") Short deliveryPlaceID, @Param("deliveryPlace") String deliveryPlace,
			@Param("otherDeliveryPlace") String otherDeliveryPlace,

			@Param("deliveryComplicationID") Short deliveryComplicationID,
			@Param("deliveryComplicationType") String deliveryComplicationType,
			@Param("otherDeliveryComplication") String otherDeliveryComplication,

			@Param("pregOutcomeID") Short pregOutcomeID, @Param("pregOutcome") String pregOutcome,

			@Param("postpartumComplicationID") Short postpartumComplicationID,
			@Param("postpartumComplicationType") String postpartumComplicationType,
			@Param("otherPostpartumCompType") String otherPostpartumCompType,

			@Param("postNatalComplicationID") Short postNatalComplicationID,
			@Param("postNatalComplication") String postNatalComplication,
			@Param("otherPostNatalComplication") String otherPostNatalComplication,

			@Param("newBornComplicationID") Short newBornComplicationID,
			@Param("newBornComplication") String newBornComplication,
			@Param("otherNewBornComplication") String otherNewBornComplication,

			@Param("congenitalAnomalies") String congenitalAnomalies,

			@Param("modifiedBy") String modifiedBy, @Param("beneficiaryRegID") Long beneficiaryRegID,
			@Param("benVisitID") Long benVisitID);

	@Query("SELECT f FROM FemaleObstetricHistory f WHERE f.beneficiaryRegID=:benRegID AND "
			+ " ((f.pregOutcomeID IN :ids) OR (f.pregOutcomeID = 4 AND f.pregDurationID = 1)) "
			+ " AND f.pregComplicationType like %:pregComp% AND f.deleted is false ")
	public ArrayList<FemaleObstetricHistory> getPastObestetricDataForHRP(@Param("benRegID") Long benRegID,
			@Param("pregComp") String pregComp, @Param("ids") List<Short> ids);

}
