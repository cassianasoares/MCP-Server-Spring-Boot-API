package com.demo.mcpservicetube.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TranscriptParts {
    @JsonProperty("transcript_parts")
    private List<Transcript> transcripts;

    public List<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(List<Transcript> transcripts) {
        this.transcripts = transcripts;
    }
}

