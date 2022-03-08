package com.saimawzc.freight.weight;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/***
 * 小数点之后限制位数
 * **/
public class LengthFilter implements InputFilter {

    /** 输入框小数的位数, 如保留一位小数*/
    private   int DECIMAL_COUNT = 1;
    public LengthFilter(int num){
        this.DECIMAL_COUNT=num;
    }

    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        // 删除等特殊字符，直接返回
        if(source==null){
            return null;
        }
        if ("".equals(source.toString())) {
            return null;
        }
        if(!TextUtils.isEmpty(dest.toString())){
            String destValue = dest.toString();
            String[] splitArray = destValue .split("\\.");
            if (splitArray.length > 1) {
                String result = splitArray[1];
                int d = result.length() + 1 - DECIMAL_COUNT;
                if(source.toString().length()<=end - d){
                    return null;
                }
                if (d > 0) {
                    return source.subSequence(start, end - d);
                }
            }
        }

        return null;
    }
}
