package com.example.factory.service.stoppage;

import com.example.factory.dto.stoppage.SubTypeStoppageDto;
import com.example.factory.model.stoppage.SubTypeStoppage;

import java.util.List;

public interface SubTypeStoppageService {

    SubTypeStoppage create(SubTypeStoppageDto stoppage);
    SubTypeStoppage read(long id);
    List<SubTypeStoppage> getAll();
    SubTypeStoppage update(SubTypeStoppageDto stoppage);
    void delete(long machineId);
    List<SubTypeStoppage> getAllByBaseTypeStoppage(long id);
}
