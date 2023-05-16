package com.cabbage.mms.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    public Integer missionId;

    public Integer empId;

    public String comment;

    private Integer stars;

}
