package com.leihui.user_center.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leihui.user_center.model.domain.User;
import com.leihui.user_center.model.domain.request.UserLoginRequest;
import com.leihui.user_center.model.domain.request.UserRegisterRequest;
import com.leihui.user_center.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.leihui.user_center.constant.UserConstant.ADMIN_ROLE;
import static com.leihui.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * User API
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String codeId = userRegisterRequest.getCodeId();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword, codeId);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, httpServletRequest);
    }

    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null) {
            return null;
        }
        long userId = currentUser.getId();
        //TODO verify if user is legal
        User user = userService.getById(userId);
        return userService.desenUser(user);

    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        //Check role, only for admin
        if (!idAdmin(request)) {
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //When username is not empty, return query
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.desenUser(user)).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        //Check role, only for admin
        if(!idAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * Check Admin role
     * @param request HttpServletRequest
     * @return true for admin
     */
    private boolean idAdmin(HttpServletRequest request) {
        //Check role, true for admin
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;

    }
}
