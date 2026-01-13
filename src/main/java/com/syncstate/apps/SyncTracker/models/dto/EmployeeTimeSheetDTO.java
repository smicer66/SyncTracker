package com.syncstate.apps.SyncTracker.models.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class EmployeeTimeSheetDTO {
    private String employeeName;
    private String employeeNumber;
    private String employeeSocialSecurityNo;
    private Collection<TimeSheetDTO> timeSheetList;

    @Getter
    @Setter
    public static class TimeSheetDTO{

        private LocalDateTime clockInTime;
        private LocalDateTime clockOutTime;
        private Boolean clockOutBySystem;
        private long approximatedWorkHoursInMinutes;
    }
}
