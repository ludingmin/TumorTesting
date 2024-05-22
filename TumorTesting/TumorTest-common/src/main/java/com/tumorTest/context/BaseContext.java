package com.tumorTest.context;

import com.tumorTest.dto.UserDto;

public class BaseContext {

    public static ThreadLocal<UserDto> threadLocal = new ThreadLocal<>();

    public static void setUser(UserDto userDto) {
        threadLocal.set(userDto);
    }

    public static UserDto getUser() {
        return threadLocal.get();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
