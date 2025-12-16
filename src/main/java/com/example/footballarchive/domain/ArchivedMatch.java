package com.example.footballarchive.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(
        name = "archived_match",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "archived_match_title",
                        columnNames = "match_title"
                )
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ArchivedMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "match_title", nullable = false)
    private String matchTitle;

    @NotBlank
    @Column(name = "venue", nullable = false)
    private String venue;

    protected ArchivedMatch() {}

    protected ArchivedMatch(String matchTitle, String venue) {
        this.matchTitle = matchTitle;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public String getMatchTitle() {
        return matchTitle;
    }

    public void setMatchTitle(String matchTitle) {
        this.matchTitle = matchTitle;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

}
