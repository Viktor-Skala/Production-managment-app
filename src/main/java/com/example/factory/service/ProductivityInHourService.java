package com.example.factory.service;

import com.example.factory.dto.productivityInHour.ProductivityInHourDiagramResponseDto;
import com.example.factory.dto.productivityInHour.ProductivityInHourFilterDto;
import com.example.factory.model.Product;
import com.example.factory.model.ProductivityInHour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductivityInHourService {

    ProductivityInHour create(ProductivityInHour productivityInHour);
    ProductivityInHour getProductivity(Product product, LocalDateTime currentTime);
    ProductivityInHour getProductivityById(Long productivityId);
    List<ProductivityInHour> getAll();
    ProductivityInHour update(ProductivityInHour productivityInHour);
    Optional<ProductivityInHour> findProductivityByDateAndProduct(ProductivityInHour productivityInHour);
    List<ProductivityInHour> getAllFiltered(ProductivityInHourFilterDto productivity);
    Page<ProductivityInHour> getAllFilteredPaged(ProductivityInHourFilterDto productivity, Pageable pageable);
    List<ProductivityInHourDiagramResponseDto> getAllFilteredDiagram(ProductivityInHourFilterDto productivity);
    void delete(long productivityId);
}
