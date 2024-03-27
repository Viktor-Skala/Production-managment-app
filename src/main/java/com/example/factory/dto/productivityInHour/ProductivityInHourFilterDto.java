package com.example.factory.dto.productivityInHour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductivityInHourFilterDto {
    private Long id;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private Long productId;
    private Long machineId;
    private Long prodInHourFrom;
    private Long prodInHourTo;
}
