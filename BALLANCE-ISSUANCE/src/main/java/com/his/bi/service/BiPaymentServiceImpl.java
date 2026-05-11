package com.his.bi.service;

import com.his.bi.dto.BiPaymentDto;
import com.his.bi.entity.BiPaymentEntity;
import com.his.bi.repository.BiPaymentRepository;
import com.his.bi.entity.EdEligEntity;   // from ED module
import com.his.bi.repository.EdEligRepository;
import com.his.bi.service.BiPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class BiPaymentServiceImpl implements BiPaymentService {

    @Autowired
    private BiPaymentRepository biRepo;

    @Autowired
    private EdEligRepository edRepo;

    @Override
    public List<BiPaymentDto> getAllPayments() {
        return biRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 2 1 * ?") // runs 2 AM on 1st of every month
    //@Scheduled(cron = "0 */2 * * * ?") // Postman ke bina hi har 2 minute me automatic Excel file generate karke dikhayega.
    public void generateMonthlyPayments() {
        List<EdEligEntity> approvedCitizens = edRepo.findByPlanStatus("APPROVED");

        List<BiPaymentEntity> payments = approvedCitizens.stream().map(elig -> {
            BiPaymentEntity payment = new BiPaymentEntity();
            payment.setCaseNum(elig.getCaseNum());
            payment.setEligId(elig.getEligId());
            payment.setPlanName(elig.getPlanName());
            payment.setBenefitAmt(elig.getBenefitAmt());
            payment.setPaymentDate(LocalDate.now());
            payment.setStatus("SUCCESS");
            payment.setFileName("Monthly_Payments_" + LocalDate.now() + ".xlsx");
            return payment;
        }).collect(Collectors.toList());

        biRepo.saveAll(payments);
        exportToExcel(payments);
    }

    private void exportToExcel(List<BiPaymentEntity> payments) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Payments");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("CASE_NUM");
            header.createCell(1).setCellValue("ELIG_ID");
            header.createCell(2).setCellValue("PLAN_NAME");
            header.createCell(3).setCellValue("BENEFIT_AMT");
            header.createCell(4).setCellValue("PAYMENT_DATE");
            header.createCell(5).setCellValue("STATUS");

            int rowNum = 1;
            for (BiPaymentEntity p : payments) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(p.getCaseNum());
                row.createCell(1).setCellValue(p.getEligId());
                row.createCell(2).setCellValue(p.getPlanName());
                row.createCell(3).setCellValue(p.getBenefitAmt());
                row.createCell(4).setCellValue(p.getPaymentDate().toString());
                row.createCell(5).setCellValue(p.getStatus());
            }

            try (FileOutputStream fos = new FileOutputStream("D:/HIS/Sharepoint/BI_Payments/Monthly_Payments_" + LocalDate.now() + ".xlsx")) {
                workbook.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BiPaymentDto convertToDto(BiPaymentEntity entity) {
        BiPaymentDto dto = new BiPaymentDto();
        dto.setPaymentId(entity.getPaymentId());
        dto.setCaseNum(entity.getCaseNum());
        dto.setEligId(entity.getEligId());
        dto.setPlanName(entity.getPlanName());
        dto.setBenefitAmt(entity.getBenefitAmt());
        dto.setPaymentDate(entity.getPaymentDate());
        dto.setStatus(entity.getStatus());
        dto.setFileName(entity.getFileName());
        return dto;
    }
}
