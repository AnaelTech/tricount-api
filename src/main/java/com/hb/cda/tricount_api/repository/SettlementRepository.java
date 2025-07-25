package com.hb.cda.tricount_api.repository;

import com.hb.cda.tricount_api.entity.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettlementRepository extends JpaRepository<Settlement, String> {
}
