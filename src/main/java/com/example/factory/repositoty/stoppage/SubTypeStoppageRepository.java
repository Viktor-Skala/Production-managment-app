package com.example.factory.repositoty.stoppage;

import com.example.factory.model.stoppage.BaseTypeStoppage;
import com.example.factory.model.stoppage.SubTypeStoppage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface SubTypeStoppageRepository extends JpaRepository<SubTypeStoppage, Long> {
    List<SubTypeStoppage> findByBaseTypeStoppage_Id(Long id);
    Optional<SubTypeStoppage> findByName(@NonNull String name);
}
