package com.codingsense.sender.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codingsense.sender.model.AppConfig;
@Repository
public interface AppConfigRepository extends JpaRepository<AppConfig, Integer>{
}
