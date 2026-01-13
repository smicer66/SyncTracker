package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.ScheduledWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IScheduledWorkRepository extends JpaRepository<ScheduledWork, BigInteger> {

    @Query("Select tp from ScheduledWork tp WHERE tp.deletedAt IS NULL AND tp.employeeId = :employeeId " +
            "AND tp.expectedStartTime >= :time AND tp.expectedEndTime <= :time " +
            "AND tp.approvedByEmployeeId = 1")
    ScheduledWork getScheduledWorkByEmployeeIdByTime(BigInteger employeeId, LocalDateTime time);
}
