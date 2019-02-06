package com.iemr.mmu.repo.provider;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iemr.mmu.data.provider.ProviderServiceMapping;

public interface ProviderServiceMappingRepo extends CrudRepository<ProviderServiceMapping, Integer> {
	@Query(" SELECT t.providerServiceMapID FROM ProviderServiceMapping t "
			+ " WHERE t.serviceID = :serviceID AND deleted is false ")
	List<Integer> getProviderServiceMapIdForServiceID(@Param("serviceID") Short serviceID);
}
