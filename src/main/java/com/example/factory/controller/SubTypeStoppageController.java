package com.example.factory.controller;

import com.example.factory.dto.ProductResponseDto;
import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import com.example.factory.dto.stoppage.SubTypeStoppageDto;
import com.example.factory.service.stoppage.BaseTypeStoppageService;
import com.example.factory.service.stoppage.SubTypeStoppageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sub_type_stoppage")
@PreAuthorize("hasAuthority('Engineer')")
public class SubTypeStoppageController {

    private SubTypeStoppageService stoppageService;

    public SubTypeStoppageController(SubTypeStoppageService stoppageService) {
        this.stoppageService = stoppageService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SubTypeStoppageDto stoppageDto) {
        return ResponseEntity.ok().body(SubTypeStoppageDto.of(stoppageService.create(stoppageDto)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> get(@PathVariable("id") long stoppageId) {
        return ResponseEntity.ok().body(SubTypeStoppageDto.of(stoppageService.read(stoppageId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long stoppageId,
                                    @RequestBody SubTypeStoppageDto stoppageDto) {
        stoppageDto.setId(stoppageId);
        return ResponseEntity.ok().body(SubTypeStoppageDto.of(stoppageService.update(stoppageDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long stoppageId) {
        stoppageService.delete(stoppageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public List<SubTypeStoppageDto> getAll() {
        return stoppageService.getAll().stream()
                .map(s -> SubTypeStoppageDto.of(s))
                .collect(Collectors.toList());
    }

    @GetMapping("/all/by_base_stoppage/{id}")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> getAllByMachineId(@PathVariable("id") long baseTypeId) {
        return ResponseEntity.ok().body(stoppageService.getAllByBaseTypeStoppage(baseTypeId).stream()
                .map(s -> SubTypeStoppageDto.of(s))
                .collect(Collectors.toList()));
    }
}
