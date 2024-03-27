package com.example.factory.dto.stoppage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class StoppageFilterDto {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration durationStart;
    private Duration durationEnd;
    private Long productId;
    private Long machineId;
    private Long ownerId;
    private Long subTypeStoppageId;
    private Long baseTypeStoppageId;
    private String sortDirection;
    private String sortBy;
}
