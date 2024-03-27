package com.example.factory.dto.productivityInHour;

import com.example.factory.model.ProductivityInHour;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductivityInHourResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private Long machineId;
    private String machineName;
    private String dateTimeString;
    private LocalDateTime dateTime;
    private Integer prodInHour;

    public static ProductivityInHourResponseDto of(ProductivityInHour productivity) {
        return ProductivityInHourResponseDto.builder()
                .id(productivity.getId())
                .productId(productivity.getProduct().getId())
                .productName(productivity.getProduct().getName() + " " + productivity.getProduct().getNumbersInPack())
                .machineId(productivity.getMachine().getId())
                .machineName(productivity.getMachine().getName())
                .dateTimeString(productivity.getDate().toString())
                .dateTime(productivity.getDate())
                .prodInHour(productivity.getProdInHour())
                .build();
    }
}
