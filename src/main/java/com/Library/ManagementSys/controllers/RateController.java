package com.Library.ManagementSys.controllers;

import com.Library.ManagementSys.advices.ApiResponse;
import com.Library.ManagementSys.services.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/rate")
public class RateController
{
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping
    public ResponseEntity getRate(@RequestParam String curr, @RequestParam String base_curr,
                                  @RequestParam Integer units)
    {
        if(!curr.isEmpty())
        {
            ApiResponse<String> apiResponse = new ApiResponse<>();

            Double rate = rateService.getRate(base_curr).getData().get(curr);

            apiResponse.setData(units+" "+ base_curr +" = "+ rate*units +" "+curr);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }

        Map<String, Double> conversionRate = rateService.getRate(base_curr).getData();
        ApiResponse<List<String>> apiResponse = new ApiResponse<>();
        List<String> stringList = new ArrayList<String>();

        conversionRate.forEach((key,val) ->
        {
            stringList.add(units+" "+ base_curr +" = "+ val*units +" "+key);
        });
        apiResponse.setData(stringList);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
