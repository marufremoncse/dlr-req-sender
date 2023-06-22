package com.codingsense.sender.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.codingsense.sender.model.AppConfig;

public interface AppConfigRepository extends JpaRepository<AppConfig, Integer>{
}
