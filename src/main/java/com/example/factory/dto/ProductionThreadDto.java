package com.example.factory.dto;

import com.example.factory.ProductGenerationThread;
import com.example.factory.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class ProductionThreadDto {

    private String threadName;
    private String state;
    private long productId;
    private long machineId;

    public static ProductionThreadDto of(ProductGenerationThread thread) {
        return ProductionThreadDto.builder()
                .threadName(thread.getName())
                .state(thread.getState().toString())
                .productId(thread.getProduct().getId())
                .machineId(thread.getProduct().getMachine().getId())
                .build();
    }
}
