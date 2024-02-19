package com.leihui.user_center.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Front-end User Register Entity
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID =321432432L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String codeId;
}
