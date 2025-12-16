package com.example.footballarchive.dto;

public class UserArchivedMatchDto {
    private Long id;
    private String type;
    private String matchTitle;
    private String venue;

    private String content;   // report text / quote / link description
    private String reference; // url / speaker / null

    public UserArchivedMatchDto(
            Long id,
            String type,
            String matchTitle,
            String venue,
            String content,
            String reference) {

        this.id = id;
        this.type = type;
        this.matchTitle = matchTitle;
        this.venue = venue;
        this.content = content;
        this.reference = reference;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public String getVenue() {
        return venue;
    }

    public String getContent() {
        return content;
    }

    public String getReference() {
        return reference;
    }
}
