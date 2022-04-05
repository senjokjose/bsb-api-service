package com.bsbApiService.bsb.controller;

import com.bsbApiService.bsb.model.BSBData;
import com.bsbApiService.bsb.service.BSBSupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/bsbs")
public class BSBSupplyController {

    @Autowired
    private BSBSupplyService bsbSupplyService;

    @GetMapping
    public Iterable findAll() {
        return bsbSupplyService.getAllBSBData();
    }

    @GetMapping(path="/bsbNumber/{bsbNumber}")
    public BSBData findByBSBNumber(@PathVariable String bsbNumber) {
        return bsbSupplyService.getBSBData(bsbNumber);
    }

}
