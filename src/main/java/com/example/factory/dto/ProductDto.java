package com.example.factory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductDto {

    private String name;
    private Long machineId;
    private Integer numbersInPack;
    private Long expectedProductivity;

}
