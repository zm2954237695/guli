package com.guo.ucenter.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVO implements Serializable {
    private String mobile;
    private String password;
}
