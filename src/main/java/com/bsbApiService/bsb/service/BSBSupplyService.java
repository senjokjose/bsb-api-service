package com.bsbApiService.bsb.service;

import com.bsbApiService.bsb.model.BSBData;
import com.bsbApiService.bsb.repository.BSBDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BSBSupplyService {
    @Autowired
    private BSBDataRepository bsbDataRepository;

    /**
     * Return all the BSB data from System. Look in the distributed cache and return the data.
     * @return
     */
    @Cacheable("allBsbCache") //
    public List<BSBData> getAllBSBData(){
        return bsbDataRepository.findAll();
    }

    /**
     * Search BSB by BSB number
     * @param bsbNumber
     * @return
     */
    @Cacheable(key= "#bsbNumber", value = "bsbSearchCache")
    public BSBData getBSBData(String bsbNumber){
        return bsbDataRepository.findBSBDataByBsbNumber(bsbNumber);
    }

}
