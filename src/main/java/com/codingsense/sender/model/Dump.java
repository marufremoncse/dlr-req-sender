package com.codingsense.sender.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dump extends DlrRequest {
	@SuppressWarnings("unused")
	private long id;
}
