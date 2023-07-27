package com.ekene.clustereddata_warehouse.repository;

import com.ekene.clustereddata_warehouse.model.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FxDealRepository extends JpaRepository<FxDeal, Long> {
}
