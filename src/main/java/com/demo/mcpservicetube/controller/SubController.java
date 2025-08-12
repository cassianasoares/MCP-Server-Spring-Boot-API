package com.demo.mcpservicetube.controller;

import com.demo.mcpservicetube.model.Video;
import com.demo.mcpservicetube.service.SubtitleService;
import lombok.AllArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/sub")
public class SubController {

    private SubtitleService subtitleService;

    @Tool(name = "get_subtitle", description = "Get subtitle for a video")
    @GetMapping()
    public ResponseEntity<String> getSub(@RequestParam(value = "video_id") String videoId,
                                         @RequestParam(value = "lang") String lang,
                                         @RequestParam(value = "text_format") String textFormat) {
        Video video = new Video(videoId, lang, textFormat);
        var subtitle = subtitleService.getSubtitle(video);

        if (subtitle == null || subtitle.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(subtitle);
    }

}
