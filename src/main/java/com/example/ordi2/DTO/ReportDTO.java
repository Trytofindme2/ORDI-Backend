package com.example.ordi2.DTO;

import java.util.UUID;

public class ReportDTO
{
    private UUID reportedBy;
    private UUID receipe;
    private String reportReason;

    public ReportDTO(){};

    public ReportDTO(UUID reportedBy, UUID receipe, String reportReason) {
        this.reportedBy = reportedBy;
        this.receipe = receipe;
        this.reportReason = reportReason;
    }

    public UUID getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(UUID reportedBy) {
        this.reportedBy = reportedBy;
    }

    public UUID getReceipe() {
        return receipe;
    }

    public void setReceipe(UUID receipe) {
        this.receipe = receipe;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }
}
