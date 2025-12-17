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
        request.setMatchTitle("El Clasico 2024");
        request.setVenue("Santiago Bernabeu");
        request.setReportText(
                "Barcelona defeated real madrid in El Clasico 2024 with a strong second-half performance."
        );

        MatchReport saved = Mockito.mock(MatchReport.class);
        Mockito.when(saved.getId()).thenReturn(1L);
        Mockito.when(saved.getMatchTitle()).thenReturn("El Clasico 2024");

        Mockito.when(repository.save(any()))
                .thenReturn(saved);
        Long id = service.createArchivedMatch(request);

        assertNotNull(id);
        assertEquals(1L, id);



    }

    @Test
    void createArchivedMatch_duplicateTitle_throwsConflict() {

        CreatedArchivedMatchRequest request = new CreatedArchivedMatchRequest();
        request.setType(MatchType.REPORT);
        request.setMatchTitle("Duplicate Match");
        request.setVenue("Some Venue");
        request.setReportText("This is a valid report text with more than twenty characters.");

        Mockito.when(repository.save(any()))
                .thenThrow(DataIntegrityViolationException.class);

        assertThrows(
                ArchiveConflictException.class,
                () -> service.createArchivedMatch(request)
        );
    }
}
