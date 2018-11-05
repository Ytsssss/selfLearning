package com.self.leaning.demo.data.dao;

import com.self.leaning.demo.controller.ClassController;
import com.self.leaning.demo.data.pojo.ClassDO;

import java.util.List;

/**
 * @author yegk7
 */
public interface ClassRepository {

    void save(ClassDO classDO);

    long pullTeacherItem(String classId, List<String> item);

    ClassDO queryById(String classId);

    long pushTeacherItem(String classId, List<String> item);

    long pullStudentItem(String classId, List<String> item);

    long pushStudentItem(String classId, List<ClassController.StudentDTO> item);

    long updateStudentItem(String classId, String studentId, ClassController.StudentDTO item);
}
