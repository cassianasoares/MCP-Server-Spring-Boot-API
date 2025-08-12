package com.demo.mcpservicetube.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
public class Transcript {

    private double duration;
    private double start;
    private String text;

    public String getSubtitleText(List<Transcript> transcripts) {
        return transcripts.stream()
                .map(Transcript::getText)
                .collect(Collectors.joining(" "));
    }
}
