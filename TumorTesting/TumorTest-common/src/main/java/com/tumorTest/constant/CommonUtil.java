package com.tumorTest.constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean checkNmber(String number){


        // 定义手机号码的正则表达式
        String regex = "^1[3-9]\\d{9}$";

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);

        // 创建匹配器
        Matcher matcher = pattern.matcher(number);

        // 判断是否匹配
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

}
