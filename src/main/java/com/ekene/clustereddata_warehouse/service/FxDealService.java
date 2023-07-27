package com.ekene.clustereddata_warehouse.service;

import com.ekene.clustereddata_warehouse.dto.FxDealDTO;
import com.ekene.clustereddata_warehouse.exception.NotFoundException;

import java.util.List;

public interface FxDealService {
    FxDealDTO createFxDeal(FxDealDTO fxDealDTO);

    FxDealDTO getFxDealById(Long id) throws NotFoundException;

    List<FxDealDTO> getAllFxDeals();
}
