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
    private Integer id;
    private  String name;
    private String number;
    private String password;
    private Integer age;
    private String province;
    private String county;
    private String town;
    private String village;
    private String address;
    private String postcode;


}
