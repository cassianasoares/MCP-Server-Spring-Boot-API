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
class FirstApiTest extends RequestApiTest {

    @InjectMocks
    private FirstApi firstApi;

    @BeforeEach
    void setup(){
        // Injeta o valor da variável que seria @Value
        String mockUrl = "https://example.com/download/subtitle/?id=%s&lang=%s&ext=%s";
        ReflectionTestUtils.setField(firstApi, "firstApiUrl", mockUrl);
    }

    @Test
    @DisplayName("Should return subtitle text when First API request is successful")
    public void makeRequest(){

        String subtitleResponse;
        try {
            subtitleResponse = Utils.getResourcePath("FirstApiTest/subtitle_response.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        makeRequest(
                subtitleResponse,
                "https://example.com/download/subtitle/?id=tFg49R-Pe3&lang=a.en&ext=txt",
                firstApi
        );
    }
}