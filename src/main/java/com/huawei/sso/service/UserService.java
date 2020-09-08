package com.huawei.sso.service;

import com.huawei.sso.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

        List<User> getList();

        public String idaasLogin(Map<String, Object> userInfoMap);

        public List<User> getUserId (int id);

        public String getAddUser(User user);

}
