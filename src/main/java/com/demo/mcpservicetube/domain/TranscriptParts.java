package com.demo.mcpservicetube.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.util.List;

@Setter
public class TranscriptParts {
    @JsonProperty("transcript_parts")
    private List<Transcript> transcripts;

    public List<Transcript> getTranscripts() {
        return transcripts;
    }
}

