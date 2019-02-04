package com.iemr.mmu.repo.masterrepo.anc;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.masterdata.anc.DeliveryPlace;

@Repository
public interface DeliveryPlaceRepo extends CrudRepository<DeliveryPlace, Short>{
	
	@Query("select deliveryPlaceID, deliveryPlace from DeliveryPlace where deleted = false order by deliveryPlace")
	public ArrayList<Object[]> getDeliveryPlaces();
}
