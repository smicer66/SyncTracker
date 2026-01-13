package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Timesheet;
import com.syncstate.apps.SyncTracker.models.TimesheetNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface ITimesheetNoteRepository extends JpaRepository<TimesheetNote, BigInteger> {

    @Query("Select tp from TimesheetNote tp WHERE tp.deletedAt IS NULL AND tp.timesheetNoteId = :timesheetNoteId")
    TimesheetNote getTimesheetByTimesheetNoteId(BigInteger timesheetNoteId);
}
