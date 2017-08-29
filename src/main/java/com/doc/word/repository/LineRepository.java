package com.doc.word.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doc.word.entity.Line;
import com.doc.word.entity.LineId;

@Repository
public interface LineRepository extends JpaRepository<Line, LineId> {
}