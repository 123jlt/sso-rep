package com.huawei.sso.service.impl;

import com.huawei.sso.entity.User;
import com.huawei.sso.mapper.UserMapper;
import com.huawei.sso.service.UserService;
import com.huawei.sso.utils.EmptyUtils;
import com.huawei.sso.utils.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    GenerateToken generateToken;


    @Override
    public List<User> getList() {
        return userMapper.getList();
    }

    @Override
    public List<User> getUserId(int id) {
        return userMapper.getUserId(id);
    }

    @Override
    public String getAddUser(User user) {
        userMapper.getAddUser(user);
        return user.getUserId();
    }

    @Override
    public String idaasLogin(Map<String, Object> userInfoMap) {

        String userId = userInfoMap.get("userId").toString();
        String userName = userInfoMap.get("userName").toString();
        String name = userInfoMap.get("name").toString();
        String email = userInfoMap.get("email").toString();
        int mobile = (int) userInfoMap.get("mobile");
        String account = userInfoMap.get("account").toString();
       /* //判断是否入库
        User user = (User) userMapper.getUserId(openId);*/
        User user = new User();
        String userid;
        if (EmptyUtils.isEmpty(user)) {
            user.setUserId(userId);
            user.setUsername(userName);
            user.setName(name);
            user.setEmail(email);
            user.setMobile(mobile);
            user.setAccount(account);

            userid = this.userMapper.getAddUser(user).toString();

        }
        String token = generateToken.getToken(user);

        return token;
    }

}
