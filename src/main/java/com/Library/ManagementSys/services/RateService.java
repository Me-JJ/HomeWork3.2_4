package com.Library.ManagementSys.services;

import com.Library.ManagementSys.dto.RateDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RateService
{
    private final RestClient restClient;

    public RateService(RestClient restClient) {
        this.restClient = restClient;
    }

    public RateDto getRate(String base_curr)
    {
        String url = "?apikey=fca_live_NQZDjsUVeLLqGNZ1HZs1m2TTSHT9TRTXRhfm0b3r&currencies=&base_currency="+base_curr;

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(RateDto.class);

    }
}
