package com.tumorTest.excption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class LoginException extends BaseException{


    public LoginException(){}
    public LoginException(String msg){
        super(msg);
    }

}
