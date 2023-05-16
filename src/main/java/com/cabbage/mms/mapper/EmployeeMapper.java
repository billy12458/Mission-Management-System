package com.cabbage.mms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.cabbage.mms.Entity.Employee;

@Mapper
public interface EmployeeMapper {

    Employee findById(Integer id);

    Employee findProfileById(Integer id);

    // 这里只能返回int或者void
    // @Insert("INSERT INTO employee VALUES(?,?,?,?,?,?")
    int saveEmployee(Employee employee);

    List<Employee> findAll(Integer departId);

    List<Employee> findAllOrderedByAgeDesc();

    List<Employee> findAllOrderedByAgeAsc();

    List<Employee> findAllNotBinded();

    int updateEmployee(@Param("employee") Employee employee, @Param("id") Integer id);

    int bindEmployee(@Param("departId") Integer departId, @Param("id") Integer id);

    int updatePasswordByEmail(@Param("password") String password, @Param("salt") String salt,
            @Param("email") String email);

}
