package com.example.ordi2.service;


import com.example.ordi2.DTO.RecipeWithReportsDTO;
import com.example.ordi2.DTO.ReportDTO;
import com.example.ordi2.DTO.ReportResponseDTO;
import com.example.ordi2.helper.customException;
import com.example.ordi2.model.Receipe;
import com.example.ordi2.model.Report;
import com.example.ordi2.model.User;
import com.example.ordi2.repo.receipeRepo;
import com.example.ordi2.repo.reportRepo;
import com.example.ordi2.repo.userRepo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class reportService
{
    private final reportRepo reportRepo;

    private final userRepo userRepo;

    private final receipeRepo receipeRepo;

    public reportService(reportRepo reportRepo , userRepo userRepo , receipeRepo receipeRepo){
        this.reportRepo = reportRepo;
        this.receipeRepo =receipeRepo;
        this.userRepo = userRepo;
    }

    public Report submitReport(UUID userid , UUID postId , String reportReason){
        User user = userRepo.findById(userid)
                .orElseThrow(() -> new customException("User not found"));

        Receipe receipe = receipeRepo.findById(postId)
                .orElseThrow(() -> new customException("Recipe not found"));

        Report report = new Report();
        report.setReportedBy(user);
        report.setReceipe(receipe);
        report.setReportReason(reportReason);
        return reportRepo.save(report);
    }

    public Page<ReportResponseDTO> getReportsDTO(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return reportRepo.findAll(pageable)
                .map(r -> new ReportResponseDTO(
                        r.getId(),
                        r.getReportReason(),
                        r.getReceipe().getId(),
                        r.getReceipe().getTitle(),
                        r.getReportedBy().getId(),
                        r.getReportedBy().getName(),
                        r.getReceipe().getUser().getId(),
                        r.getReceipe().getUser().getName(),
                        r.getReceipe().getPostAt(),
                        r.getReportAt()
                ));
    }


    @Transactional
    public RecipeWithReportsDTO getRecipeWithReports(UUID recipeId) {
        Receipe receipe = receipeRepo.findByIdWithReportsAndUsers(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Map reports
        List<RecipeWithReportsDTO.ReportInfo> reports = receipe.getReports().stream()
                .map(report -> new RecipeWithReportsDTO.ReportInfo(
                        report.getId(),                          // reportId
                        report.getReportedBy().getId(),          // reporter userId
                        report.getReportedBy().getName(),        // reporter name
                        report.getReportedBy().getProfile_URl(), // reporter profile picture
                        report.getReportReason(),                // reason
                        report.getReportAt()                     // timestamp
                ))
                .collect(Collectors.toList());

        // Map recipe DTO
        RecipeWithReportsDTO dto = new RecipeWithReportsDTO();
        dto.setRecipeId(receipe.getId());
        dto.setTitle(receipe.getTitle());
        dto.setDescription(receipe.getDescription());
        dto.setDifficulty(receipe.getDifficulty());
        dto.setIngredients(receipe.getIngredients());
        dto.setPreparationTime(receipe.getPreparationTime());
        dto.setCookingTime(receipe.getCookingTime());
        dto.setPostAt(receipe.getPostAt());
        dto.setImageUrls(receipe.getImageUrls());
        dto.setUserId(receipe.getUser().getId());
        dto.setName(receipe.getUser().getName());
        dto.setUserProfileUrl(receipe.getUser().getProfile_URl());
        dto.setReports(reports);

        return dto;
    }
}
