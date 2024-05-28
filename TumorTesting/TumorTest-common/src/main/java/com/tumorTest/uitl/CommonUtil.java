package com.tumorTest.uitl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    // 读取文件内容为 byte 数组的方法
    public static byte[] readFileToBytes(File file) {
        FileInputStream fis = null;
        byte[] fileBytes = null;
        try {
            fis = new FileInputStream(file);
            fileBytes = new byte[(int) file.length()];
            fis.read(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileBytes;
    }

}
