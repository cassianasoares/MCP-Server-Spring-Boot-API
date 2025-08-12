package com.demo.mcpservicetube.strategy;

import com.demo.mcpservicetube.utils.Utils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class FirstApiTest extends RequestApiTest {

    @InjectMocks
    private FirstApi firstApi;

    @BeforeEach
    public void setUp() {
        firstApi.setFirstApiUrl("https://example.com/download/subtitle/?id=%s&lang=%s&ext=%s");
    }

    @SneakyThrows
    @Test
    @DisplayName("Should return subtitle text when First API request is successful")
    public void makeRequest(){
        // Arrange
        String subtitleResponse = Utils.getResourcePath("FirstApiTest/subtitle_response.txt");
        // Act & Assert
        makeRequest(
                subtitleResponse,
                "https://example.com/download/subtitle/?id=tFg49R-Pe3&lang=a.en&ext=txt",
                firstApi
        );
    }
}