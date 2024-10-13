package com.nhnacademy.minidooray.model.rest.milestone;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record MilestoneCreateRequest(
        String title,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate endDate
) {}
