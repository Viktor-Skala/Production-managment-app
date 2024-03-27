package com.example.factory.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Data @Builder
@Entity
public class ProductivityInHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private int prodInHour;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @OneToMany(mappedBy = "productivityInHour")
    private List<ProductivityInMinute> productivityInMinutes;
}
