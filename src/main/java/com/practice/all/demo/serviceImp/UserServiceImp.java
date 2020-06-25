package com.practice.all.demo.serviceImp;

import com.practice.all.demo.Entity.Student;
import com.practice.all.demo.dao.UserDao;
import com.practice.all.demo.service.UserService;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Student findById(String studentId) {
        Optional<Student> optional = userDao.findById(studentId);

        Student student = optional.orElse(null);

        return student;
    }

    @Override
    public void deleteById(String studentId) {
        userDao.deleteById(studentId);
    }

    @Override
    public List<Student> search(QueryBuilder qb1) {
        List<Student> list = new ArrayList<Student>();
        Iterable<Student> aIterable = userDao.search(qb1);
        for (Student student : aIterable) {
            list.add(student);
        }
        return list;
    }

    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<Student>();
        userDao.findAll().forEach(stu -> {
            //System.out.println("stuId: "+stu.getId()+"   stuName: "+stu.getName());
            Student student = new Student();
            student = stu;
            list.add(student);
        });
        return list;
    }
}
