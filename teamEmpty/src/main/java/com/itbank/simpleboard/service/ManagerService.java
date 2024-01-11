package com.itbank.simpleboard.service;

import com.itbank.simpleboard.entity.Manager;
import com.itbank.simpleboard.repository.manager.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;

    public List<Manager> findAll() {
        return managerRepository.findAll();
    }
}
