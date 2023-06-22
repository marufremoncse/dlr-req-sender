package com.codingsense.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingsense.sender.model.Dump;

public interface DumpRepository extends JpaRepository<Dump, Long> {

}
