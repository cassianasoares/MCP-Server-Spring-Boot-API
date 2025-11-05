package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class SecondApiTest extends RequestApiTest {

    @InjectMocks
    private SecondApi secondApi;

    @BeforeEach
    void setup(){
        // Injeta o valor da variável que seria @Value
        String mockUrl = "https://example.com/api/landing/youtube/subtitles?video_id=%s&language=%s";
        ReflectionTestUtils.setField(secondApi, "secondApiUrl", mockUrl);
    }

    @Test
    @DisplayName("Should return subtitle text when First API request is successful")
    public void makeRequest(){
        // Arrange
        String subtitleResponse;
        try {
            subtitleResponse = Utils.getResourcePath("/SecondApiTest/subtitle_response.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Act & Assert
        makeRequest(
                subtitleResponse,
                "https://example.com/api/landing/youtube/subtitles?video_id=tFg49R-Pe3&language=EN",
                secondApi
        );
    }

}