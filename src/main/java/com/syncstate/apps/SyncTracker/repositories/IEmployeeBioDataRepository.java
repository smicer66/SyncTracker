package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.EmployeeBioData;
import com.syncstate.apps.SyncTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface IEmployeeBioDataRepository extends JpaRepository<EmployeeBioData, BigInteger> {

    @Query("Select tp from EmployeeBioData tp WHERE tp.deletedAt IS NULL AND tp.employeeBioDataId = :employeeBioDataId")
    EmployeeBioData getEmployeeBioDataByEmployeeBioId(BigInteger employeeBioDataId);

    @Query(value = "Select tp from employee_bio_data tp WHERE tp.deletedAt IS NULL AND tp.employeeId = :employeeId " +
            "AND tp.clientId = :clientId", nativeQuery = true)
    EmployeeBioData getEmployeeBioDetailsByEmployeeId(BigInteger employeeId, BigInteger clientId);

    @Query(value = "Select tp from EmployeeBioData tp WHERE tp.deletedAt IS NULL AND tp.clientId = :clientId")
    Collection<EmployeeBioData> getEmployeeBioDataByClientId(BigInteger clientId);
}
