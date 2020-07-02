package com.cuckoospring.resumeparser.annie;

import gate.Corpus;
import gate.util.GateException;

/**
 * 自定义信息抽取系统
 * @author cuckoo-spring
 *
 */
public interface Annie {

    /**
     * 设置待解析简历资源列表
     * @param corpus 待解析简历资源
     */
    void setCorpus(Corpus corpus);

    /**
     * 执行解析
     * @throws GateException GateException
     */
    void execute() throws GateException;

    /**
     * 停止解析
     */
    void interrupt();

}
