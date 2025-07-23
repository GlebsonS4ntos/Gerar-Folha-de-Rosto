package com.Glebson.FolhadeRosto.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Glebson.FolhadeRosto.dto.AllergyDto;
import com.Glebson.FolhadeRosto.dto.CitizenDto;
import com.Glebson.FolhadeRosto.dto.ConditionDto;
import com.Glebson.FolhadeRosto.dto.DrugDto;
import com.Glebson.FolhadeRosto.service.CitizenService;
import com.Glebson.FolhadeRosto.service.PdfService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/citizen")
public class CitizenController {
        private final CitizenService relatorioService;
        private final PdfService pdfService;

    public CitizenController(CitizenService relatorioService, PdfService pdfService) {
        this.relatorioService = relatorioService;
        this.pdfService = pdfService;
    }

    @GetMapping()
    public ResponseEntity getCitizenInfo(@RequestHeader(required = false) String medicalRecordId, @RequestHeader(required = false) String citizenCode) {
        if((medicalRecordId == null && citizenCode == null) || (medicalRecordId != null && citizenCode != null)) return ResponseEntity.badRequest().build();
        
        try {
            if (medicalRecordId != null) {
                Long ctCode = this.relatorioService.getCitizenCodeByMedicalRecordAsync(Long.parseLong(medicalRecordId.replace("\"", ""))).get();

                CompletableFuture<CitizenDto> citizenInfo = this.relatorioService.getCitizenInfoAsync(ctCode);
                CompletableFuture<List<ConditionDto>> conditions = this.relatorioService.getConditionsAsync(ctCode);
                CompletableFuture<List<DrugDto>> drugs = this.relatorioService.getActiveDrugsAsync(ctCode);
                CompletableFuture<List<AllergyDto>> allergies = this.relatorioService.getAllergiesAsync(ctCode);

                CompletableFuture.allOf(citizenInfo, conditions, drugs, allergies).join();

                return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=foDeResto.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfService.generateFolhaDeRostoPdf(
                        citizenInfo.get(),
                        conditions.get(),
                        drugs.get(),
                        allergies.get()));
            }

            CompletableFuture<CitizenDto> citizenInfo = this.relatorioService.getCitizenInfoAsync(Long.parseLong(citizenCode.replace("\"", "")));
            CompletableFuture<List<ConditionDto>> conditions = this.relatorioService.getConditionsAsync(Long.parseLong(citizenCode.replace("\"", "")));
            CompletableFuture<List<DrugDto>> drugs = this.relatorioService.getActiveDrugsAsync(Long.parseLong(citizenCode.replace("\"", "")));
            CompletableFuture<List<AllergyDto>> allergies = this.relatorioService.getAllergiesAsync(Long.parseLong(citizenCode.replace("\"", "")));

            CompletableFuture.allOf(citizenInfo, conditions, drugs,allergies).join();

            return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=foDeResto.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfService.generateFolhaDeRostoPdf(
                    citizenInfo.get(),
                    conditions.get(),
                    drugs.get(),
                    allergies.get()));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}