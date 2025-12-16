package com.example.footballarchive.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class MatchReport extends ArchivedMatch {
    @NotBlank
    @Size(min = 20)
    @Column(name = "report_text", nullable = false, length = 3000)
    private String reportText;

    protected MatchReport() {
    }

    public MatchReport(String matchTitle, String venue, String reportText) {
        super(matchTitle, venue);
        this.reportText = reportText;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }
}
