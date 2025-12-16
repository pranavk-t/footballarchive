package com.example.footballarchive.dto;

public class PublicArchivedMatchDto {
    private Long id;
    private String matchTitle;
    private String venue;

    public PublicArchivedMatchDto(Long id, String matchTitle, String venue) {
        this.id = id;
        this.matchTitle = matchTitle;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public String getVenue() {
        return venue;
    }
}
