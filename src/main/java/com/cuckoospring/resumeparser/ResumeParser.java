package com.cuckoospring.resumeparser;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.cuckoospring.resumeparser.annie.Annie;
import com.cuckoospring.resumeparser.annie.ResumeAnnie;
import com.cuckoospring.resumeparser.consts.ResumeParserConsts;
import com.cuckoospring.resumeparser.entity.GenderEnum;
import com.cuckoospring.resumeparser.entity.Resume;
import com.cuckoospring.resumeparser.exception.ResumeParserException;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.Document;
import gate.DocumentContent;
import gate.Factory;
import gate.Gate;
import gate.corpora.DocumentContentImpl;
import gate.corpora.DocumentImpl;
import gate.util.GateException;
import gate.util.InvalidOffsetException;

/**
 * 简历解析工具
 * @author cuckoo-spring
 *
 */
public final class ResumeParser {

    /** 日志记录对象*/
    private static final Log log = LogFactory.getLog(ResumeParser.class);

    /** 禁止实例化 */
    private ResumeParser() {}

    /**
     * 解析简历文件得到简历信息实体
     * @param file 简历文件
     * @return 简历
     * @throws ResumeParserException The file is not exist!
     * @throws ResumeParserException Cannot initialize Gate.
     * @throws ResumeParserException Cannot load annie application file.
     * @throws ResumeParserException Cannot parse the file.
     * @throws ResumeParserException Cannot get resume informations after parsing the file.
     */
    public static Resume parse(File file) throws ResumeParserException {
        if (null == file || !file.exists() || !file.isFile()) {
            throw new ResumeParserException("The file is not exist!");
        }
        // 初始化 Gate
        initGate();
        Annie annie = null;
        try {
            // 初始化简历信息抽取系统
            annie = new ResumeAnnie(ResumeParserConsts.ANNIE_RESUME);
            // 解析文件
            DocumentImpl documentImpl = new DocumentImpl();
            executeAnnie(annie, documentImpl, file);
            // 获得结果
            return generateResume(documentImpl.getAnnotations(), documentImpl.getContent());
        } finally {
            try {
                if (null != annie) {
                    annie.interrupt();
                    annie = null;
                }
            } catch (Exception e) {
                // ignore
            }
        }
    }

    /**
     * 通过 Apache Tika 解析文件获得文本
     * @param file 文件
     * @return 文本
     * @throws IOException IO 异常
     * @throws SAXException SAXException
     * @throws TikaException Apache Tika 异常
     */
    private static String getFileContent(File file) throws IOException, SAXException, TikaException {
        ContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        new AutoDetectParser(TikaConfig.getDefaultConfig()).parse(
            TikaInputStream.get(file.toPath(), metadata), handler, metadata, new ParseContext());
        return handler.toString();
    }

    /**
     * 初始化 Gate
     * @throws ResumeParserException Cannot init Gate.
     */
    private static void initGate() throws ResumeParserException {
        if (!Gate.isInitialised()) {
            Gate.runInSandbox(true);
            try {
                Gate.init();
            } catch (GateException e) {
                if (log.isErrorEnabled()) {
                    log.error("", e);
                }
                throw new ResumeParserException("Cannot initialize Gate.");
            }
        }
    }

    /**
     * 通过 Annie 进行信息的抽取
     * @param annie Annie
     * @param document 文档对象
     * @param file 简历文件
     * @throws ResumeParserException Cannot parse the file.
     */
    private static void executeAnnie(Annie annie, Document document, File file) throws ResumeParserException {
        try {
            Corpus corpus = Factory.newCorpus(null);
            document.setContent(new DocumentContentImpl(getFileContent(file)));
            corpus.add(document);
            annie.setCorpus(corpus);
            annie.execute();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new ResumeParserException("Cannot parse the file.");
        }
    }

