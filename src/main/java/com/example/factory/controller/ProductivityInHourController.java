package com.example.factory.controller;

import com.example.factory.dto.productivityInHour.ProductivityInHourDiagramResponseDto;
import com.example.factory.dto.productivityInHour.ProductivityInHourFilterDto;
import com.example.factory.dto.productivityInHour.ProductivityInHourResponseDto;
import com.example.factory.dto.productivityInMinute.ProductivityInMinuteFilterDto;
import com.example.factory.dto.productivityInMinute.ProductivityInMinuteResponseDto;
import com.example.factory.service.MachineService;
import com.example.factory.service.ProductService;
import com.example.factory.service.ProductivityInHourService;
import com.example.factory.service.ProductivityInMinuteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/productivity_in_hour")
@PreAuthorize("hasAuthority('Admin')")
public class ProductivityInHourController {

    private ProductivityInHourService productivityInHourService;

    public ProductivityInHourController(ProductivityInHourService productivityInHourService) {
        this.productivityInHourService = productivityInHourService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(ProductivityInHourResponseDto.of(productivityInHourService.getProductivityById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long productivityId) {
            productivityInHourService.delete(productivityId);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Engineer')")
    public List<ProductivityInHourResponseDto> getAll() {
        return productivityInHourService.getAll().stream()
                .map(p -> ProductivityInHourResponseDto.of(p))
                .collect(Collectors.toList());
    }

    @PostMapping("/all_filtered")
    @PreAuthorize("hasAuthority('Engineer')")
    public List<ProductivityInHourResponseDto> getAllFiltered(@RequestBody ProductivityInHourFilterDto productivityInHourFilterDto) {
        return productivityInHourService.getAllFiltered(productivityInHourFilterDto).stream()
                .map(p -> ProductivityInHourResponseDto.of(p))
                .collect(Collectors.toList());
    }

    @PostMapping("/diagram_all_filtered")
    @PreAuthorize("hasAuthority('Worker')")
    public List<ProductivityInHourDiagramResponseDto> getAllFilteredDiagram(@RequestBody ProductivityInHourFilterDto productivity) {
        return productivityInHourService.getAllFilteredDiagram(productivity);
    }
    @PostMapping("/all_by_criteria_paged")
    @PreAuthorize("hasAuthority('Worker')")
    public Page<ProductivityInHourResponseDto> getAllFilteredPaged(@RequestBody ProductivityInHourFilterDto productivityInHourFilterDto,
                                                         Pageable pageable) {
        return productivityInHourService.getAllFilteredPaged(productivityInHourFilterDto, pageable)
                .map(p -> ProductivityInHourResponseDto.of(p));
    }
}
