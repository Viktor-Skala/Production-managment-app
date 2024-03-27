package com.example.factory.dto.stoppage;

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
public class StoppageCreationResponseDto {

    private Long id;
    private String startDate;
    private Long productId;
    private String productName;
    private Long machineId;
    private String machineName;

    public static StoppageCreationResponseDto of(Stoppage stoppage) {
        return StoppageCreationResponseDto.builder()
                .id(stoppage.getId())
                .startDate(stoppage.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .productId(stoppage.getProduct().getId())
                .productName(stoppage.getProduct().getName() + " " + stoppage.getProduct().getNumbersInPack())
                .machineId(stoppage.getMachine().getId())
                .machineName(stoppage.getMachine().getName())
                .build();
    }
}
