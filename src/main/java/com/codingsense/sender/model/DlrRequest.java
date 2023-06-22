package com.codingsense.sender.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@MappedSuperclass
@Data
public class DlrRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;

	@Column(name = "message_id")
	public String messageId;

	@Column(name = "sent_date")
	public String sentDate;

	@Column(name = "done_date")
	public String doneDate;

	@Column(name = "message_status")
	public String messageStatus;

	@Column(name = "gsm_error")
	public String gsmError;

	@Column(name = "price")
	public String price;

	@Column(name = "pdu_count")
	public String pduCount;

	@Column(name = "short_message")
	public String shortMessage;

	@Column(name = "mobile")
	public String mobile;

	@Column(name = "status")
	@ColumnDefault("'N'")
	public char status;

	@Column(name = "created_at")
	public LocalDateTime createdAt;

	@Column(name = "updated_at")
	public LocalDateTime updatedAt;

	@Lob
	@Column(name = "api_response", columnDefinition = "TEXT")
	public String apiResponse;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
