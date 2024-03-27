package com.example.factory.service;

import com.example.factory.dto.ProductResponseDto;
import com.example.factory.model.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product read(long id);
    Product update(Product product);
    void delete(long productId);
    List<Product> getAll();
    List<ProductResponseDto> getAllByMachineId(long machineId);
}
