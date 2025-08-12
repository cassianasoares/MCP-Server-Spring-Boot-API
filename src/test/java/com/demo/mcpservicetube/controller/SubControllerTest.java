package com.demo.mcpservicetube.controller;

import com.demo.mcpservicetube.model.Video;
import com.demo.mcpservicetube.service.SubtitleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class SubControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SubtitleService subtitleService;

    @Test
    @DisplayName("Should return subtitle text when subtitle is found")
    void getSub() throws Exception {
        // Arrange
        when(subtitleService.getSubtitle(
                new Video("tFg49R-Pe3", "EN", "txt")
        )).thenReturn("Sample subtitle text");

        //Act
        var response = mockMvc.perform(get("/sub")
                .param("video_id", "tFg49R-Pe3")
                .param("lang", "EN")
                .param("text_format", "txt"))
                .andReturn()
                .getResponse();

        // Assert
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals("Sample subtitle text", response.getContentAsString());
    }

    @Test
    @DisplayName("Should return 404 when subtitle is not found")
    void getSubError() throws Exception {
        // Arrange
        when(subtitleService.getSubtitle(
                new Video("tFg49R-Pe3", "EN", "txt")
        )).thenReturn(null);

        //Act
        var response = mockMvc.perform(get("/sub")
                        .param("video_id", "tFg49R-Pe3")
                        .param("lang", "EN")
                        .param("text_format", "txt"))
                .andReturn()
                .getResponse();

        // Assert
        Assertions.assertEquals(404, response.getStatus());
    }
}