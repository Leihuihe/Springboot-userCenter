package com.leihui.user_center.service;
import java.util.Date;

import com.leihui.user_center.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;
    @Test
    public void testAdduser() {
        User user = new User();
        user.setUsername("test");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("");
        user.setEmail("");


        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "leihui";
        String userPassword = "";
        String checkPassword = "123456";
        String codeId = "1234";
        long result = userService.userRegister(userAccount,userPassword,checkPassword,codeId);
        Assertions.assertEquals(-1, result);
        userAccount = "lei";
        result = userService.userRegister(userAccount,userPassword,checkPassword,codeId);
        Assertions.assertEquals(-1, result);
        userAccount = "leihui";
        userPassword = "123456";
        result = userService.userRegister(userAccount,userPassword,checkPassword,codeId);
        Assertions.assertEquals(-1, result);
        userPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword,codeId);
        Assertions.assertEquals(-1, result);
        //Don't have special character checking yet
        userAccount = "lei hui";
        checkPassword = "12345678";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1, result);
        userAccount = "LeihuiHe";
        userPassword = "12345678";
        result = userService.userRegister(userAccount,userPassword,checkPassword,codeId);
        Assertions.assertTrue(result>0);

    }
}