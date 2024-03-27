package com.example.factory.model.stoppage;

import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class BaseTypeStoppage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "baseTypeStoppage")
    private List<SubTypeStoppage> subTypeStoppages;
    @OneToMany(mappedBy = "baseTypeStoppage")
    private List<Stoppage> stoppages;

    public BaseTypeStoppage(long id) {
        this.id = id;
    }

    public static BaseTypeStoppage of(BaseTypeStoppageDto stoppageDto) {
        return BaseTypeStoppage.builder()
                .id(stoppageDto.getId())
                .name(stoppageDto.getName())
                .build();
    }
}
