package com.example.footballarchive.repository;

import com.example.footballarchive.domain.ArchivedMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArchiveEntryRepository extends JpaRepository<ArchivedMatch,Long> {
    @Query("""
           SELECT a
           FROM ArchivedMatch a
           WHERE LOWER(a.matchTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
           """)
    List<ArchivedMatch> searchByTitle(@Param("keyword") String keyword);
}
