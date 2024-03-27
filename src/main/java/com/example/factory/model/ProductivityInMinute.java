package com.example.factory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data @Builder
@Entity
public class ProductivityInMinute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private int prodInMinute;
    @ManyToOne
    @JoinColumn(name = "productivityInHour_id")
    private ProductivityInHour productivityInHour;
}
