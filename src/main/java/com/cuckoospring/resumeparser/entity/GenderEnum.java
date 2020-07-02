package com.cuckoospring.resumeparser.entity;

import com.cuckoospring.resumeparser.consts.ResumeParserConsts;

/**
 * 性别
 * @author cuckoo-spring
 *
 */
public enum GenderEnum {

    /** 男性 */
    MALE(ResumeParserConsts.GENDER_MALE),

    /** 女性 */
    FEMALE(ResumeParserConsts.GENDER_FEMALE);

    /** 值 */
    private String value = "";

    /**
     * 构造函数
     * @param value 性别取值
     */
    GenderEnum(String value) {
        this.value = value;
    }

    /**
     * 获取值
     * @return 值
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
