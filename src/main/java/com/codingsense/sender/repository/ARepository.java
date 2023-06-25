package com.codingsense.sender.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codingsense.sender.model.A;

public interface ARepository extends JpaRepository<A, Long> {
	List<A> findByStatus(char status);

	@Override
	long count();
}
