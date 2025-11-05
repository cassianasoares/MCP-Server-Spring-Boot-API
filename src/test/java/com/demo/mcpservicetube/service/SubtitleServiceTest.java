package com.demo.mcpservicetube.service;

import com.demo.mcpservicetube.model.Video;
import com.demo.mcpservicetube.strategy.FirstApi;
import com.demo.mcpservicetube.strategy.RequestApi;
import com.demo.mcpservicetube.strategy.SecondApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubtitleServiceTest {

    @InjectMocks
    private SubtitleService subtitleService;
    @Mock
    private RestTemplateBuilder restTemplateBuilder;
    @Mock
    private FirstApi firstApiMock;
    @Mock
    private SecondApi secondApiMock;
    @Mock
    private List<RequestApi> requestApis;

    @BeforeEach
    void setUp(){
        when(restTemplateBuilder.build()).thenReturn(new RestTemplate());
        this.requestApis =List.of(firstApiMock, secondApiMock);
        this.subtitleService = new SubtitleService(this.restTemplateBuilder, this.requestApis);
    }

    @Test
    @DisplayName("Should not try to call second API if first API returns subtitle")
    void getSubtitleInTheFirstApi() {
        //Arrange
        Video video = new Video("kp9EgS-B7", "en", "txt");
        when(firstApiMock.makeRequest(eq(video), any(RestTemplate.class)))
                .thenReturn("Sample subtitle text from first API");

        // Act
        String result = subtitleService.getSubtitle(video);

        // Assert
        assertNotNull(result);
        verifyNoInteractions(requestApis.get(1));
    }

    @Test
    @DisplayName("Should try to call second API if first API returns null")
    void getSubtitleInTheSecondApi() {
        //Arrange
        Video video = new Video("kp9EgS-B7", "en", "txt");
        when(firstApiMock.makeRequest(eq(video), any(RestTemplate.class)))
                .thenReturn(null);
        when(secondApiMock.makeRequest(eq(video), any(RestTemplate.class)))
                .thenReturn("Sample subtitle text from first API");

        // Act
        String result = subtitleService.getSubtitle(video);

        // Assert
        assertNotNull(result);
        verify(firstApiMock, times(1)).makeRequest(eq(video), any(RestTemplate.class));
        verify(firstApiMock, times(1)).makeRequest(eq(video), any(RestTemplate.class));
    }

    @Test
    @DisplayName("Should return null if both APIs return null")
    void subtitleReturnNoFound() {
        //Arrange
        Video video = new Video("kp9EgS-B7", "en", "txt");
        when(firstApiMock.makeRequest(eq(video), any(RestTemplate.class)))
                .thenReturn(null);
        when(secondApiMock.makeRequest(eq(video), any(RestTemplate.class)))
                .thenReturn(null);

        // Act
        String result = subtitleService.getSubtitle(video);

        // Assert
        assertNull(result);
        verify(firstApiMock, times(1)).makeRequest(eq(video), any(RestTemplate.class));
        verify(firstApiMock, times(1)).makeRequest(eq(video), any(RestTemplate.class));
    }

}