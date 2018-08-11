package com.gtfund.gtapp.model;

/**
 * 登陆验证参数模型
 * Created by enzexue on 2018/8/8.
 */
public class Authorization{

    private String idType;
    private String id;
    private String idToken;
    private String password;
    private String captchaId;
    private String captchaAnswer;

    public Authorization(String idType, String id, String idToken, String password, String captchaId, String captchaAnswer) {
        this.idType = idType;
        this.id = id;
        this.idToken = idToken;
        this.password = password;
        this.captchaId = captchaId;
        this.captchaAnswer = captchaAnswer;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaAnswer() {
        return captchaAnswer;
    }

    public void setCaptchaAnswer(String captchaAnswer) {
        this.captchaAnswer = captchaAnswer;
    }

}
