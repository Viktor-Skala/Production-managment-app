package com.example.factory.dto;

import com.example.factory.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private long id;
    private String productName;
    private int numbersInPack;
    private long expectedProductivity;
    private long machineId;
    private String machineName;

    public static ProductResponseDto of(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getName() + " " + product.getNumbersInPack())
                .numbersInPack(product.getNumbersInPack())
                .expectedProductivity(product.getExpectedProductivity())
                .machineId(product.getMachine().getId())
                .machineName(product.getMachine().getName())
                .build();
    }
}
