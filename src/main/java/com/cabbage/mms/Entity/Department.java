package com.cabbage.mms.Entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer departmentId;

    private String departmentName;

    private Integer membernumbers;

    private Integer leaderId;

}
