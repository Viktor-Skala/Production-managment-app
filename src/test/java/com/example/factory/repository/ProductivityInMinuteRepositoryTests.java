package com.example.factory.repository;

import com.example.factory.model.Product;
import com.example.factory.model.ProductivityInMinute;
import com.example.factory.repositoty.ProductRepository;
import com.example.factory.repositoty.ProductivityInMinuteRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

@SpringBootTest
public class ProductivityInMinuteRepositoryTests {

    @Autowired
    private ProductivityInMinuteRepository productivityRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findByDateAndProduct_IdTest() {
//        Product product = productRepository.findById(1L).get();
//        LocalDateTime time = LocalDateTime.of(2023, Month.DECEMBER,8, 17, 7);
//        ProductivityInMinute expected = ProductivityInMinute.builder()
//                .product(product)
//                .prodInMinute(14)
//                .date(time)
//                .build();
//        ProductivityInMinute actual = productivityRepository.findByDateAndProduct_Id(time, product.getId()).get();
//        assertEquals(actual.getProdInMinute(), 14);
//        assertEquals(actual.getId(), 304);
    }
}
