package com.example.factory.service.stoppage;

import com.example.factory.dto.stoppage.StoppageFilterDto;
import com.example.factory.model.stoppage.Stoppage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoppageService {

    Stoppage create(Stoppage stoppage);
    Stoppage read(long id);
    Stoppage update(Stoppage stoppage);
    void delete(long machineId);
    List<Stoppage> getAll();
    List<Stoppage> getAllFiltered(StoppageFilterDto stoppage);
    Page<Stoppage> findEntitiesByDynamicCriteriaPaged(StoppageFilterDto stoppage, Pageable pageable);
    List<Stoppage> findEntitiesByDynamicCriteria(StoppageFilterDto stoppage);
    List<Stoppage> findNotFinishedFilteredStoppage(StoppageFilterDto stoppage);
}
