package com.example.footballarchive.domain;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.*;
public class MatchArchiveCollection extends ArchivedMatch {
    @ManyToMany
    @JoinTable(
            name = "match_archive_collection_items",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "match_id")
    )
    private List<ArchivedMatch> matches = new ArrayList<>();

    protected MatchArchiveCollection() {
    }

    public MatchArchiveCollection(String matchTitle, String venue) {
        super(matchTitle, venue);
    }

    public List<ArchivedMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<ArchivedMatch> matches) {
        this.matches = matches;
    }
}
