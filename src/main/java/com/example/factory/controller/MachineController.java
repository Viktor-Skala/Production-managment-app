package com.example.factory.controller;

import com.example.factory.dto.MachineDto;
import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import com.example.factory.model.Machine;
import com.example.factory.service.MachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/machine")
@PreAuthorize("hasAuthority('Engineer')")
public class MachineController {

    private MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Machine machine) {
        return ResponseEntity.ok().body(MachineDto.of(machineService.create(machine)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> get(@PathVariable("id") long machineId) {
        return ResponseEntity.ok().body(MachineDto.of(machineService.read(machineId)));
    }

    @PutMapping("/{machine_id}")
    public ResponseEntity<?> update(@RequestBody Machine machine) {
        return ResponseEntity.ok().body(MachineDto.of(machineService.update(machine)));
    }

    @DeleteMapping("/{machine_id}")
    public ResponseEntity<?> delete(@PathVariable("machine_id") long machineId) {
        machineService.delete(machineId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Worker')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(machineService.getAll().stream()
                .map(m -> MachineDto.of(m))
                .collect(Collectors.toList()));
    }
}
