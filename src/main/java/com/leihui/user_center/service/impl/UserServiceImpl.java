package com.leihui.user_center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leihui.user_center.common.ErrorCode;
import com.leihui.user_center.exception.BusinessException;
import com.leihui.user_center.mapper.UserMapper;
import com.leihui.user_center.model.domain.User;
import com.leihui.user_center.service.UserService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import static com.leihui.user_center.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Andi
 * @description table【user】 implement
 * @createDate 2024-01-23 22:15:21
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "leihui";
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String codeId) {
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, codeId)) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"Empty Params");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "user account is too short");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password is too short");
        }
        if (codeId.length() > 5) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Code id is too long");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "Password is not consistent");
        }
        // Check DB unique userAccount
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // Check DB unique codeId
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("codeId", codeId);
        count = this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //Encryption
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT +userPassword).getBytes());

        //Insert to DB
        User user = new User();
        user.setCodeID(codeId);
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        //when it fails, userId is null, error long->Long
        if(!saveResult) {
            return -1;
        }

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4 || userPassword.length() < 8) {
            return null;
        }
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT +userPassword).getBytes());
        //Query User account
        //Mybatis check logic delete?
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("User login failed, wrong account or password");
            return null;
        }
        //Data desensitization
        User desenUser = desenUser(user);

        //Record user login status via session
        request.getSession().setAttribute(USER_LOGIN_STATE,desenUser);
        return desenUser;
    }

    /**
     * User Data desensitization
     * @param originUser
     * @return
     */
    @Override
    public User desenUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User desenUser = new User();
        desenUser.setId(originUser.getId());
        desenUser.setUsername(originUser.getUsername());
        desenUser.setUserAccount(originUser.getUserAccount());
        desenUser.setAvatarUrl(originUser.getAvatarUrl());
        desenUser.setGender(originUser.getGender());
        desenUser.setPhone(originUser.getPhone());
        desenUser.setEmail(originUser.getEmail());
        desenUser.setCodeID(originUser.getCodeID());
        desenUser.setUserRole(originUser.getUserRole());
        desenUser.setUserStatus(originUser.getUserStatus());
        desenUser.setCreateTime(originUser.getCreateTime());
        return desenUser;
    }

    /**
     * User Logout
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




