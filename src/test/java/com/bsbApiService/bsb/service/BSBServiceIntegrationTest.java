package com.bsbApiService.bsb.service;

import com.bsbApiService.bsb.model.BSBData;
import com.bsbApiService.bsb.repository.BSBDataRepository;
import com.bsbApiService.fileProcessing.service.BSBUpdateFileProcessingService;
import com.bsbApiService.fileProcessing.service.DefaultBSBFileProcessingService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BSBServiceIntegrationTest {

    @Autowired
    private BSBDataRepository bsbDataRepository;

    @Autowired
    private DefaultBSBFileProcessingService defaultBSBFileProcessingService;

    @Autowired
    private BSBSupplyService bsbSupplyService;

    private boolean bsbDataLoaded = false;


    @BeforeEach
    void setUp() {
        loadBsbData();

    }

    @Test
    public void getAllBSBData() {
        loadBsbData();
        Assert.assertTrue(bsbSupplyService.getAllBSBData().size() > 0);
    }

    @Test
    public void getBSBData() {
        loadBsbData();
        String bsbNumber = "012-002";
        BSBData bsbData = bsbSupplyService.getBSBData(bsbNumber);
        Assert.assertNotNull(bsbData);
        Assert.assertEquals(bsbNumber, bsbData.getBsbNumber());
    }

    @Test
    public void getBSBData_notFound() {
        loadBsbData();
        String bsbNumber = "012-0021";
        BSBData bsbData = bsbSupplyService.getBSBData(bsbNumber);
        Assert.assertNull(bsbData);
    }

    private void loadBsbData(){
        if(bsbDataLoaded){
            return;
        }
        defaultBSBFileProcessingService.processFile("/Users/senjosmac/Desktop/ftp-downloads/BSBDirectoryMar22-312.csv");
        Assert.assertTrue(bsbDataRepository.findAll().size() > 0);
        bsbDataLoaded = true;
    }


}