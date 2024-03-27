package com.example.factory.model;

import com.example.factory.dto.ProductDto;
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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer numbersInPack;
    @Column(nullable = false)
    private long expectedProductivity;
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductivityInMinute> productivityInMinutes;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductivityInHour> productivityInHours;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Stoppage> stoppages;

    public Product(long id) {
        this.id = id;
    }

    public static Product of(ProductDto request) {
        return Product.builder()
                .name(request.getName())
                .numbersInPack(request.getNumbersInPack())
                .expectedProductivity(request.getExpectedProductivity())
                .build();
    }
}
