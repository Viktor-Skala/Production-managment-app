package com.example.factory.model.stoppage;

import com.example.factory.dto.stoppage.StoppageCreateDto;
import com.example.factory.model.Product;
import com.example.factory.model.Machine;
import com.example.factory.model.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data @Builder
@AllArgsConstructor
public class Stoppage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Duration duration;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Employee owner;
    @ManyToOne
    @JoinColumn(name = "sub_type_id")
    private SubTypeStoppage subTypeStoppage;
    @ManyToOne
    @JoinColumn(name = "base_type_id")
    private BaseTypeStoppage baseTypeStoppage;

    public Stoppage() {
        startDate = LocalDateTime.now();
    }
}
