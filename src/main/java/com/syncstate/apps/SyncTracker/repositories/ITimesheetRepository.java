package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Timesheet;
import com.syncstate.apps.SyncTracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface ITimesheetRepository extends JpaRepository<Timesheet, BigInteger> {

    @Query("Select tp from Timesheet tp WHERE tp.deletedAt IS NULL AND tp.employeeId = :employeeId " +
            "AND tp.clientId = :clientId")
    Collection<Timesheet> getTimesheetByEmployeeId(BigInteger employeeId, BigInteger clientId);
}
