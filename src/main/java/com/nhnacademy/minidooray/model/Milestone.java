package com.nhnacademy.minidooray.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Milestone {
    private long id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
}
