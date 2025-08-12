package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.model.Video;
import lombok.SneakyThrows;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class RequestApiTest {

    @Mock
    private RestTemplate restTemplate;

    @SneakyThrows
    public void makeRequest(String resource, String url, RequestApi api) {
        // Arrange
        var video = new Video("tFg49R-Pe3", "en", "txt");
        when(restTemplate.getForObject(
                url,
                String.class
        )).thenReturn(resource);

        // Act
        String result = api.makeRequest(video, restTemplate);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(subtitleExpected()));
    }

    public String subtitleExpected() {
        return "this video has found you before August 16th. Lean in. Listen in because I've got a word that I know needed to find you right now. "
                + "So, you're at the right place at the right time. And I'm just going to read what came through when I was journaling with the Holy Spirit. "
                + "It's not random. It's not just a video.";
    }

}
