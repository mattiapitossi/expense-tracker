package com.expensetracker.repository;

import com.expensetracker.model.ExpensePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensePeriodRepository extends JpaRepository<ExpensePeriod, Long> {

    public List<ExpensePeriod> getExpensePeriodsByNextPaymentIsNotNull();
    
}
