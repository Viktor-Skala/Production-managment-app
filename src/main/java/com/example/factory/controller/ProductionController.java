package com.example.factory.controller;

import com.example.factory.ProductGenerationThread;
import com.example.factory.dto.ProductionManagerDTO;
import com.example.factory.dto.ProductionThreadDto;
import com.example.factory.dto.stoppage.StoppageCreationResponseDto;
import com.example.factory.model.Product;
import com.example.factory.model.State;
import com.example.factory.model.stoppage.Stoppage;
import com.example.factory.service.ProductService;
import com.example.factory.service.MachineService;
import com.example.factory.service.ProductivityInHourService;
import com.example.factory.service.ProductivityInMinuteService;
import com.example.factory.service.stoppage.StoppageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private ProductivityInMinuteService productivityInMinuteService;
    private ProductivityInHourService productivityInHourService;
    private ProductService productService;
    private MachineService machineService;
    private StoppageService stoppageService;
    private ThreadGroup threadGroup;
    private Map<String, ProductGenerationThread> poolProductThreads;

    public ProductionController(ProductivityInMinuteService productivityInMinuteService, ProductivityInHourService productivityInHourService, ProductService productService, MachineService machineService, StoppageService stoppageService, ThreadGroup threadGroup, Map<String, ProductGenerationThread> poolProductThreads) {
        this.productivityInMinuteService = productivityInMinuteService;
        this.productivityInHourService = productivityInHourService;
        this.productService = productService;
        this.machineService = machineService;
        this.stoppageService = stoppageService;
        this.threadGroup = threadGroup;
        this.poolProductThreads = poolProductThreads;
    }

    @GetMapping("/all")
    public List<ProductionThreadDto> allProductions() {
        return poolProductThreads.values().stream()
                .map(s -> ProductionThreadDto.of(s))
                .collect(Collectors.toList());
    }

    @GetMapping("/info/{thread_name}")
    public ResponseEntity<?> getState(@PathVariable("thread_name") String threadName) {
        if (!checkThreadExist(threadName)) {
            return ResponseEntity.internalServerError().body(String.format("Production %s doesn't run",threadName));
        }
        ProductGenerationThread thread = poolProductThreads.get(threadName);
        return ResponseEntity.ok().body(ProductionThreadDto.of(thread));
    }

    @PostMapping("/start")
    public ResponseEntity<?> create(@RequestBody ProductionManagerDTO productionDTO) {
        Product product = productService.read(productionDTO.getProductId());
        String machineName = product.getMachine().getName();
        String threadName = product.getName() + " " + product.getNumbersInPack() + " " + machineName;
        if (checkThreadExist(machineName)) {
            return ResponseEntity.internalServerError().body(String.format("Production %s already run",threadName));
        }
        ProductGenerationThread productionThread = ProductGenerationThread.builder()
                .name(threadName)
                .state(State.RUN)
                .product(product)
                .productivityInMinuteService(productivityInMinuteService)
                .productivityInHourService(productivityInHourService)
                .build();
        poolProductThreads.put(threadName, productionThread);
        Thread thread = new Thread(threadGroup, productionThread, threadName);
        thread.setDaemon(true);
        thread.start();
        return ResponseEntity.ok().body(ProductionThreadDto.of(productionThread));
    }

    private boolean checkThreadExist(String threadName) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        return Arrays.stream(threads)
                .anyMatch(t -> t.getName().contains(threadName));
    }

    @GetMapping("/wait/{thread_name}")
    public ResponseEntity<?> pauseThread(@PathVariable("thread_name") String threadName) {
        if (!checkThreadExist(threadName)) {
            return ResponseEntity.internalServerError().body(String.format("Production %s doesn't run",threadName));
        }
        ProductGenerationThread productionThread = poolProductThreads.get(threadName);
        if (productionThread.getState().toString().equals("PAUSED")) {
            return ResponseEntity.internalServerError().body("Production " + threadName + " already paused");
        }
        Stoppage stoppage = new Stoppage();
        stoppage.setProduct(productService.read(productionThread.getProduct().getId()));
        stoppage.setMachine(machineService.read(productionThread.getProduct().getMachine().getId()));
        var stoppageResponseDto = StoppageCreationResponseDto.of(stoppageService.create(stoppage));
        productionThread.setState(State.PAUSED);
        System.out.println(threadName + " paused");
        return ResponseEntity.ok().body(stoppageResponseDto);
    }

    @GetMapping("/resume/{thread_name}")
    public ResponseEntity<?> notifyThread(@PathVariable("thread_name") String threadName) {
        if (!checkThreadExist(threadName)) {
            return ResponseEntity.internalServerError().body(String.format("Production %s doesn't run",threadName));
        }
        ProductGenerationThread productionThread = poolProductThreads.get(threadName);
        if (productionThread.getState().toString().equals("RUN")) {
            return ResponseEntity.internalServerError().body(String.format("Production %s already run",threadName));
        }
        productionThread.setState(State.RUN);
        System.out.println(threadName + " notify");
        return ResponseEntity.ok().body(ProductionThreadDto.of(productionThread));
    }

    @GetMapping("/finish/{thread_name}")
    public ResponseEntity<?> finishThread(@PathVariable("thread_name") String threadName) throws InterruptedException {
        if (!checkThreadExist(threadName)) {
            return ResponseEntity.internalServerError().body(String.format("Production %s doesn't run",threadName));
        }
        ProductGenerationThread productionThread = poolProductThreads.get(threadName);
        if (productionThread.getState().toString().equals("PAUSED")) {
            return ResponseEntity.internalServerError().body("Production " + threadName + " can't be finished while it paused");
        }
        Thread thread = getThreadByName(threadName);
        productionThread.setState(State.FINISHED);
        poolProductThreads.remove(threadName);
        System.out.println(thread.getName() + " finished");
        return ResponseEntity.ok().body(thread.getName());
    }

    private Thread getThreadByName(String threadName) {
        Thread[] threads = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threads);
        return Arrays.stream(threads)
                .filter(t -> t.getName().equals(threadName))
                .findFirst()
                .get();
    }
}
