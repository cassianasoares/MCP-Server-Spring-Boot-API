package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.domain.Transcript;
import com.demo.mcpservicetube.domain.TranscriptParts;
import com.demo.mcpservicetube.model.Video;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SecondApi implements RequestApi{

    @Value("${second.api.url}")
    @Setter
    private String secondApiUrl;

    @Override
    public String makeRequest(Video video, RestTemplate restTemplate) {
        String url = String.format(
                secondApiUrl,
                video.videoId(), video.lang().toUpperCase()
        );

        var response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        TranscriptParts transcripts;

        try {
            transcripts = mapper.readValue(response, TranscriptParts.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new Transcript().getSubtitleText(transcripts.getTranscripts());
    }
}
