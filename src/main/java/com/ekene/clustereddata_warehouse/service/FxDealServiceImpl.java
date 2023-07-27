package com.ekene.clustereddata_warehouse.service;

import com.ekene.clustereddata_warehouse.dto.FxDealDTO;
import com.ekene.clustereddata_warehouse.exception.NotFoundException;
import com.ekene.clustereddata_warehouse.model.FxDeal;
import com.ekene.clustereddata_warehouse.repository.FxDealRepository;
import com.ekene.clustereddata_warehouse.util.FxDealUtils;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FxDealServiceImpl implements FxDealService {
    private final FxDealUtils fxDealUtils;
    private final FxDealRepository fxDealRepository;

    public FxDealDTO createFxDeal(FxDealDTO fxDealDTO) {
        if (!this.fxDealUtils.isISOCurrencyCodeValid(fxDealDTO.getOrderingCurrencyISOCode()) && !this.fxDealUtils.isISOCurrencyCodeValid(fxDealDTO.getToCurrencyISOCode())) {
            log.info("Wrong ISO code provided");
            throw new ValidationException("Invalid currency code provided");
        } else {
            FxDeal fxDeal = fxDealUtils.map(fxDealDTO);
            fxDeal = fxDealRepository.save(fxDeal);
            return this.fxDealUtils.map(fxDeal);
        }
    }

    public FxDealDTO getFxDealById(Long id) throws NotFoundException {
        log.info("Retrieve fx deal by id");
        Optional<FxDealDTO> optionalFxDeal = fxDealRepository.findById(id).map(fxDealUtils::map);
        return optionalFxDeal.orElseThrow(() -> new NotFoundException(""));
    }

    public List<FxDealDTO> getAllFxDeals() {
        log.info("Retrieve all deals");
        List<FxDeal> fxDeals = fxDealRepository.findAll();
        return fxDeals.stream().map(fxDealUtils::map).collect(Collectors.toList());
    }

}
