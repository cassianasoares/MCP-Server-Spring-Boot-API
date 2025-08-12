package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.utils.Utils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecondApiTest extends RequestApiTest {

    @InjectMocks
    private SecondApi secondApi;

    @BeforeEach
    public void setUp() {
        secondApi.setSecondApiUrl("https://example.com/api/landing/youtube/subtitles?video_id=%s&language=%s");
    }


    @SneakyThrows
    @Test
    @DisplayName("Should return subtitle text when First API request is successful")
    public void makeRequest(){
        // Arrange
        String subtitleResponse = Utils.getResourcePath("/SecondApiTest/subtitle_response.json");
        // Act & Assert
        makeRequest(
                subtitleResponse,
                "https://example.com/api/landing/youtube/subtitles?video_id=tFg49R-Pe3&language=EN",
                secondApi
        );
    }

}