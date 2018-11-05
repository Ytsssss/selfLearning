package com.self.leaning.demo.data.pojo;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author yegk7
 */
@Data
@Document(collection = "class")
@FieldNameConstants
public class ClassDO {

    public static final String FIELD_WITH_OUT_STUDENT_ID ="studentId";
    public static final String FIELD_STUDENT_ID ="students.studentId";
    public static final String FIELD_STUDENT_NAME ="students.$.name";
    public static final String FIELD_STUDENT_SEX ="students.$.sex";
    public static final String FIELD_STUDENT_AGE ="students.$.age";

    @Id
    private String id;

    private String name;

    /**
     * 老师Id列表
     */
    private List<String> teacherIds;

    /**
     * 学生列表
     */
    private List<Student> students;

    @Data
    public static class Student {

        private String studentId;

        private String name;

        private String sex;

        private Integer age;
    }
}
