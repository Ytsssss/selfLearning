package com.self.leaning.demo.data.dao;

import com.mongodb.client.result.UpdateResult;
import com.self.leaning.demo.controller.ClassController;
import com.self.leaning.demo.data.dao.common.AbstractMongoRepository;
import com.self.leaning.demo.data.pojo.ClassDO;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yegk7
 */
@Slf4j
@Repository
public class ClassRepositoryImpl extends AbstractMongoRepository<ClassDO> implements ClassRepository {
    public ClassRepositoryImpl(MongoTemplate template) {
        super(ClassDO.class, template);
    }

    @Override
    public void save(ClassDO classDO) {

        template.save(classDO);
    }

    @Override
    public long pullTeacherItem(String classId, List<String> item) {

        Criteria criteria = Criteria.where(ClassDO.FIELD_ID).is(new ObjectId(classId));

        Update update = new Update();
        update.pullAll(ClassDO.FIELD_TEACHER_IDS, item.toArray());

        UpdateResult writeResult = template.updateMulti(Query.query(criteria), update, clazz);
        long n = writeResult.getModifiedCount();
        if (n == 0) {
            log.warn("No matched class {} found when pull class teacher item", classId);
        }

        return n;
    }

    @Override
    public ClassDO queryById(String classId) {

        return template.findById(new ObjectId(classId), clazz);
    }

    @Override
    public long pushTeacherItem(String classId, List<String> item) {

        Criteria criteria = Criteria.where(ClassDO.FIELD_ID).is(new ObjectId(classId));

        Update update = new Update();
        update.pushAll(ClassDO.FIELD_TEACHER_IDS, item.toArray());

        UpdateResult writeResult = template.updateMulti(Query.query(criteria), update, clazz);
        long n = writeResult.getModifiedCount();
        if (n == 0) {
            log.warn("No matched class {} found when push class teacher item", classId);
        }

        return n;
    }

    @Override
    public long pullStudentItem(String classId, List<String> ids) {

        Criteria criteria = Criteria.where(ClassDO.FIELD_ID).is(new ObjectId(classId));

        Criteria subCriteria = Criteria.where(ClassDO.FIELD_WITH_OUT_STUDENT_ID).in(ids);
        Update update = new Update();
        update.pull(ClassDO.FIELD_STUDENTS, Query.query(subCriteria));

        UpdateResult writeResult = template.updateMulti(Query.query(criteria), update, clazz);
        long n = writeResult.getModifiedCount();
        if (n == 0) {
            log.warn("No matched class {} found when pull class student item", classId);
        }

        return n;
    }

    @Override
    public long pushStudentItem(String classId, List<ClassController.StudentDTO> item) {

        Criteria criteria = Criteria.where(ClassDO.FIELD_ID).is(new ObjectId(classId));

        Update update = new Update();
        Update.AddToSetBuilder add = update.new AddToSetBuilder(ClassDO.FIELD_STUDENTS);
        update = add.each(item);

        UpdateResult writeResult = template.updateMulti(Query.query(criteria), update, clazz);
        long n = writeResult.getModifiedCount();
        if (n == 0) {
            log.warn("No matched class {} found when push class student item", classId);
        }

        return n;
    }

    @Override
    public long updateStudentItem(String classId, String studentId, ClassController.StudentDTO item) {

        Criteria criteria = Criteria.where(ClassDO.FIELD_ID).is(classId)
                .and(ClassDO.FIELD_STUDENT_ID).is(studentId);

        Update update = Update.update(ClassDO.FIELD_STUDENT_AGE, item.getAge())
                .set(ClassDO.FIELD_STUDENT_NAME, item.getName())
                .set(ClassDO.FIELD_STUDENT_SEX, item.getSex());

        UpdateResult updateResult = template.updateFirst(Query.query(criteria), update, clazz);

        long n = updateResult.getModifiedCount();
        if (n == 0) {
            log.warn("No matched class {} found when update class student item", classId);
        }

        return n;
    }
}
