package com.iemr.mmu.repo.syncActivity_syncLayer;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iemr.mmu.data.syncActivity_syncLayer.DataSyncGroups;

@Repository
public interface DataSyncGroupsRepo extends CrudRepository<DataSyncGroups, Integer> {
	public ArrayList<DataSyncGroups> findByDeleted(Boolean deleted);
}
