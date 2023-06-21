package com.codingsense.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingsense.sender.model.B;

public interface BRepository extends JpaRepository<B, Long>{
	List<B> findByStatus(char status);
}
