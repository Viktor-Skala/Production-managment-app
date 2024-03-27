package com.example.factory.service.stoppage;

import com.example.factory.dto.stoppage.BaseTypeStoppageDto;
import com.example.factory.model.stoppage.BaseTypeStoppage;

import java.util.List;

public interface BaseTypeStoppageService {

    BaseTypeStoppage create(BaseTypeStoppage stoppage);
    BaseTypeStoppage read(long id);
    List<BaseTypeStoppage> getAll();
    BaseTypeStoppage update(BaseTypeStoppage stoppage);
    void delete(long machineId);
}
