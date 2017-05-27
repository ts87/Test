package com.homework.ts.util;

/**
 * Created by ts on 2017/5/7.
 */

public class DateUtil {

    public static String DateFormat(String date){
        String finalDate = "";
        String[] dateStr = date.split("T");
        finalDate += dateStr[0];
        String temp = dateStr[1].substring(0,8);
        finalDate += " " + temp;
        return finalDate;
    }
}
