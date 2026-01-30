package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.Timesheet;
import com.syncstate.apps.SyncTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, BigInteger> {

    @Query("Select tp from Employee tp WHERE tp.deletedAt IS NULL AND tp.employeeId = :employeeId")
    Employee getEmployeeByEmployeeId(BigInteger employeeId);

    @Query(value = "Select tp from Employee tp WHERE tp.deletedAt IS NULL AND tp.userId = :userId AND tp.clientId = :clientId")
    Employee getEmployeeByUserIdAndClientId(BigInteger userId, BigInteger clientId);



}
