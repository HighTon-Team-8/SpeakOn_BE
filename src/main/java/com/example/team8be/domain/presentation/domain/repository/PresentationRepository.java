package com.example.team8be.domain.presentation.domain.repository;

import com.example.team8be.domain.presentation.domain.Presentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresentationRepository extends CrudRepository<Presentation, Long> {
}
