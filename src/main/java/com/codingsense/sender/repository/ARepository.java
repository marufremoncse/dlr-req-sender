package com.codingsense.sender.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingsense.sender.model.A;
import java.util.List;


public interface ARepository extends JpaRepository<A, Long>{
	List<A> findByStatus(char status);
}
