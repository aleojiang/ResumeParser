package com.cuckoospring.resumeparser.entity;

import java.io.Serializable;

/**
 * 简历实体
 * @author cuckoo-spring
 *
 */
public class Resume implements Serializable {

    /** 序列化 ID */
    private static final long serialVersionUID = 3796995928474568017L;

    /** 姓名 */
    private String name;

    /** 性别 */
    private GenderEnum gender;

    /** 出生年份 */
    private Integer birthYear;

    /** 出生月份 */
    private Integer birthMonth;

    /** 出生日期 */
    private Integer birthDay;

    /** 最高学历 */
    private EduLevelEnum eduLevel;

    /** 应聘职位 */
    private String job;

    /** 期望工作地点 */
    private String workPlace;

    /** 手机号码 */
    private String mobile;

    /** 电子邮箱 */
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Integer birthDay) {
        this.birthDay = birthDay;
    }

    public EduLevelEnum getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(EduLevelEnum eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
