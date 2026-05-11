package com.his.dc.dto;

import lombok.Data;

import java.time.LocalDate;

    @Data
    public class DcChildDto {
        private String childName;
        private LocalDate childDob;
        private String gender;
        private String schoolName;
    }


