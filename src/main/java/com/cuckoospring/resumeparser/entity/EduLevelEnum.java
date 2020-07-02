package com.cuckoospring.resumeparser.entity;

import com.cuckoospring.resumeparser.consts.ResumeParserConsts;

/**
 * 学历层次
 * @author cuckoo-spring
 *
 */
public enum EduLevelEnum {

    /** 小学 */
    PRIMARY(ResumeParserConsts.EDU_LEVEL_PRIMARY),

    /** 初中 */
    JUNIOR(ResumeParserConsts.EDU_LEVEL_JUNIOR),

    /** 高中 */
    HIGH(ResumeParserConsts.EDU_LEVEL_HIGH),

    /** 专科 */
    SPECIALTY(ResumeParserConsts.EDU_LEVEL_SPECIALTY),

    /** 本科 */
    UNDERGRADUATE(ResumeParserConsts.EDU_LEVEL_UNDERGRADUATE),

    /** 硕士研究生 */
    MASTER(ResumeParserConsts.EDU_LEVEL_MASTER),

    /** 博士研究生 */
    DOCTOR(ResumeParserConsts.EDU_LEVEL_DOCTOR);

    /** 值 */
    private String value = "";

    /**
     * 构造函数
     * @param value 学历层次取值
     */
    EduLevelEnum(String value) {
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
