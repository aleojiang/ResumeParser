package com.cuckoospring.resumeparser.consts;

/**
 * 简历解析常量
 * @author cuckoo-spring
 *
 */
public class ResumeParserConsts {

    /**
     * 禁止实例化
     */
    private ResumeParserConsts() {}

    /** Annie 名称：ResumeAnnie */
    public static final String ANNIE_RESUME = "ResumeAnnie";

    /** Annie 名称：EducationExperienceAnnie */
    public static final String ANNIE_EDUCATION_EXPERIENCE = "EducationExperienceAnnie";

    /** Annie 名称：JobExperienceAnnie */
    public static final String JOB_EXPERIENCE_ANNIE = "JobExperienceAnnie";

    /** 注解标签：姓名 - personName */
    public static final String ANNOTATION_PERSON_NAME = "personName";

    /** 注解标签：性别 - personGender */
    public static final String ANNOTATION_PERSON_GENDER = "personGender";

    /** 注解标签：生日 - personBirthday */
    public static final String ANNOTATION_PERSON_BIRTHDAY = "personBirthday";

    /** 注解标签：手机号码 - personPhone */
    public static final String ANNOTATION_PERSON_PHONE = "personPhone";

    /** 注解标签：电子邮箱 - personEmail */
    public static final String ANNOTATION_PERSON_EMAIL = "personEmail";

    /** 注解标签：应聘职位 - jobIntent */
    public static final String ANNOTATION_JOB_INTENT = "jobIntent";

    /** 注解标签：期望工作地点 - jobCity */
    public static final String ANNOTATION_JOB_CITY = "jobCity";

    /** 性别：男 - male */
    public static final String GENDER_MALE = "male";

    /** 性别：女 - female */
    public static final String GENDER_FEMALE = "female";

    /** 学历层次：小学 - primary */
    public static final String EDU_LEVEL_PRIMARY = "primary";

    /** 学历层次：初中 - junior */
    public static final String EDU_LEVEL_JUNIOR = "junior";

    /** 学历层次：高中 - high */
    public static final String EDU_LEVEL_HIGH = "high";

    /** 学历层次：专科 - specialty */
    public static final String EDU_LEVEL_SPECIALTY = "specialty";

    /** 学历层次：本科 - undergraduate */
    public static final String EDU_LEVEL_UNDERGRADUATE = "undergraduate";

    /** 学历层次：硕士研究生 - master */
    public static final String EDU_LEVEL_MASTER = "master";

    /** 学历层次：博士研究生 - doctor */
    public static final String EDU_LEVEL_DOCTOR = "doctor";

}
