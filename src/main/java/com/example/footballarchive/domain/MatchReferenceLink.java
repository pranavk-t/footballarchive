package com.example.footballarchive.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
public class MatchReferenceLink extends ArchivedMatch {
    @NotBlank
    @Column(name = "reference_url", nullable = false)
    private String referenceUrl;

    @Column(name = "description")
    private String description;

    protected MatchReferenceLink() {
    }

    public MatchReferenceLink(
            String matchTitle,
            String venue,
            String referenceUrl,
            String description) {
        super(matchTitle, venue);
        this.referenceUrl = referenceUrl;
        this.description = description;
    }

    public String getReferenceUrl() {
        return referenceUrl;
    }

    public void setReferenceUrl(String referenceUrl) {
        this.referenceUrl = referenceUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
