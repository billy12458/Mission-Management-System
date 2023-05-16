package com.cabbage.mms.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.cabbage.mms.Entity.Employee;
import com.cabbage.mms.mapper.EmployeeMapper;
import com.cabbage.mms.utils.PageUtils;
import com.github.pagehelper.PageInfo;

@SuppressWarnings("all")
@Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
@Service("employeeService")
public class EmployeeService {

    @Autowired
    public EmployeeMapper employeeMapper;

    // @Cacheable(value = "profileCache", key = "#id")
    public Employee findById(Integer id) {
        return employeeMapper.findById(id);
    }

    public Employee findProfileById(Integer id) {
        return employeeMapper.findProfileById(id);
    }

    // @CachePut(value = "profileCache", key = "#employee.id")
    public int saveEmployee(Employee employee) {
        try {
            String salt = RandomStringUtils.randomAlphanumeric(8);
            employee.setSalt(salt);
            employee.setPassword(new Sha256Hash(employee.getPassword(), salt, 512).toHex());
            employee.setStatus(0);
            employee.setStartTime(new Date());
            employee.setType(0);
            employee.setAvatar(null);
            return employeeMapper.saveEmployee(employee);
        } catch (Exception e) {
            throw e;
        }
    }

    public int updatePasswordByEmail(String password, String salt, String email) {
        String updatePassword = new Sha256Hash(password, salt, 512).toHex();
        return employeeMapper.updatePasswordByEmail(updatePassword, salt, email);
    }

    public int bindEmployee(Integer departId, Integer id) {
        return employeeMapper.bindEmployee(departId, id);
    }

    // 这里固定是比如说20条
    public PageInfo<Employee> findAll(Integer departId, Integer pageNum, boolean count) {
        List<Employee> allList = employeeMapper.findAll(departId);
        PageInfo<Employee> infoPage = PageUtils.getEmpInfo(pageNum, 20, count, allList);
        return infoPage;
    }

    public void updateEmployee(Employee employee, Integer id) {
        // mybatis动态sql语句可以解决是空则不更新
        employeeMapper.updateEmployee(employee, id);
    }

    public PageInfo<Employee> findAllNotBinded(Integer pageNum, boolean count) {
        List<Employee> allList = employeeMapper.findAllNotBinded();
        PageInfo<Employee> infoPage = PageUtils.getEmpInfo(pageNum, 20, count, allList);
        return infoPage;
    }
}
