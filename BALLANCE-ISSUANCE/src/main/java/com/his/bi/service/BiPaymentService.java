package com.his.bi.service;

import com.his.bi.dto.BiPaymentDto;
import java.util.List;

public interface BiPaymentService {
    List<BiPaymentDto> getAllPayments();
    void generateMonthlyPayments();
}
