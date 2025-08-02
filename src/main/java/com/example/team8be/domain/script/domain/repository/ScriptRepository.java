package com.example.team8be.domain.script.domain.repository;

import com.example.team8be.domain.script.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
}
