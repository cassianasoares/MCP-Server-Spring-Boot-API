package com.demo.mcpservicetube.service;

import com.demo.mcpservicetube.model.Video;
import com.demo.mcpservicetube.strategy.RequestApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SubtitleService {

    private final RestTemplate restTemplate;
    private final List<RequestApi> requestApis;

    public SubtitleService(RestTemplateBuilder restTemplateBuilder, List<RequestApi> requestApis) {
        this.restTemplate = restTemplateBuilder.build();
        this.requestApis = requestApis;
    }

    public String getSubtitle(Video video) {

        String subTitleResult;

        for (RequestApi requestApi : requestApis) {
            try {
                subTitleResult = requestApi.makeRequest(video, restTemplate);
                if (subTitleResult != null && !subTitleResult.isEmpty()) {
                    return subTitleResult;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
      return null;
    }
}
