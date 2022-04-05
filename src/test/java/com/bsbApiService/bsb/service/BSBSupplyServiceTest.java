package com.bsbApiService.bsb.service;

import com.bsbApiService.bsb.repository.BSBDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BSBSupplyServiceTest {

    @Mock
    private BSBDataRepository bsbDataRepository;

    @InjectMocks
    private BSBSupplyService bsbSupplyService = new BSBSupplyService();

    @BeforeEach
    void setUp() {
    }

    @Test
    public void getAllBSBData() {
        bsbSupplyService.getAllBSBData();
        Mockito.verify(bsbDataRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getBSBData() {
        String bsbNumber = "0000-222";
        bsbSupplyService.getBSBData(bsbNumber);
        Mockito.verify(bsbDataRepository, Mockito.times(1)).findBSBDataByBsbNumber(bsbNumber);
    }
}