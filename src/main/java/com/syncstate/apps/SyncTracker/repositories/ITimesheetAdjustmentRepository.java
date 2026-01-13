package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Timesheet;
import com.syncstate.apps.SyncTracker.models.TimesheetAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface ITimesheetAdjustmentRepository extends JpaRepository<TimesheetAdjustment, BigInteger> {

}
