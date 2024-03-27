package com.example.factory.model;

import com.example.factory.dto.MachineDto;
import com.example.factory.model.stoppage.Stoppage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<Product> products;
    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<Stoppage> stoppages;
    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<ProductivityInMinute> productivityInMinutes;
    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<ProductivityInHour> productivityInHours;

    public Machine(long id) {
        this.id = id;
    }

    public static Machine of(MachineDto machineDto) {
        return Machine.builder()
                .name(machineDto.getName())
                .build();
    }
}
