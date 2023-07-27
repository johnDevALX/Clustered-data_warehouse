package com.ekene.clustereddata_warehouse.controller;

import com.ekene.clustereddata_warehouse.ClusteredDataWarehouseApplication;
import com.ekene.clustereddata_warehouse.dto.FxDealDTO;
import com.ekene.clustereddata_warehouse.model.FxDeal;
import com.ekene.clustereddata_warehouse.repository.FxDealRepository;
import com.ekene.clustereddata_warehouse.util.FxDealUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@AutoConfigureMockMvc
@SpringBootTest(classes = ClusteredDataWarehouseApplication.class)
public class FxDealControllerTest {
    @Autowired
    FxDealRepository fxDealRepository;

    @Autowired
    FxDealUtils fxDealUtils;

    @Autowired
    MockMvc restFxDealMockMvc;

    FxDeal fxDeal;

    static final ObjectMapper mapper = createObjectMapper();


    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


    private final static BigDecimal DEFAULT_DEAL_AMOUNT = BigDecimal.ONE;
    private final static Currency DEFAULT_FROM = Currency.getInstance("NGN");
    private final static Currency DEFAULT_TO = Currency.getInstance("USD");
    private final static LocalDateTime DEFAULT_DEAL_TIMESTAMP = LocalDateTime.now();

    @BeforeEach
    public void initTest() {
        fxDeal = createDeal();
    }

    @AfterEach
    public void destroyTest() {
        fxDealRepository.deleteAll();
    }

    public static FxDeal createDeal() {
        return FxDeal.builder().
                dealAmount(DEFAULT_DEAL_AMOUNT).
                orderingCurrencyISOCode(DEFAULT_FROM).
                toCurrencyISOCode(DEFAULT_TO).
                dealTimeStamp(DEFAULT_DEAL_TIMESTAMP)
                .build();
    }

    @Test
    @Transactional
    void createFxDeal() throws Exception {
        int databaseSizeBeforeCreate = fxDealRepository.findAll().size();
        // Create the FxDeal
        FxDealDTO fxDealDTO = fxDealUtils.map(fxDeal);
        restFxDealMockMvc
                .perform(post("/api/deals/fxDeals").contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonBytes(fxDealDTO)))
                .andExpect(status().isCreated());

        // Validate the FxDeal in the database
        List<FxDeal> fxDealList = fxDealRepository.findAll();
        assertThat(fxDealList).hasSize(databaseSizeBeforeCreate + 1);
        FxDeal testFxDeal = fxDealList.get(fxDealList.size() - 1);
        assertThat(testFxDeal.getDealAmount()).isEqualTo(DEFAULT_DEAL_AMOUNT);
        assertThat(testFxDeal.getDealTimeStamp()).isEqualTo(DEFAULT_DEAL_TIMESTAMP);
        assertThat(testFxDeal.getOrderingCurrencyISOCode()).isEqualTo(DEFAULT_FROM);
        assertThat(testFxDeal.getToCurrencyISOCode()).isEqualTo(DEFAULT_TO);
    }

    @Test
    @Transactional
    void getAllFxDeals() throws Exception {
        // Initialize the database
        fxDealRepository.saveAndFlush(fxDeal);

        // Get all the fx deals
        restFxDealMockMvc
                .perform(get("/api/deals/getAllFxDeals"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.[*].dealAmount").value(hasItem(DEFAULT_DEAL_AMOUNT.intValue())))
                .andExpect(jsonPath("$.data.[*].orderingCurrencyISOCode").value(hasItem(DEFAULT_FROM.getCurrencyCode())))
                .andExpect(jsonPath("$.data.[*].toCurrencyISOCode").value(hasItem(DEFAULT_TO.getCurrencyCode())));
    }


    @Test
    @Transactional
    void getFxDealById() throws Exception {
        // Initialize the database
        FxDeal deal = fxDealRepository.saveAndFlush(fxDeal);

        // Get the fx deal by Id
        restFxDealMockMvc
                .perform(get("/api/deals/fxDeals/" + deal.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.data.dealAmount").value((DEFAULT_DEAL_AMOUNT.intValue())))
                .andExpect(jsonPath("$.data.orderingCurrencyISOCode").value((DEFAULT_FROM.getCurrencyCode())))
                .andExpect(jsonPath("$.data.toCurrencyISOCode").value((DEFAULT_TO.getCurrencyCode())));
    }
}
