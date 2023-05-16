package com.cabbage.mms.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

    private String username;

    public Integer id;

    private String salt;

    private String password;

    public String occupation;

    public byte[] avatar;

    public Date startTime;

    public Integer type;

    private String email;

    private String sex;

    private Integer age;

    public Integer status;

    public String phone;

    public String code;

    public Integer departId;

    private List<Mission> getMissions;
}
