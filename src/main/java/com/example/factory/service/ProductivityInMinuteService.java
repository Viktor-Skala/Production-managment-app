package com.example.factory.service;

import com.example.factory.dto.productivityInMinute.ProductivityInMinuteFilterDto;
import com.example.factory.model.Product;
import com.example.factory.model.ProductivityInMinute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductivityInMinuteService {

    ProductivityInMinute create(ProductivityInMinute productivityInMinute);
    ProductivityInMinute getProductivity(Product product, LocalDateTime time);
    ProductivityInMinute getProductivityById(Long productivityId);
    List<ProductivityInMinute> getAll();
    ProductivityInMinute update(ProductivityInMinute productivityInMinute);
    Optional<ProductivityInMinute> findProductivityByDateAndProduct(ProductivityInMinute productivityInMinute);
    List<ProductivityInMinute> getAllFiltered(ProductivityInMinuteFilterDto productivity);
    Page<ProductivityInMinute> getAllFilteredPaged(ProductivityInMinuteFilterDto productivity, Pageable pageable);
    void delete(long productivityId);
}
