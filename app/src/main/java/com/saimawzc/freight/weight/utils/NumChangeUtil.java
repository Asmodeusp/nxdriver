package com.saimawzc.freight.weight.utils;

/**
 * Created by Administrator on 2020-02-09.
 */

public class NumChangeUtil {
   static String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    static String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

    public static  String changeNum(String string){
        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  "+result);
        }

        System.out.println("----------------");
        System.out.println(result);
        return result;

    }
}
