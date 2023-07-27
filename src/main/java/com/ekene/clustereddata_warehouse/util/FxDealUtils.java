package com.ekene.clustereddata_warehouse.util;

import com.ekene.clustereddata_warehouse.dto.FxDealDTO;
import com.ekene.clustereddata_warehouse.model.FxDeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
@Slf4j
public class FxDealUtils {

    public FxDealUtils() {
    }

    public FxDeal map(FxDealDTO fxDealDTO) {
        try {
            return FxDeal.builder().
                    orderingCurrencyISOCode(fxDealDTO.getOrderingCurrencyISOCode() != null ? Currency.getInstance(fxDealDTO.getOrderingCurrencyISOCode()) : null).
                    toCurrencyISOCode(fxDealDTO.getToCurrencyISOCode() != null ? Currency.getInstance(fxDealDTO.getToCurrencyISOCode()) : null).
                    dealTimeStamp(fxDealDTO.getDealTimeStamp() != null ? fxDealDTO.getDealTimeStamp() : null).
                    dealAmount(fxDealDTO.getDealAmount() != null ? fxDealDTO.getDealAmount() : null).
                    build();
        } catch (Exception var3) {
            log.info("{}", var3);
            log.info("Exception in mapping FxDeal DTO to fxDeal class");
            return null;
        }
    }

    public FxDealDTO map(FxDeal fxDeal) {
        try {
            return FxDealDTO.builder().
                    id(fxDeal.getId() != null ? fxDeal.getId() : null).
                    orderingCurrencyISOCode(fxDeal.getOrderingCurrencyISOCode() != null ? fxDeal.getOrderingCurrencyISOCode().getCurrencyCode() : null).
                    toCurrencyISOCode(fxDeal.getToCurrencyISOCode() != null ? fxDeal.getToCurrencyISOCode().getCurrencyCode() : null).
                    dealTimeStamp(fxDeal.getDealTimeStamp() != null ? fxDeal.getDealTimeStamp() : null).
                    dealAmount(fxDeal.getDealAmount() != null ? fxDeal.getDealAmount() : null).
                    build();
        } catch (Exception var3) {
            log.info("Exception in mapping FxDeal to FxDeal DTO class");
            return null;
        }
    }

    public boolean isISOCurrencyCodeValid(String currencyCode) {
        return Currency.getAvailableCurrencies().stream().anyMatch((c) -> {
            return c.getCurrencyCode().equalsIgnoreCase(currencyCode);
        });
    }
}
