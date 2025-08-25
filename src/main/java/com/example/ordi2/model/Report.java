package com.example.ordi2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String reportReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipe_id")
    private Receipe receipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User reportedBy;

    private LocalDateTime reportAt;

    public Report(){};

    public Report(User reportedBy, Receipe receipe, String reportReason , LocalDateTime reportAt) {
        this.reportedBy = reportedBy;
        this.receipe = receipe;
        this.reportReason = reportReason;
        this.reportAt = reportAt;
    }

    @PrePersist
    public void setPostAtBeforePersist() {
        this.reportAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Receipe getReceipe() {
        return receipe;
    }

    public void setReceipe(Receipe receipe) {
        this.receipe = receipe;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public LocalDateTime getReportAt() {
        return reportAt;
    }

    public void setReportAt(LocalDateTime reportAt) {
        this.reportAt = reportAt;
    }
}
