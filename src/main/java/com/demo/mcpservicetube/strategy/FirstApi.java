package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.model.Video;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

@Component
public class FirstApi implements RequestApi{

    @Value("${first.api.url}")
    @Setter
    private String firstApiUrl;

    @Override
    public String makeRequest(Video video, RestTemplate restTemplate) {

        String url = String.format(
                firstApiUrl,
                video.videoId(), "a."+video.lang().toLowerCase(), video.textFormat().toLowerCase()
        );
        var response = restTemplate.getForObject(url, String.class);

        return HtmlUtils.htmlUnescape(response.replace("\\n", "\n").replace("\r\n", " ") );

    }
}
