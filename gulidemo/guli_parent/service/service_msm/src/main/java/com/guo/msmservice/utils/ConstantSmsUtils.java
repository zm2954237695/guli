package com.guo.msmservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ConstantSmsUtils implements InitializingBean {


    @Value("${tencent.sms.keyId}")
    private String secretID;
    @Value("${tencent.sms.keysecret}")
    private String secretKey;
    @Value("${tencent.sms.smsSdkAppId}")
    private String smsSdkAppID;
    @Value("${tencent.sms.signName}")
    private String signName;
    @Value("${tencent.sms.templateId}")
    private String templateID;

    public static  String SECRET_KEY;
    public static  String SECRET_ID;
    public static  String SMSSDK_APP_ID;
    public static  String SIGN_NAME;
    public static  String TEMPLATE_ID;
    @Override
    public void afterPropertiesSet()  {
         SECRET_ID = secretID;
         SECRET_KEY=  secretKey;
         SMSSDK_APP_ID = smsSdkAppID;
         SIGN_NAME = signName;
         TEMPLATE_ID  = templateID;
    }
}
