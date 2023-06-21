package com.codingsense.sender.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class B implements DlrRequest{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "message_id")
    private String messageId;
	
	@Column(name = "sent_date")
    private String sentDate;
	
	@Column(name = "done_date")
    private String doneDate;
	
	@Column(name = "message_status")
    private String messageStatus;
	
	@Column(name = "gsm_error")
    private String gsmError;
	
	@Column(name = "price")
    private String price;
	
	@Column(name = "pdu_count")
    private String pduCount;
	
	@Column(name = "short_message")
    private String shortMessage;
	
	@Column(name = "mobile")
    private String mobile;
	
	@Column(name = "status")
	@ColumnDefault("'N'")
    private char status;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Lob
    @Column(name = "api_response", columnDefinition = "TEXT")
    private String apiResponse;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
