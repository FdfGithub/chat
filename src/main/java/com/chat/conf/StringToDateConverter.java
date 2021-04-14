package com.chat.conf;

import com.chat.util.DateTimeUtil;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class StringToDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        return DateTimeUtil.strToDate(s,"yyyy-MM-dd");
    }
}
