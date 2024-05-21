package com.tumorTest.entity;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mysql.cj.xdevapi.Table;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


//用户表
@Data
public class User {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private  String name;
    private String number;
    private String password;
    private Integer age;
    private String address;
    private String postcode;
    //0代表用户||1代表医生
    private Integer type;


}
