package com.swapnanil.apiservice.repository;

import com.swapnanil.apiservice.model.TesterCode;
import com.swapnanil.apiservice.model.TesterCodeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TesterCodeRepository extends JpaRepository<TesterCode, TesterCodeKey> {
    List<TesterCode> findAllByIdPid(String pid);
}
