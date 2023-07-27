package com.ekene.clustereddata_warehouse.controller;

import com.ekene.clustereddata_warehouse.dto.FxDealDTO;
import com.ekene.clustereddata_warehouse.exception.NotFoundException;
import com.ekene.clustereddata_warehouse.service.FxDealService;
import com.ekene.clustereddata_warehouse.util.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/deals")
@RequiredArgsConstructor
@Slf4j
public class FxDealController extends BaseController {
    private final FxDealService fxDealService;

    @PostMapping("/fxDeals")
    public ResponseEntity<?> createFxDeal(@RequestBody FxDealDTO fxDealDTO) {
        log.info("HTTP request from controller to create fxDeal: {}", fxDealDTO);
        log.info("{} is null? {}", fxDealDTO, fxDealDTO == null);
        FxDealDTO fxDealOrder = fxDealService.createFxDeal(fxDealDTO);
        log.info("Successfully saved fxDeal: {}", fxDealDTO);
        return this.getAppResponse(HttpStatus.CREATED, "success", fxDealOrder);
    }

    @GetMapping("/fxDeals/{id}")
    public ResponseEntity<?> getFxDealById(@PathVariable("id") Long id) throws NotFoundException {
        FxDealDTO fxDeal = fxDealService.getFxDealById(id);
        log.info("Successfully retrieved fx deal by id: {}", id);
        return this.getAppResponse(HttpStatus.OK, "success", fxDeal);
    }

    @GetMapping({"/getAllFxDeals"})
    public ResponseEntity<?> getAllFxDeals() {
        log.debug("REST request to get list of fxDeals");
        return this.getAppResponse(HttpStatus.OK, "success", this.fxDealService.getAllFxDeals());
    }

}
