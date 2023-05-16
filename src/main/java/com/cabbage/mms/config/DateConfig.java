package com.cabbage.mms.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DateConfig {

    @Bean("format")
    public SimpleDateFormat getFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format;
    }
}
