package com.example.k5_iot_springboot.service.impl;

import com.example.k5_iot_springboot.entity.A_Test;
import com.example.k5_iot_springboot.service.A_TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class A_TestServiceImpl implements A_TestService {

    @Override
    public A_Test createTest(A_Test test) {
        return null;
    }

    @Override
    public List<A_Test> getAllTests() {
        return List.of();
    }

    @Override
    public A_Test getTestByTestId(Long testId) {
        return null;
    }

    @Override
    public A_Test updateTest(Long testId, A_Test test) {
        return null;
    }

    @Override
    public void deleteTest(Long testId) {

    }
}
