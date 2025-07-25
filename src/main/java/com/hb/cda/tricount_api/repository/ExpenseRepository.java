package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Expense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {

  List<Expense> findByGroup_Id(String groupId);

  List<Expense> findByGroup_IdAndPayer_Id(String groupId, String payerId);

  List<Expense> findByGroup_IdAndBeneficiaries_Id(String groupId, String userId);

  List<Expense> findByGroup_IdAndAmountGreaterThan(String groupId, Double amount);

  List<Expense> findByGroup_IdAndAmountLessThan(String groupId, Double amount);
}
