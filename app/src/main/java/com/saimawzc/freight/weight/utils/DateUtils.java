package com.saimawzc.freight.weight.utils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtils {
    private static DateUtils instance;

    public static DateUtils getInstance() {
        if (instance == null) {
            instance = new DateUtils();
        }
        return instance;
    }

    /**
     * <pre>
     * 根据指定的日期字符串获取星期几
     * </pre>
     *
     * @param strDate 指定的日期字符串(yyyy-MM-dd 或 yyyy/MM/dd)
     * @return week
     */
    public String getWeek(String strDate) {

        int y = Integer.parseInt(strDate.substring(0, 4));//年
        int m = Integer.parseInt(strDate.substring(5, 7));//月
        int d = Integer.parseInt(strDate.substring(8, 10));//日

        Calendar c = Calendar.getInstance();

        c.set(Calendar.YEAR, y);
        c.set(Calendar.MONTH, m - 1);
        c.set(Calendar.DAY_OF_MONTH, d);

        String week = "";
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);

        switch (weekIndex) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    /**
     * 从字符串中获取日期
     * @param strDate
     * @return
     */
    public String getDate(String strDate) {
        String s = "";
        Pattern p = Pattern.compile("(\\d{4})-(\\d{1,2})-(\\d{1,2}) (\\d{1,2}):(\\d{1,2})");
        Matcher m = p.matcher(strDate);
        while (m.find()) {
            s += m.group();
        }
        return s;
    }

    /**
     * 从字符串中获取时间
     * @param strDate
     * @return
     */
    public String getTime(String strDate) {
        String s = "";
        Pattern p = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})");
        Matcher m = p.matcher(strDate);
        while (m.find()) {
            s += m.group();
        }
        return s;
    }


}
