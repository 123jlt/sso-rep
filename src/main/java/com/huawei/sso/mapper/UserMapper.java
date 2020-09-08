package com.huawei.sso.mapper;

import com.huawei.sso.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User>  getList();

    public List<User> getUserId (@Param(value = "id")int id);

    public String getAddUser (User user);

}
