package com.cabbage.mms.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {

    List<Integer> getEmpIds(Integer departId);

    List<Integer> getAllEmps();

}
