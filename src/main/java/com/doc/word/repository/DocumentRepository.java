package com.doc.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doc.word.entity.Document;
import com.doc.word.entity.DocumentId;

@Repository
public interface DocumentRepository extends JpaRepository<Document, DocumentId> {
}