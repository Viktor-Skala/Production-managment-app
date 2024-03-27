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
public class StoppageResponseDto {

    private Long id;
    private String startDate;
    private String endDate;
    private String duration;
    private Long durationInSec;
    private Long productId;
    private String productName;
    private Long machineId;
    private String machineName;
    private Long ownerId;
    private String ownerName;
    private Long subTypeStoppageId;
    private String subTypeStoppageName;
    private Long baseTypeStoppageId;
    private String baseTypeStoppageName;

    public static StoppageResponseDto of(Stoppage stoppage) {
        return StoppageResponseDto.builder()
                .id(stoppage.getId())
                .startDate(stoppage.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .endDate(stoppage.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                .duration(stoppage.getDuration().toDaysPart() + "д " + stoppage.getDuration().toHoursPart() + "ч:" + stoppage.getDuration().toMinutesPart() + "м:" + stoppage.getDuration().toSecondsPart() + "с")
                .durationInSec(stoppage.getDuration().toSeconds())
                .productId(stoppage.getProduct().getId())
                .productName(stoppage.getProduct().getName() + " " + stoppage.getProduct().getNumbersInPack())
                .machineId(stoppage.getMachine().getId())
                .machineName(stoppage.getMachine().getName())
//                .ownerId(stoppage.getOwner().getId())
//                .ownerName(stoppage.getOwner().getName())
                .subTypeStoppageId(stoppage.getSubTypeStoppage().getId())
                .subTypeStoppageName(stoppage.getSubTypeStoppage().getName())
                .baseTypeStoppageId(stoppage.getBaseTypeStoppage().getId())
                .baseTypeStoppageName(stoppage.getBaseTypeStoppage().getName())
                .build();
    }
}
