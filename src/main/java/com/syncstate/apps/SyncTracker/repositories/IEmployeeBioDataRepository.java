package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.EmployeeBioData;
import com.syncstate.apps.SyncTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface IEmployeeBioDataRepository extends JpaRepository<EmployeeBioData, BigInteger> {

    @Query("Select tp from EmployeeBioData tp WHERE tp.deletedAt IS NULL AND tp.employeeBioDataId = :employeeBioDataId")
    EmployeeBioData getEmployeeBioDataByEmployeeId(BigInteger employeeBioDataId);
}
