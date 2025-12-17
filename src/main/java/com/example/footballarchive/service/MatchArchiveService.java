package com.example.footballarchive.service;
import java.util.ArrayList;
import java.util.List;


import com.example.footballarchive.domain.ArchivedMatch;
import com.example.footballarchive.domain.MatchArchiveCollection;
import com.example.footballarchive.domain.MatchQuote;
import com.example.footballarchive.domain.MatchReferenceLink;
import com.example.footballarchive.domain.MatchReport;
import com.example.footballarchive.dto.CreatedArchivedMatchRequest;
import com.example.footballarchive.dto.MatchType;
import com.example.footballarchive.dto.PublicArchivedMatchDto;
import com.example.footballarchive.dto.UserArchivedMatchDto;
import com.example.footballarchive.exception.ArchiveConflictException;
import com.example.footballarchive.logging.ArchiveAuditLogger;
import com.example.footballarchive.repository.ArchiveEntryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


@Service
public class MatchArchiveService {
    private final ArchiveEntryRepository repository;
    private final ArchiveAuditLogger auditLogger;

    @Value("${app.archive.max-collection-size}")
    private int maxCollectionSize;

    public MatchArchiveService(
            ArchiveEntryRepository repository,
            ArchiveAuditLogger auditLogger) {
        this.repository = repository;
        this.auditLogger = auditLogger;
    }

    public List<PublicArchivedMatchDto> getPublicMatches() {
        auditLogger.logPublicRead();

        List<ArchivedMatch> matches = repository.findAll();
        List<PublicArchivedMatchDto> result = new ArrayList<>();

        for (ArchivedMatch match : matches) {
            result.add(new PublicArchivedMatchDto(
                    match.getId(),
                    match.getMatchTitle(),
                    match.getVenue()
            ));
        }

        return result;
    }

    public List<UserArchivedMatchDto> getUserMatches(String keyword) {
        auditLogger.logUserRead();

        List<ArchivedMatch> matches;

        if (keyword == null || keyword.isBlank()) {
            matches = repository.findAll();
        } else {
            matches = repository.searchByTitle(keyword);
        }

        List<UserArchivedMatchDto> result = new ArrayList<>();

        for (ArchivedMatch match : matches) {
            result.add(mapToUserDto(match));
        }

        return result;
    }

    public Long createArchivedMatch(CreatedArchivedMatchRequest request) {
        ArchivedMatch entity = mapToEntity(request);

        try {
            ArchivedMatch saved = repository.save(entity);
            auditLogger.logCreation(saved.getMatchTitle());
            return saved.getId();
        } catch (DataIntegrityViolationException ex) {
            auditLogger.logConflict(request.getMatchTitle());
            throw new ArchiveConflictException(
                    "Archived match with the same title already exists");
        }
    }

    private UserArchivedMatchDto mapToUserDto(ArchivedMatch match) {

        if (match instanceof MatchReport) {
            MatchReport report = (MatchReport) match;      /* shows error report not found*/

            return new UserArchivedMatchDto(
                    report.getId(),
                    "REPORT",
                    report.getMatchTitle(),
                    report.getVenue(),
                    report.getReportText(),
                    null
            );
        }

        if (match instanceof MatchReferenceLink) {
            MatchReferenceLink link = (MatchReferenceLink) match;

            return new UserArchivedMatchDto(
                    link.getId(),
                    "LINK",
                    link.getMatchTitle(),
                    link.getVenue(),
                    link.getDescription(),
                    link.getReferenceUrl()
            );
        }

        if (match instanceof MatchQuote) {
            MatchQuote quote = (MatchQuote) match;

            return new UserArchivedMatchDto(
                    quote.getId(),
                    "QUOTE",
                    quote.getMatchTitle(),
                    quote.getVenue(),
                    quote.getQuoteText(),
                    quote.getSpeaker()
            );
        }

        if (match instanceof MatchArchiveCollection) {
            MatchArchiveCollection collection =
                    (MatchArchiveCollection) match;

            return new UserArchivedMatchDto(
                    collection.getId(),
                    "COLLECTION",
                    collection.getMatchTitle(),
                    collection.getVenue(),
                    "Collection size: " + collection.getMatches().size(),
                    null
            );
        }

        throw new IllegalStateException("Unknown archived match type");
    }

    private ArchivedMatch mapToEntity(CreatedArchivedMatchRequest request) {

        MatchType type = request.getType();

        if (type == null) {
            throw new IllegalArgumentException("Match type must be provided");
        }

        switch (type) {

            case REPORT:
                return new MatchReport(
                        request.getMatchTitle(),
                        request.getVenue(),
                        request.getReportText()
                );

            case LINK:
                return new MatchReferenceLink(
                        request.getMatchTitle(),
                        request.getVenue(),
                        request.getReferenceUrl(),
                        request.getDescription()
                );

            case QUOTE:
                return new MatchQuote(
                        request.getMatchTitle(),
                        request.getVenue(),
                        request.getQuoteText(),
                        request.getSpeaker()
                );

            case COLLECTION:
                MatchArchiveCollection collection =
                        new MatchArchiveCollection(
                                request.getMatchTitle(),
                                request.getVenue()
                        );

                if (collection.getMatches().size() > maxCollectionSize) {
                    throw new ArchiveConflictException(
                            "Collection exceeds maximum allowed size");
                }

                return collection;

            default:
                throw new IllegalArgumentException("Unsupported match type");
        }
    }
}
