package com.example.factory.controller;

import com.example.factory.dto.ProductDto;
import com.example.factory.dto.ProductResponseDto;
import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import com.example.factory.model.Product;
import com.example.factory.service.ProductService;
import com.example.factory.service.MachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@PreAuthorize("hasAuthority('Engineer')")
public class ProductController {

    private ProductService productService;
    private MachineService machineService;

    public ProductController(ProductService productService, MachineService machineService) {
        this.productService = productService;
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto productDto) {
        Product product = Product.of(productDto);
        product.setMachine(machineService.read(productDto.getMachineId()));
            return ResponseEntity.ok().body(ProductResponseDto.of(productService.create(product)));
    }

    @GetMapping("/{product_id}")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> get(@PathVariable("product_id") long productId) {
            return ResponseEntity.ok().body(ProductResponseDto.of(productService.read(productId)));
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<?> update(@PathVariable("product_id") long productId,
                                     @RequestBody ProductDto productDto) {
        Product product = Product.of(productDto);
        product.setId(productId);
        product.setMachine(machineService.read(productDto.getMachineId()));
            return ResponseEntity.ok().body(ProductResponseDto.of(productService.update(product)));
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> delete(@PathVariable("product_id") long productId) {
            productService.delete(productId);
            return ResponseEntity.ok().build();
    }

    @GetMapping("/allDto")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<List<ProductResponseDto>> getAllDto() {
        return ResponseEntity.ok().body(productService.getAll().stream()
                .map(p -> ProductResponseDto.of(p))
                .collect(Collectors.toList()));
    }

    @GetMapping("/all/byMachineId/{machine_id}")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> getAllByMachineId(@PathVariable("machine_id") long machineId) {
            return ResponseEntity.ok().body(productService.getAllByMachineId(machineId));
    }
}
