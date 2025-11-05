package com.demo.mcpservicetube.model;

import java.util.List;
import java.util.stream.Collectors;
public class Transcript {

    private double duration;
    private double start;
    private String text;

    public String getSubtitleText(List<Transcript> transcripts) {
        return transcripts.stream()
                .map(Transcript::getText)
                .collect(Collectors.joining(" "));
    }

    public double getDuration() {
        return duration;
    }

    public double getStart() {
        return start;
    }

    public String getText() {
        return text;
    }
}
