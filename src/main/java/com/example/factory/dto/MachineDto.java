package com.example.factory.dto;

import com.example.factory.model.Machine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class MachineDto {

    private Long id;
    private String name;

    public static MachineDto of(Machine machine) {
        return MachineDto.builder()
                .id(machine.getId())
                .name(machine.getName())
                .build();
    }
}
