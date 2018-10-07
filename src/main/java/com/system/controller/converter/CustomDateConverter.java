package com.system.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateConverter implements Converter<String, Date> {


    public Date convert(String s) {
        //瀹炵幇 灏嗘棩鏈熶覆杞垚鏃ユ湡绫诲瀷(鏍煎紡鏄痽yyy-MM-dd)
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            //杞垚鐩存帴杩斿洖
            return simpleDateFormat.parse(s);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //濡傛灉鍙傛暟缁戝畾澶辫触杩斿洖null
        return null;
    }
}
