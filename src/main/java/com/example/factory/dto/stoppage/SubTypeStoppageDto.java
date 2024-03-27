package com.example.factory.dto.stoppage;

import com.example.factory.model.stoppage.SubTypeStoppage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class SubTypeStoppageDto {

    private long id;
    private String name;
    private Long baseTypeStoppageId;
    private String baseTypeStoppageName;

    public static SubTypeStoppageDto of(SubTypeStoppage stoppage) {
        return SubTypeStoppageDto.builder()
                .id(stoppage.getId())
                .name(stoppage.getName())
                .baseTypeStoppageId(stoppage.getBaseTypeStoppage().getId())
                .baseTypeStoppageName(stoppage.getBaseTypeStoppage().getName())
                .build();
    }
}
