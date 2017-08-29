package com.doc.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doc.word.entity.Word;
import com.doc.word.entity.WordId;

@Repository
public interface WordRepository extends JpaRepository<Word, WordId> {
}