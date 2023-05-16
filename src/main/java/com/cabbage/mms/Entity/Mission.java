package com.cabbage.mms.Entity;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {

    public String missionName;

    public Integer missionId;

    public Integer departmentId;

    private Integer importance;

    public Date startTime;

    public Date endTime;

    public String description;

    // modify
    public Integer workTime;

    public Integer status;

    @Transient
    public List<Integer> to;

    public MultipartFile[] multipartFile;

}
