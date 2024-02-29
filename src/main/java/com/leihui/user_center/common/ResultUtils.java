package com.leihui.user_center.common;

/**
 * return utils: generate return entity
 */
public class ResultUtils {
    /**
     *  return success
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }
    /**
     *  return error
     * @param errorCode
     * @return
     * @param
     */
    public static  BaseResponse error(ErrorCode errorCode) {
        // return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(),errorCode.getDescription());
        return new BaseResponse<>(errorCode);
    }

    public static  BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, message, description);
    }

    public static  BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(),null, errorCode.getMessage(), description);
    }

    public static  BaseResponse error(int errorCode, String message, String description) {
        return new BaseResponse<>(errorCode,null, message, description);
    }
}