    /**
     * 从 Gate 解析的注解结果中获取简历各属性数据，组装简历实体
     * @param set Gate 注解集合
     * @param content Gate 文档内容
     * @return 简历实体
     * @throws ResumeParserException Cannot get resume informations after parsing the file.
     */
    private static Resume generateResume(AnnotationSet set, DocumentContent content) throws ResumeParserException {
        Resume resume = new Resume();
        try {
            // 姓名
            resume.setName(getContent(set.get(ResumeParserConsts.ANNOTATION_PERSON_NAME), content));
            // 性别
            resume.setGender(getGender(getContent(set.get(ResumeParserConsts.ANNOTATION_PERSON_GENDER), content)));
            // 生日
            getBirthDay(getContent(set.get(ResumeParserConsts.ANNOTATION_PERSON_BIRTHDAY), content), resume);
            // 应聘职位
            resume.setJob(getContent(set.get(ResumeParserConsts.ANNOTATION_JOB_INTENT), content));
            // 期望工作地点
            resume.setWorkPlace(getContent(set.get(ResumeParserConsts.ANNOTATION_JOB_CITY), content));
            // 手机号码
            resume.setMobile(getContent(set.get(ResumeParserConsts.ANNOTATION_PERSON_PHONE), content));
            // 电子邮箱
            resume.setEmail(getContent(set.get(ResumeParserConsts.ANNOTATION_PERSON_EMAIL), content));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("", e);
            }
            throw new ResumeParserException("Cannot get resume informations after parsing the file.");
        }
        return resume;
    }

    /**
     * 获取指定注解的第一个文本
     * @param set Gate 注解集合
     * @param content Gate 文档内容
     * @return 文本
     * @throws InvalidOffsetException 位置异常
     */
    private static String getContent(AnnotationSet set, DocumentContent content) throws InvalidOffsetException {
        if (!set.isEmpty()) {
            Iterator<Annotation> iterator = set.iterator();
            while (iterator.hasNext()) {
                Annotation annotation = iterator.next();
                String result = content.getContent(annotation.getStartNode().getOffset(),
                    annotation.getEndNode().getOffset()).toString();
                if (StringUtils.isNoneEmpty(result)) {
                    return result.trim();
                }
            }
        }
        return "";
    }

    /**
     * 获取性别
     * @param gender 性别文本
     * @return 性别
     */
    private static GenderEnum getGender(String gender) {
        if (StringUtils.isNotBlank(gender)) {
            if ("男".equals(gender) || "male".equalsIgnoreCase(gender)) {
                return GenderEnum.MALE;
            } else if ("女".equals(gender) || "female".equalsIgnoreCase(gender)) {
                return GenderEnum.FEMALE;
            }
        }
        return null;
    }

    /**
     * 获取出生年月日
     * @param birthDay 生日文本
     * @param resume 简历
     */
    private static void getBirthDay(String birthDay, Resume resume) {
        if (StringUtils.isBlank(birthDay)) {
            return;
        }
        int index = 0;
        StringBuilder builder = new StringBuilder();
        for (char ch : birthDay.toCharArray()) {
            if (Character.isDigit(ch)) {
                // 数字
                builder.append(ch);
            } else if (!Character.isWhitespace(ch) && builder.length() > 0) {
                // 非空字符且已有值
                if (index++ == 0) {
                    resume.setBirthYear(getYear(builder.toString()));
                } else if (index++ == 1) {
                    resume.setBirthMonth(getMonth(builder.toString()));
                } else if (index++ == 2) {
                    resume.setBirthDay(getDay(builder.toString()));
                }
                builder.setLength(0);
            }
        }
    }

    /**
     * 解析得到出生年份。支持四位年份和两位年份。
     * @param number 出生年份字符串
     * @return 出生年份
     */
    private static Integer getYear(String number) {
        if (number.length() == 2) {
            int year = Integer.parseInt("20" + number);
            if (year <= Calendar.getInstance().get(Calendar.YEAR)) {
                return year;
            }
            number = "19" + number;
        }
        if (number.length() == 4) {
            return Integer.parseInt(number);
        }
        return null;
    }

    /**
     * 解析得到出生月份
     * @param number 出生月份字符串
     * @return 出生月份
     */
    private static Integer getMonth(String number) {
        int month = Integer.parseInt(number);
        if (month > 0 && month < 13) {
            return month;
        }
        return null;
    }

    /**
     * 解析得到出生日期
     * @param number 出生日期字符串
     * @return 出生日期
     */
    private static Integer getDay(String number) {
        int day = Integer.parseInt(number);
        if (day > 0 && day <= 31) {
            return day;
        }
        return null;
    }

}
