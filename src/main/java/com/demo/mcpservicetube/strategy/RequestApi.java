package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.model.Video;
import org.springframework.web.client.RestTemplate;

public interface RequestApi {
    String makeRequest(Video video, RestTemplate restTemplate);
}
