package com.example.footballarchive.controller;
import com.example.footballarchive.dto.CreatedArchivedMatchRequest;
import com.example.footballarchive.dto.PublicArchivedMatchDto;
import com.example.footballarchive.dto.UserArchivedMatchDto;
import com.example.footballarchive.service.MatchArchiveService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/knowledge")
public class MatchArchiveController {

    private final MatchArchiveService service;

    public MatchArchiveController(MatchArchiveService service) {
        this.service = service;
    }

    @GetMapping("/public")
    public ResponseEntity<List<PublicArchivedMatchDto>> getPublicMatches() {
        return ResponseEntity.ok(service.getPublicMatches());
    }

    @GetMapping
    public ResponseEntity<List<UserArchivedMatchDto>> getUserMatches(
            @RequestParam(required = false) String keyword) {

        return ResponseEntity.ok(service.getUserMatches(keyword));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> createArchivedMatch(
            @Valid @RequestBody CreatedArchivedMatchRequest request) {

        Long id = service.createArchivedMatch(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }
}