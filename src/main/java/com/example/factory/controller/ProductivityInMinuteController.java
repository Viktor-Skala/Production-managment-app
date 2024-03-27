package com.example.factory.controller;

import com.example.factory.dto.productivityInMinute.ProductivityInMinuteFilterDto;
import com.example.factory.dto.productivityInMinute.ProductivityInMinuteResponseDto;
import com.example.factory.dto.stoppage.*;
import com.example.factory.service.MachineService;
import com.example.factory.service.ProductService;
import com.example.factory.service.ProductivityInMinuteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productivity_in_minute")
@PreAuthorize("hasAuthority('Admin')")
public class ProductivityInMinuteController {

    private ProductivityInMinuteService productivityInMinuteService;

    public ProductivityInMinuteController(ProductivityInMinuteService productivityInMinuteService) {
        this.productivityInMinuteService = productivityInMinuteService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long productivityId) {
            productivityInMinuteService.delete(productivityId);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public List<ProductivityInMinuteResponseDto> getAll() {
        return productivityInMinuteService.getAll().stream()
                .map(p -> ProductivityInMinuteResponseDto.of(p))
                .collect(Collectors.toList());
    }

    @PostMapping("/all_filtered")
    public List<ProductivityInMinuteResponseDto> getAllFiltered(@RequestBody ProductivityInMinuteFilterDto productivityInMinuteFilterDto) {
        return productivityInMinuteService.getAllFiltered(productivityInMinuteFilterDto).stream()
                .map(p -> ProductivityInMinuteResponseDto.of(p))
                .collect(Collectors.toList());
    }

    @PostMapping("/all_by_criteria_paged")
    public Page<ProductivityInMinuteResponseDto> getAllFilteredPaged(@RequestBody ProductivityInMinuteFilterDto productivityInMinuteFilterDto,
                                                         Pageable pageable) {
        return productivityInMinuteService.getAllFilteredPaged(productivityInMinuteFilterDto, pageable)
                .map(p -> ProductivityInMinuteResponseDto.of(p));
    }
}
