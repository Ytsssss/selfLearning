package com.self.leaning.demo.controller;

import com.self.leaning.demo.data.dao.ClassRepository;
import com.self.leaning.demo.data.pojo.ClassDO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yegk7
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "rest/v1/demo/class")
public class ClassController {

    private final ClassRepository repository;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO insert(@RequestBody ClassDO classDO) {

        repository.save(classDO);
        return classDO;
    }

    @PatchMapping(value = "{classId}/teacher/pull", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO pullTeachItem(@RequestBody List<String> item,
                                 @PathVariable("classId") String classId) {

        repository.pullTeacherItem(classId, item);
        return repository.queryById(classId);
    }

    @PatchMapping(value = "{classId}/teacher/push", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO pushTeachItem(@RequestBody List<String> item,
                                 @PathVariable("classId") String classId) {

        repository.pushTeacherItem(classId, item);
        return repository.queryById(classId);
    }

    @PatchMapping(value = "{classId}/student/pull", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO pullStudentItem(@RequestBody List<String> ids,
                                   @PathVariable("classId") String classId) {

        repository.pullStudentItem(classId, ids);
        return repository.queryById(classId);
    }

    @PatchMapping(value = "{classId}/student/push", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO pushStudentItem(@RequestBody List<StudentDTO> item,
                                   @PathVariable("classId") String classId) {

        repository.pushStudentItem(classId, item);
        return repository.queryById(classId);
    }

    @PatchMapping(value = "{classId}/student/{studentId}/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO updateStudentItem(@RequestBody StudentDTO item,
                                     @PathVariable("classId") String classId,
                                     @PathVariable("studentId") String studentId) {

        repository.updateStudentItem(classId, studentId, item);
        return repository.queryById(classId);
    }

    @GetMapping(value = "/{classId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ClassDO get(@PathVariable("classId")String classId){

        return repository.queryById(classId);
    }

    @Data
    public static class StudentDTO {

        private String name;

        private String sex;

        private Integer age;
    }

}
