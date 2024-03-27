package com.example.factory.model.stoppage;

import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import com.example.factory.dto.stoppage.SubTypeStoppageDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class SubTypeStoppage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private BaseTypeStoppage baseTypeStoppage;
    @OneToMany(mappedBy = "subTypeStoppage")
    private List<Stoppage> stoppages;

    public SubTypeStoppage(long id) {
        this.id = id;
    }

    public static SubTypeStoppage of(SubTypeStoppageDto stoppageDto) {
        return SubTypeStoppage.builder()
                .id(stoppageDto.getId())
                .name(stoppageDto.getName())
                .build();
    }
}
