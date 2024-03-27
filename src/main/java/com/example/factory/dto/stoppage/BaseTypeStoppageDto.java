package com.example.factory.dto.stoppage;

import com.example.factory.model.stoppage.BaseTypeStoppage;
import com.example.factory.model.stoppage.Stoppage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BaseTypeStoppageDto {

    private Long id;
    private String name;

    public static BaseTypeStoppageDto of(BaseTypeStoppage stoppage) {
        return BaseTypeStoppageDto.builder()
                .id(stoppage.getId())
                .name(stoppage.getName())
                .build();
    }
}
