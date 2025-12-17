package com.example.footballarchive.logging;
import org.slf4j.*;
import org.springframework.stereotype.Component;

@Component
public class ArchiveAuditLogger {
    private static final Logger log =
            LoggerFactory.getLogger(ArchiveAuditLogger.class);

    public void logPublicRead() {
        log.info("Public access: listing archived matches");
    }

    public void logUserRead() {
        log.info("Authenticated access: listing archived matches");
    }

    public void logCreation(String matchTitle) {
        log.info("Admin created archived match with title='{}'", matchTitle);
    }

    public void logConflict(String matchTitle) {
        log.warn("Conflict detected while creating archived match with title='{}'", matchTitle);
    }
}
