package com.example.factory.dto.productivityInMinute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductivityInMinuteFilterDto {
    private Long id;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private Long productId;
    private Long machineId;
    private Long prodInMinuteFrom;
    private Long prodInMinuteTo;
}
