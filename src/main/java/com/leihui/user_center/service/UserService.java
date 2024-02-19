package com.leihui.user_center.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leihui.user_center.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;


/**
* @author Andi
* @description 针对表【user】的数据库操作Service
* @createDate 2024-01-23 22:15:21
*/
public interface UserService extends IService<User> {


    /**
     * User Register
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return user id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String codeId);

    /**
     * User Login
     * @param userAccount
     * @param userPassword
     * @return login User
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * User Data desensitization
     * @param originUser
     * @return desensitized User
     */
    User desenUser(User originUser);

    /**
     *
     * @param request
     * @return logout
     */
    int userLogout(HttpServletRequest request);
}
