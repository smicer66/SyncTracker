package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.EmployeeContract;
import com.syncstate.apps.SyncTracker.models.EmployeeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface IEmployeeGroupRepository extends JpaRepository<EmployeeGroup, BigInteger> {

    @Query("Select tp from EmployeeGroup tp WHERE tp.deletedAt IS NULL AND tp.clientId = :clientId")
    List<EmployeeGroup> getEmployeeGroupByClientId(BigInteger clientId);

}
