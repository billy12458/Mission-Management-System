package com.cabbage.mms.service;

import java.util.List;

import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cabbage.mms.mapper.DepartmentMapper;

@SuppressWarnings("all")
@SuppressAjWarnings
@Service("departmentService")
public class DepartmentService {

    @Autowired
    public DepartmentMapper departmentMapper;

    public List<Integer> getEmpIds(Integer departId) {
        return departmentMapper.getEmpIds(departId);
    }

    public List<Integer> getAllEmps() {
        return departmentMapper.getAllEmps();
    }
}
