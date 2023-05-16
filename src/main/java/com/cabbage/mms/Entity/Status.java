package com.cabbage.mms.Entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class Status implements Serializable {

    private boolean online;

    private String lastLoginTime;

    private String lastLogoutTime;

    private String lastVistedTime;

}
