package com.example.footballarchive.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class MatchQuote extends ArchivedMatch {
    @NotBlank
    @Column(name = "quote_text", nullable = false, length = 1000)
    private String quoteText;

    @NotBlank
    @Column(name = "speaker", nullable = false)
    private String speaker;

    protected MatchQuote() {
    }

    public MatchQuote(String matchTitle, String venue, String quoteText, String speaker) {
        super(matchTitle, venue);
        this.quoteText = quoteText;
        this.speaker = speaker;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
