package com.ltp.backend.service;

import com.ltp.backend.entity.Manager;

public interface ManagerService {
    Manager getSingleManager(Long id);

    Manager updateManager(Long id, Manager manager);
}
