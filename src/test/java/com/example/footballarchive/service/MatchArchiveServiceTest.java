package com.example.footballarchive.service;
import com.example.footballarchive.domain.MatchReport;
import com.example.footballarchive.dto.CreatedArchivedMatchRequest;
import com.example.footballarchive.dto.MatchType;
import com.example.footballarchive.exception.ArchiveConflictException;
import com.example.footballarchive.logging.ArchiveAuditLogger;
import com.example.footballarchive.repository.ArchiveEntryRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class MatchArchiveServiceTest {
    private final ArchiveEntryRepository repository =
            Mockito.mock(ArchiveEntryRepository.class);

    private final ArchiveAuditLogger auditLogger =
            Mockito.mock(ArchiveAuditLogger.class);

    private final MatchArchiveService service =
            new MatchArchiveService(repository, auditLogger);

    @Test
    void createArchivedMatch_success() {

        CreatedArchivedMatchRequest request = new CreatedArchivedMatchRequest();
        request.setType(MatchType.REPORT);
        request.setMatchTitle("WC Final 2018");
        request.setVenue("Luzhniki Stadium");
        request.setReportText("France defeated Croatia 4-2");

        Mockito.when(repository.save(any()))
                .thenReturn(new MatchReport(
                        "WC Final 2018",
                        "Luzhniki Stadium",
                        "France defeated Croatia 4-2"
                ));

        Long id = service.createArchivedMatch(request);

        assertNotNull(id);
    }

    @Test
    void createArchivedMatch_duplicateTitle_throwsConflict() {

        CreatedArchivedMatchRequest request = new CreatedArchivedMatchRequest();
        request.setType(MatchType.REPORT);
        request.setMatchTitle("Duplicate Match");
        request.setVenue("Some Venue");
        request.setReportText("Some report");

        Mockito.when(repository.save(any()))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(
                ArchiveConflictException.class,
                () -> service.createArchivedMatch(request)
        );
    }
}
