package com.syncstate.apps.SyncTracker.repositories;


import com.syncstate.apps.SyncTracker.models.Employee;
import com.syncstate.apps.SyncTracker.models.EmployeeContract;
import com.syncstate.apps.SyncTracker.models.EmployeeContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;

@Repository
public interface IEmployeeContractTemplateRepository extends JpaRepository<EmployeeContractTemplate, BigInteger> {

    @Query("Select tp from Employee tp WHERE tp.deletedAt IS NULL AND tp.employeeId = :employeeId")
    Employee getEmployeeByEmployeeId(BigInteger employeeId);

    @Query("Select tp from EmployeeContractTemplate tp WHERE tp.deletedAt IS NULL AND tp.clientId = :clientId")
    Collection<EmployeeContractTemplate> getEmployeeContractTemplateByClientId(BigInteger clientId);

}
