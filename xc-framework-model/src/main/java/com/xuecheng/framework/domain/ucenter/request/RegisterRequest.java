package com.xuecheng.framework.domain.ucenter.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RegisterRequest extends RequestData {
    String username;
    String password;
    String passwordConfirm;
    String email;
    String sex;
    String phone;
}
