package com.cuckoospring.resumeparser.exception;

/**
 * 简历解析异常
 * @author cuckoo-spring
 *
 */
public class ResumeParserException extends Exception {

    /** 序列化 ID */
    private static final long serialVersionUID = 2537503810292016998L;

    public ResumeParserException(String message) {
        super(message);
    }

}
