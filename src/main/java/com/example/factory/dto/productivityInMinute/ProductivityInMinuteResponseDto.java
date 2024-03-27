package com.example.factory.dto.productivityInMinute;

import com.example.factory.model.ProductivityInMinute;
import com.example.factory.model.stoppage.Stoppage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductivityInMinuteResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private Long machineId;
    private String machineName;
    private String dateTime;
    private Integer prodInMinute;
    private String hourDateTime;

    public static ProductivityInMinuteResponseDto of(ProductivityInMinute productivity) {
        return ProductivityInMinuteResponseDto.builder()
                .id(productivity.getId())
                .productId(productivity.getProduct().getId())
                .productName(productivity.getProduct().getName() + " " + productivity.getProduct().getNumbersInPack())
                .machineId(productivity.getMachine().getId())
                .machineName(productivity.getMachine().getName())
                .dateTime(productivity.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .prodInMinute(productivity.getProdInMinute())
                .hourDateTime(productivity.getProductivityInHour().getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .build();
    }
}
