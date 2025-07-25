package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Settlement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, String> {
  List<Settlement> findByGroup_Id(String groupId);

  List<Settlement> findByDebtor_Id(String userId);

  List<Settlement> findByCreditor_Id(String userId);
}
