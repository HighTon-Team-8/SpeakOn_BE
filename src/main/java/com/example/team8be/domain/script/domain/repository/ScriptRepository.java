package com.example.team8be.domain.script.domain.repository;

import com.example.team8be.domain.script.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByMaterialIdOrderBySlideNumberAsc(Long materialId);
}
