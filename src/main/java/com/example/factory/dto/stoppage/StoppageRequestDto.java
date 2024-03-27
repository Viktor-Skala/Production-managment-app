package com.example.factory.dto.stoppage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StoppageRequestDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long productId;
    private Long machineId;
    private Long ownerId;
    private Long subTypeStoppageId;
    private Long baseTypeStoppageId;
}
