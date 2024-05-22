package com.tumorTest.excption;

public class FormatException extends BaseException{
    private static String msg = "格式错误";
    public FormatException(){
        super(msg);
    }
}
