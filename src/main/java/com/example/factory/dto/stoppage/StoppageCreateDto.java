package com.example.factory.dto.stoppage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StoppageCreateDto {

    private Long productId;
    private Long machineId;
    private Long subTypeStoppageId;
    private Long baseTypeStoppageId;
}
