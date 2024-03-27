package com.example.factory.dto.productivityInHour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductivityInHourDiagramResponseDto {
    private String productName;
    private List<String> dateTimes;
    private List<Integer> prodInHours;
}
