package com.cuckoospring.resumeparser.annie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cuckoospring.resumeparser.exception.ResumeParserException;

import gate.Corpus;
import gate.CorpusController;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

/**
 * 简历信息抽取系统
 * @author cuckoo-spring
 *
 */
public class ResumeAnnie implements Annie {

    /** 日志记录对象*/
    private static final Log log = LogFactory.getLog(ResumeAnnie.class);

    /** CorpusController */
    protected CorpusController controller;

    /**
     * 构造函数
     * @param name xgapp 文件名称
     * @throws ResumeParserException Cannot load annie application file.
     */
    public ResumeAnnie(String name) throws ResumeParserException {
        try {
            controller = (CorpusController) PersistenceManager.loadObjectFromUrl(
                getClass().getClassLoader().getResource("gate-files/".concat(name).concat(".xgapp")));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new ResumeParserException("Cannot load annie application file.");
        }
    }

    @Override
    public void setCorpus(Corpus corpus) {
        controller.setCorpus(corpus);
    }

    @Override
    public void execute() throws GateException {
        controller.execute();
    }

    @Override
    public void interrupt() {
        if (!controller.isInterrupted()) {
            controller.interrupt();
        }
    }

}
